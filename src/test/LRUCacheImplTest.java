package test;

import main.com.dumdum.LRUCacheImpl;
import org.junit.Test;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import static org.junit.Assert.*;

public class LRUCacheImplTest {

    @Test
    public void addSomeDataToCache_WhenGetData_ThenIsEqualWithCacheElement() {
        LRUCacheImpl<String, String> lRUCacheImpl = new LRUCacheImpl<>(3);
        lRUCacheImpl.setKey("1", "test1");
        lRUCacheImpl.setKey("2", "test2");
        lRUCacheImpl.setKey("3", "test3");
        assertEquals("test1", lRUCacheImpl.getKey("1").get());
        assertEquals("test2", lRUCacheImpl.getKey("2").get());
        assertEquals("test3", lRUCacheImpl.getKey("3").get());
    }

    @Test
    public void addDataToCacheToTheNumberOfSize_WhenAddOneMoreData_ThenLeastRecentlyDataWillEvict() {
        LRUCacheImpl<String, String> LRUCacheImpl = new LRUCacheImpl<>(3);
        LRUCacheImpl.setKey("1", "test1");
        LRUCacheImpl.setKey("2", "test2");
        LRUCacheImpl.setKey("3", "test3");
        LRUCacheImpl.setKey("4", "test4");
        assertFalse(LRUCacheImpl.getKey("1").isPresent());
    }

    @Test
    public void runMultiThreadTask_WhenPutDataInConcurrentToCache_ThenNoDataLost() throws Exception {
        final int size = 50;
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        LRUCacheImpl<Integer, String> cache = new LRUCacheImpl<>(size);
        CountDownLatch countDownLatch = new CountDownLatch(size);
        try {
            IntStream.range(0, size).<Runnable>mapToObj(key -> () -> {
                cache.setKey(key, "value" + key);
                countDownLatch.countDown();
            }).forEach(executorService::submit);
            countDownLatch.await();
        } finally {
            executorService.shutdown();
        }
        assertEquals(cache.size(), size);
        IntStream.range(0, size).forEach(i -> assertEquals("value" + i, cache.getKey(i).get()));
    }
}