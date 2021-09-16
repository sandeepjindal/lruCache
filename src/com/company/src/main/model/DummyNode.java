package com.company.src.main.model;

import com.company.src.main.interfaces.LinkedListNode;

public class DummyNode<V> implements LinkedListNode<V> {

    private DoublyLinkedList<V> list;

    public DummyNode(DoublyLinkedList<V> list) {
        this.list = list;
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
        throw new NullPointerException();
    }

    @Override
    public void detach() {
        return;
    }

    @Override
    public DoublyLinkedList<V> getListReference() {
        return list;
    }

    @Override
    public LinkedListNode<V> setPrev(LinkedListNode<V> prev) {
        return prev;
    }

    @Override
    public LinkedListNode<V> setNext(LinkedListNode<V> next) {
        return next;
    }

    @Override
    public LinkedListNode<V> getPrev() {
        return this;
    }

    @Override
    public LinkedListNode<V> getNext() {
        return this;
    }

    @Override
    public LinkedListNode<V> search(V value) {
        return this;
    }
}
