package com.nasida.core.manager;

import com.nasida.core.connector.Connector;
import com.nasida.core.connector.HeartCheckConnector;

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

public class LimitConnectorManager<K> extends HeartCheckManager<K> implements ConnectorManager<K> {

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

    public LimitConnectorManager(int maxCapacity, TimeUnit durationUnit, long checkDuration, long delay) {
        super(durationUnit, checkDuration, delay);
        if (maxCapacity < 1) throw new IllegalArgumentException();
        this.cache = new ConcurrentHashMap<>(maxCapacity);
        this.maxCapacity = maxCapacity;
    }

    @Override
    public boolean put(K key, Connector connector) {
        if (key == null || connector == null) throw new NullPointerException();
        if (maxCapacity <= cache.size()) {
            throw new IllegalStateException("The current capacity has reached the maximum quantity and no further additions can be made!");
        }
        cache.put(key, connector);
        return true;
    }

    @Override
    public Connector remove(K key) {
        if (key == null) throw new NullPointerException();
        return cache.remove(key);
    }

    @Override
    public Connector get(K key) {
        if (key == null) throw new NullPointerException();
        return cache.get(key);
    }

    @Override
    public Connector getActiveOne(K key) {
        Connector connector = get(key);
        if (connector.isActive()) {
            return connector;
        }
        return null;
    }

    @Override
    public List<Connector> getAll() {
        if (cache.isEmpty()) {
            return null;
        }
        return new ArrayList<>(cache.values());
    }

    @Override
    public boolean contains(K key) {
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
        System.out.println("heart beat check!");
        System.out.println(cache.values());
        cache.entrySet().removeIf(entry -> {
            Connector connector = entry.getValue();
            boolean active = connector.isActive();
            if (!active) {
                connector.close();
                System.out.println("关闭连接："+entry.getKey());
                return true;
            }
            return false;
        });
    }
}
