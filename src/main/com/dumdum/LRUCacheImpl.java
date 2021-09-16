package main.com.dumdum;

import main.com.dumdum.interfaces.Cache;
import main.com.dumdum.interfaces.LinkedListNode;
import main.com.dumdum.model.CacheElement;
import main.com.dumdum.model.DoublyLinkedList;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCacheImpl<K, V> implements Cache<K, V> {

    private int size;

    private Map<K, LinkedListNode<CacheElement<K,V>>> llNodeMap;
//
    private DoublyLinkedList<CacheElement<K, V>> doublyLL;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public LRUCacheImpl(int size) {
        this.size = size;
        this.doublyLL = new DoublyLinkedList<CacheElement<K, V>>();
        this.llNodeMap = new ConcurrentHashMap<>(size);
    }

    @Override
    public Optional<V> getKey(K key) {
        this.lock.readLock().lock();
        try{
            LinkedListNode<CacheElement<K, V>> llNode = this.llNodeMap.get(key);
            if(llNode == null || llNode.isEmpty()){
                return Optional.empty();
            } else{
                llNodeMap.put(key,this.doublyLL.moveToFront(llNode));
                return Optional.of(llNode.getElement().getValue());
            }
        } finally {
            this.lock.readLock().unlock();
        }
    }

    @Override
    public boolean setKey(K key, V value) {
        this.lock.writeLock().lock();
        try{
            CacheElement<K, V> item = new CacheElement<>(key, value);
            LinkedListNode<CacheElement<K, V>> newLLNode;
            if(this.llNodeMap.containsKey(key)){
                newLLNode = this.doublyLL.updateAndMoveToFront(this.llNodeMap.get(key),item);
            } else {
                if (this.size() >= this.size) {
                   this.evictElement();
                }
                newLLNode = this.doublyLL.add(item);
            }
            if(newLLNode.isEmpty()){
                return false;
            }
            this.llNodeMap.put(key,newLLNode);
            return true;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public int size() {
        this.lock.readLock().lock();
        try{
            return doublyLL.size();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void clear() {
        this.lock.writeLock().lock();
        try{
            doublyLL.clear();
            llNodeMap.clear();
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    private boolean evictElement() {
        this.lock.writeLock().lock();
        try {
            LinkedListNode<CacheElement<K, V>> linkedListNode = doublyLL.removeTail();
            if (linkedListNode.isEmpty()) {
                return false;
            }
            llNodeMap.remove(linkedListNode.getElement().getKey());
            return true;
        } finally {
            this.lock.writeLock().unlock();
        }
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
