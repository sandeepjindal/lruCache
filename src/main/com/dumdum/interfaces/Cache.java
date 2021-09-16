package main.com.dumdum.interfaces;

import java.util.Optional;

public interface Cache<K,V> {

    public Optional<V> get(K key);

    public boolean set(K key, V value);

    int size();

    boolean isEmpty();

    void clear();

}
