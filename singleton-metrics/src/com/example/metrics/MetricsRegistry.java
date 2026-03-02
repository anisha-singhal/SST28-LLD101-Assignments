package com.example.metrics;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Global metrics registry — proper thread-safe Singleton.
 *
 * Uses the "initialization-on-demand holder" idiom for lazy, safe init.
 * Also guards against reflection and serialization attacks.
 */
public class MetricsRegistry implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // flag to detect if someone tries to construct a second instance via reflection
    private static boolean instanceCreated = false;

    private final Map<String, Long> counters = new HashMap<>();

    // private ctor — blocks direct instantiation and reflection attacks
    private MetricsRegistry() {
        if (instanceCreated) {
            throw new RuntimeException(
                "MetricsRegistry is a singleton — use getInstance()");
        }
        instanceCreated = true;
    }

    /**
     * Bill Pugh holder — JVM guarantees the inner class is loaded
     * only when getInstance() is first called, and class loading
     * is inherently thread-safe, so no synchronization needed.
     */
    private static class Holder {
        private static final MetricsRegistry INSTANCE = new MetricsRegistry();
    }

    public static MetricsRegistry getInstance() {
        return Holder.INSTANCE;
    }

    // --- business methods (unchanged) ---

    public synchronized void setCount(String key, long value) {
        counters.put(key, value);
    }

    public synchronized void increment(String key) {
        counters.put(key, getCount(key) + 1);
    }

    public synchronized long getCount(String key) {
        return counters.getOrDefault(key, 0L);
    }

    public synchronized Map<String, Long> getAll() {
        return Collections.unmodifiableMap(new HashMap<>(counters));
    }

    /** Ensures deserialization returns the existing singleton. */
    @Serial
    private Object readResolve() {
        return getInstance();
    }
}
