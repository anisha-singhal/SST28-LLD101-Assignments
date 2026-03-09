package com.example.metrics;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// thread-safe singleton using the static holder pattern (Bill Pugh approach)
// also guards against reflection and serialization breaking the singleton
public class MetricsRegistry implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static boolean alreadyCreated = false;

    private final Map<String, Long> counters = new HashMap<>();

    // private constructor - if someone tries reflection we throw
    private MetricsRegistry() {
        if (alreadyCreated) {
            throw new RuntimeException("Cannot create another instance, use getInstance()");
        }
        alreadyCreated = true;
    }

    // inner class only gets loaded when getInstance() is called first time
    // and class loading itself is thread safe so we dont need synchronized
    private static class Holder {
        private static final MetricsRegistry INSTANCE = new MetricsRegistry();
    }

    public static MetricsRegistry getInstance() {
        return Holder.INSTANCE;
    }

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

    // makes sure deserialization gives back the same singleton
    @Serial
    private Object readResolve() {
        return getInstance();
    }
}
