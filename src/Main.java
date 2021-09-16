import main.BasicCacheImpl;
import main.LRUCacheImpl;

public class Main {

    public static void main(String[] args) {
	    BasicCacheImpl<String,String> cache = new BasicCacheImpl<>();

	    cache.setKey("1","val1");

	    System.out.println("value is: " + cache.getKey("2").orElseGet(() -> "not present"));

		LRUCacheImpl<String,String> lruCache = new LRUCacheImpl<>(2);

		lruCache.setKey("1","val1");
		System.out.println("value is: " + lruCache.getKey("1").orElseGet(() -> "not present"));
		lruCache.setKey("2","val2");
		System.out.println("value is: " + lruCache.getKey("2").orElseGet(() -> "not present"));
		System.out.println("value is: " + lruCache.getKey("1").orElseGet(() -> "not present"));
		lruCache.setKey("3","val2");
		System.out.println("value is: " + lruCache.getKey("2").orElseGet(() -> "not present"));
		System.out.println("value is: " + lruCache.getKey("1").orElseGet(() -> "not present"));
    }
}
