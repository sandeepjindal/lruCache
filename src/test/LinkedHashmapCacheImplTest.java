package test;

import main.com.dumdum.LRUCacheImpl;
import main.com.dumdum.LinkedHashMapLRUCache;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class LinkedHashmapCacheImplTest {

    @Test
    public void addSomeDataToCache_WhenGetData_ThenIsEqualWithCacheElement() {
        LinkedHashMapLRUCache<String, String> lRUCacheImpl = new LinkedHashMapLRUCache<>(3);
        lRUCacheImpl.set("1", "test1");
        lRUCacheImpl.set("2", "test2");
        lRUCacheImpl.set("3", "test3");
        assertEquals("test1", lRUCacheImpl.get("1").get());
        assertEquals("test2", lRUCacheImpl.get("2").get());
        assertEquals("test3", lRUCacheImpl.get("3").get());
    }

    @Test
    public void addDataToCacheToTheNumberOfSize_WhenAddOneMoreData_ThenLeastRecentlyDataWillEvict() {
        LinkedHashMapLRUCache<String, String> lruCache = new LinkedHashMapLRUCache<>(3);
        lruCache.set("1", "test1");
        lruCache.set("2", "test2");
        lruCache.set("3", "test3");
        lruCache.set("4", "test4");
        assertFalse(lruCache.get("1").isPresent());
    }

    @Test
    public void runMultiThreadTask_WhenPutDataInConcurrentToCache_ThenNoDataLost() throws Exception {
        final int size = 50;
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        LinkedHashMapLRUCache<Integer, String> cache = new LinkedHashMapLRUCache<>(size);
        CountDownLatch countDownLatch = new CountDownLatch(size);
        try {
            IntStream.range(0, size).<Runnable>mapToObj(key -> () -> {
                cache.set(key, "value" + key);
                countDownLatch.countDown();
            }).forEach(executorService::submit);
            countDownLatch.await();
        } finally {
            executorService.shutdown();
        }
        assertEquals(cache.size(), size);
        IntStream.range(0, size).forEach(i -> assertEquals("value" + i, cache.get(i).get()));
    }
}