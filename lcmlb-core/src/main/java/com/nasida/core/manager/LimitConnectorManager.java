package com.nasida.core.manager;

import com.nasida.core.connector.Connector;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author yanasida
 * @date 2023/11/22 15:23
 * <p>
 * 键值链接管理器
 */

public class LimitConnectorManager<K> extends TimeCheckedManager<K> implements ConnectorManager<K> {

    private static final int MAX_CAPACITY = 20;

    private final transient ConcurrentHashMap<K, Connector> cache;

    private final int maxCapacity;

    public LimitConnectorManager() {
        super();
        this.cache = new ConcurrentHashMap<>(MAX_CAPACITY);
        this.maxCapacity = MAX_CAPACITY;
    }

    public LimitConnectorManager(int maxCapacity) {
        super();
        if (maxCapacity < 1) throw new IllegalArgumentException();
        this.cache = new ConcurrentHashMap<>(maxCapacity);
        this.maxCapacity = maxCapacity;
    }

    public LimitConnectorManager(int maxCapacity, @NotNull TimeUnit durationUnit, long checkDuration, long delay) {
        super(durationUnit, checkDuration, delay);
        if (maxCapacity < 1) throw new IllegalArgumentException();
        this.cache = new ConcurrentHashMap<>(maxCapacity);
        this.maxCapacity = maxCapacity;
    }

    @Override
    public boolean put(@NotNull K key, @NotNull Connector connector) {
        if (key == null || connector == null) throw new NullPointerException();
        if (maxCapacity <= cache.size()) {
            throw new IllegalStateException("The current capacity has reached the maximum quantity and no further additions can be made!");
        }
        cache.put(key, connector);
        return true;
    }

    @Override
    public Connector remove(@NotNull K key) {
        if (key == null) throw new NullPointerException();
        return cache.remove(key);
    }

    @Override
    public Connector get(@NotNull K key) {
        if (key == null) throw new NullPointerException();
        return cache.get(key);
    }

    @Override
    public List<Connector> getAll() {
        if (cache.isEmpty()) {
            return null;
        }
        return new ArrayList<>(cache.values());
    }

    @Override
    public boolean contains(@NotNull K key) {
        if (key == null) throw new NullPointerException();
        return cache.containsKey(key);
    }

    @Override
    public Integer size() {
        return cache.size();
    }

    public Map<K, Connector> getRealData() {
        return cache;
    }

    @Override
    void check() {
        cache.entrySet().removeIf(entry -> {
            Connector connector = entry.getValue();
            return !connector.isActive();
        });
    }
}
