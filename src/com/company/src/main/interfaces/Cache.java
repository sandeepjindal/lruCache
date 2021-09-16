package com.company.src.main.interfaces;

import java.util.Optional;

public interface Cache<K,V> {

    public Optional<V> getKey(K key);

    public boolean setKey(K key, V value);

    int size();

    boolean isEmpty();

    void clear();


}
