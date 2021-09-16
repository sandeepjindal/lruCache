package main;

import main.interfaces.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BasicCacheImpl<K,V> implements Cache<K,V> {

    private Map<K,V> cache = new HashMap<>();

    @Override
    public boolean setKey(K key, V value) {
        return !Optional.ofNullable(cache.put(key, value)).isPresent();
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @Override
    public void clear() {
        cache.clear();
    }


    @Override
    public Optional<V> getKey(K key) {
        return Optional.ofNullable(cache.get(key));
    }

    public BasicCacheImpl(Map<K, V> cache) {
        this.cache = cache;
    }

    public BasicCacheImpl() {

    }

    @Override
    public int hashCode() {
        return cache != null ? cache.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicCacheImpl<K, V> that = (BasicCacheImpl<K, V>) o;

        return cache != null ? cache.equals(that.cache) : that.cache == null;
    }
}
