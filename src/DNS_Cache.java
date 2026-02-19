/*
 * Project: DNS Cache with TTL
 * Author: Kanishk Upadhyay
 * GitHub: https://github.com/kanishkupadhyay1
 * Created: February 2026
 * Version: 1.0
 * Repository: Step_B1P17
 *
 * Description:
 * Developed a Java-based DNS cache with TTL expiration
 * and LRU eviction using LinkedHashMap,
 * enabling O(1) domain lookup and improving resolution
 * performance with hit/miss tracking.
 *
 *
 * © 2026 Kanishk Upadhyay. All rights reserved.
 */


import java.util.*;

// Cache Entry class
class Cache {

    long expiryTime;
    String ipAddress;

    public Cache(String ipAddress, long ttlSeconds) {

        this.ipAddress = ipAddress;

        // convert TTL seconds → milliseconds
        this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
    }

    public boolean isExpired() {

        return System.currentTimeMillis() > expiryTime;
    }
}


// DNS Cache class
public class DNS_Cache {

    private final int capacity;

    private LinkedHashMap<String, Cache> cache;

    private int hits = 0;
    private int miss = 0;


    public DNS_Cache(int capacity) {

        this.capacity = capacity;

        this.cache = new LinkedHashMap<String, Cache>(capacity, 0.75f, true) {

            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Cache> eldest) {

                return size() > DNS_Cache.this.capacity;
            }
        };
    }


    // Resolve domain
    public synchronized String resolve(String domain) {

        Cache entry = cache.get(domain);

        // Cache HIT
        if (entry != null && !entry.isExpired()) {

            hits++;

            System.out.println("Cache HIT for " + domain);

            return entry.ipAddress;
        }

        // Cache MISS
        miss++;

        System.out.println("Cache MISS for " + domain);

        String ip = queryUpstreamDNS(domain);

        cache.put(domain, new Cache(ip, 5)); // TTL = 5 seconds

        return ip;
    }


    // Simulate DNS lookup
    private String queryUpstreamDNS(String domain) {

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "192.168.1." + new Random().nextInt(255);
    }


    // Cleanup expired entries
    public synchronized void cleanup() {

        Iterator<Map.Entry<String, Cache>> iterator =
                cache.entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry<String, Cache> entry = iterator.next();

            if (entry.getValue().isExpired()) {

                System.out.println("Removing expired: " + entry.getKey());

                iterator.remove();
            }
        }
    }


    // Hit ratio
    public double getHitRatio() {

        int total = hits + miss;

        if (total == 0)
            return 0;

        return (double) hits / total;
    }


    // Print stats
    public void printStats() {

        System.out.println("Cache Hits: " + hits);
        System.out.println("Cache Misses: " + miss);
        System.out.println("Hit Ratio: " + getHitRatio());
    }


    // Main method
    public static void main(String[] args) throws InterruptedException {

        DNS_Cache dns = new DNS_Cache(3);

        System.out.println(dns.resolve("google.com"));

        System.out.println(dns.resolve("google.com")); // HIT

        System.out.println(dns.resolve("facebook.com"));

        Thread.sleep(6000); // wait forexpiry

        System.out.println(dns.resolve("google.com")); // MISS

        dns.cleanup();

        dns.printStats();
    }
}
