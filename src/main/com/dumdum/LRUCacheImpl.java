package main.com.dumdum;

import main.com.dumdum.interfaces.Cache;
import main.com.dumdum.model.CacheElement;

import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCacheImpl<K, V> implements Cache<K, V> {

    private int size;

    private Map<K, CacheElement<K,V>> llNodeMap;
//
    private LinkedList<CacheElement<K, V>> doublyLL;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public LRUCacheImpl(int size) {
        this.size = size;
        this.doublyLL = new LinkedList<>();
        this.llNodeMap = new ConcurrentHashMap<>(size);
    }

    @Override
    public Optional<V> get(K key) {
        this.lock.readLock().lock();
        try{
            CacheElement<K, V> llNode = this.llNodeMap.get(key);
            if(llNode == null){
                return Optional.empty();
            } else{
                this.doublyLL.remove(llNode);
                this.doublyLL.addFirst(llNode);
                CacheElement<K,V> element = this.doublyLL.getFirst();
                llNodeMap.put(key,element);
                return Optional.of(llNode.getValue());
            }
        } finally {
            this.lock.readLock().unlock();
        }
    }

    @Override
    public boolean set(K key, V value) {
        this.lock.writeLock().lock();
        try{
            CacheElement<K, V> item = new CacheElement<>(key, value);
            CacheElement<K, V> newLLNode;
            if(this.llNodeMap.containsKey(key)){
                this.doublyLL.remove(llNodeMap.get(key));
                this.doublyLL.addFirst(item);
                newLLNode = this.doublyLL.getFirst();
            } else {
                if (this.size() >= this.size) {
                   this.evictElement();
                }
                this.doublyLL.addFirst(item);
                newLLNode = this.doublyLL.getFirst();
            }
//            if(newLLNode.isEmpty()){
//                return false;
//            }
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
            CacheElement<K, V> element = doublyLL.removeLast();
            llNodeMap.remove(element.getKey());
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
