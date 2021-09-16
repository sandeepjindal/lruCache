import main.com.dumdum.BasicCacheImpl;
import main.com.dumdum.LRUCacheImpl;

public class Main {

    public static void main(String[] args) {
	    BasicCacheImpl<String,String> cache = new BasicCacheImpl<>();

	    cache.set("1","val1");

	    System.out.println("value is: " + cache.get("2").orElseGet(() -> "not present"));

		LRUCacheImpl<String,String> lruCache = new LRUCacheImpl<>(2);

		lruCache.set("1","val1");
		System.out.println("value is: " + lruCache.get("1").orElseGet(() -> "not present"));
		lruCache.set("2","val2");
		System.out.println("value is: " + lruCache.get("2").orElseGet(() -> "not present"));
		System.out.println("value is: " + lruCache.get("1").orElseGet(() -> "not present"));
		lruCache.set("3","val2");
		System.out.println("value is: " + lruCache.get("2").orElseGet(() -> "not present"));
		System.out.println("value is: " + lruCache.get("1").orElseGet(() -> "not present"));
    }
}
