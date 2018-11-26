/*
  User: Cloudy
  Date: 15-Nov-18
  Time: 15:24
*/

package cz.cloudy.fxengine.core;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cache {
    private static Map<Object, Map<Object, Object>> cache;
    private static boolean                          caching;

    static {
        cache = new LinkedHashMap<>();
        caching = true;
    }

    private Cache() {}

    /**
     * Will get object from Cache storage if exists in specified section and is registered under set key.
     *
     * @param section Section where keys are stored
     * @param key     Key where value is registered
     * @param <T>     The final type of received object
     * @return Object stored in section under some key
     */
    public static <T> T get(Object section, Object key) {
        if (!caching) return null;

        if (!cache.containsKey(section)) {
            throw new RuntimeException("This cache section doesn't exist.");
        }
        Map<Object, Object> sec = cache.get(section);
        if (!sec.containsKey(key)) {
            throw new RuntimeException("This cache key doesn't exist.");
        }
        //System.out.println("Found: " + section.toString() + ", " + key.toString());
        return (T) sec.get(key);
    }

    /**
     * Cache object to the section under specified key.
     *
     * @param section Section where keys are stored
     * @param key     Key where value will be registered
     * @param value   Value which will be stored
     */
    public static void cache(Object section, Object key, Object value) {
        if (!caching) return;

        Map<Object, Object> sec;
        if (!cache.containsKey(section)) {
            cache.put(section, new LinkedHashMap<>());
        }
        sec = cache.get(section);
        if (sec.containsKey(key)) {
            throw new RuntimeException("This cache key is already used.");
        }
        sec.put(key, value);
        //System.out.println("Cached: " + section.toString() + ", " + key.toString());
    }

    /**
     * Checks if value in section under key exist.
     *
     * @param section Section where keys are stored
     * @param key     Key where value is registered
     * @return Boolean if value exists under specified conditions
     */
    public static boolean contains(Object section, Object key) {
        if (!caching) return false;

        if (!cache.containsKey(section)) return false;
        if (!cache.get(section)
                  .containsKey(key)) return false;
        return true;
    }

    /**
     * Enables or disables caching.
     *
     * @param cache The trigger
     */
    public static void cache(boolean cache) {
        caching = cache;
    }

    /**
     * Returns the value if caching is enabled.
     *
     * @return Boolean if caching is enabled
     */
    public static boolean isCaching() {
        return caching;
    }
}
