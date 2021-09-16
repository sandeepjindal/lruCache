package main.com.dumdum.model;

import main.com.dumdum.interfaces.LinkedListNode;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DoublyLinkedList<V> {

    private DummyNode<V> dummyNode;
    private LinkedListNode<V> head;
    private LinkedListNode<V> tail;
    
    private AtomicInteger size;
    
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public DoublyLinkedList() {
        this.dummyNode = new DummyNode<V>(this);
        clear();
    }

    public void clear() {
        this.lock.writeLock().lock();
        try{
            head = dummyNode;
            tail = dummyNode;
            size = new AtomicInteger(0);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public int size() {
        this.lock.readLock().lock();
        try {
            return size.get();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    public boolean isEmpty() {
        this.lock.readLock().lock();
        try {
            return head.isEmpty();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    public boolean contains(V value) {
        this.lock.readLock().lock();
        try {
            return search(value).hasElement();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    public LinkedListNode<V> search(V value) {
        this.lock.readLock().lock();
        try {
            return head.search(value);
        } finally {
            this.lock.readLock().unlock();
        }
    }

    public LinkedListNode<V> add(V value) {
        this.lock.writeLock().lock();
        try {
            head = new Node<V>(value,this,head);
            if(tail.isEmpty()){
                tail = head;
            }
            size.incrementAndGet();
            return head;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public boolean addAll(Collection<V> values) {
        this.lock.writeLock().lock();
        try {
            for(V value: values){
                if(add(value).isEmpty()) {
                    return false;
                }
            }
            return true;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public LinkedListNode<V> remove(V value) {
        this.lock.writeLock().lock();
        try {
            LinkedListNode<V> llNode = head.search(value);

            if(!llNode.isEmpty()){
                if(llNode == tail){
                    tail = tail.getPrev();
                }
                if(llNode == head){
                    head = head.getNext();
                }
                llNode.detach();
                size.decrementAndGet();
            }
            return llNode;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public LinkedListNode<V> removeTail() {
        this.lock.writeLock().lock();
        try {
            LinkedListNode<V> oldTail = tail;
            if(oldTail == head){
                tail = head = dummyNode;
            } else{
                tail = tail.getPrev();
                oldTail.detach();
            }

            if(!oldTail.isEmpty()){
                tail = tail.getPrev();
                size.decrementAndGet();
            }
            return oldTail;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public LinkedListNode<V> moveToFront(LinkedListNode<V> node) {
        return node.isEmpty() ? dummyNode : updateAndMoveToFront(node, node.getElement());
    }

    public LinkedListNode<V> updateAndMoveToFront(LinkedListNode<V> node, V element) {
        this.lock.writeLock().lock();
        try {
            if(node.isEmpty() || (this != dummyNode.getListReference())){
                return dummyNode;
            }
            detach(node);
            add(element);
            return head;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public void detach(LinkedListNode<V> node) {
        if(node != tail){
            node.detach();
            if(node == head){
                head= head.getNext();
            }
            size.decrementAndGet();
        } else{
            removeTail();
        }
    }


}
