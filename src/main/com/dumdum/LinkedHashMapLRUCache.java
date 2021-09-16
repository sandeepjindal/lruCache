package main.com.dumdum;

import main.com.dumdum.interfaces.Cache;
import main.com.dumdum.model.CacheElement;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class LinkedHashMapLRUCache<K,V> implements Cache<K,V> {

    private LinkedHashMap<K,V> linkedHashMap;

    private final int CAPACITY;

    public LinkedHashMapLRUCache(int capacity) {
        linkedHashMap = new LinkedHashMap<K,V>(){
            public boolean removeEldestEntry(Map.Entry<K,V> eldest) {
                return size() > CAPACITY;
            }
        };

        CAPACITY = capacity;
    }


    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(linkedHashMap.get(key));
    }

    @Override
    public boolean set(K key, V value) {
        if(linkedHashMap.put(key, value) == null){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public int size() {
        return linkedHashMap.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

    }
}
