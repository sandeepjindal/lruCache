package com.company.src.main.model;

import com.company.src.main.interfaces.LinkedListNode;

public class Node<V> implements LinkedListNode<V> {

    private V value;

    private DoublyLinkedList<V> list;

    private LinkedListNode<V> prev;

    private LinkedListNode<V> next;

    public Node(V value, DoublyLinkedList<V> list, LinkedListNode<V> next) {
        this.value = value;
        this.list = list;
        this.setPrev(next.getPrev());
        this.prev.setNext(this);
        this.next = next;
        this.next.setPrev(this);
    }

    @Override
    public boolean hasElement() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public V getElement() throws NullPointerException {
        return value;
    }

    @Override
    public void detach() {
        this.prev.setNext(this.getNext());
        this.next.setPrev(this.getPrev());
    }

    @Override
    public DoublyLinkedList<V> getListReference() {
        return this.list;
    }

    @Override
    public LinkedListNode<V> setPrev(LinkedListNode<V> prev) {
        this.prev = prev;
        return this;
    }

    @Override
    public LinkedListNode<V> setNext(LinkedListNode<V> next) {
        this.next=next;
        return this;
    }

    @Override
    public LinkedListNode<V> getPrev() {
        return this.prev;
    }

    @Override
    public LinkedListNode<V> getNext() {
        return this.next;
    }

    @Override
    public LinkedListNode<V> search(V value) {
        return this.getElement() == value ? this : this.getNext().search(value);
    }
}
