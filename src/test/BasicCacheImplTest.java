package test;

import main.com.dumdum.BasicCacheImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class BasicCacheImplTest {

    @Test
    public void simpleTestToGetKey() throws Exception {

        BasicCacheImpl cache = new BasicCacheImpl();

        cache.set("1","val1");

        assertEquals("val1", cache.get("1").orElse("not present"));

    }


    @Test
    public void simpleTestToSetAlreadyPresentKey() {

        BasicCacheImpl cache = new BasicCacheImpl();

        cache.set("1","val1");

        assertEquals(false, cache.set("1","val1"));

    }

    @Test
    public void simpleTestToGetKeyNotPresent(){

        BasicCacheImpl cache = new BasicCacheImpl();

        cache.set("1","val1");

        assertEquals("not present", cache.get("2").orElse("not present"));

    }
}