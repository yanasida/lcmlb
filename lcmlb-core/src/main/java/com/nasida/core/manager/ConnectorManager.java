package com.nasida.core.manager;

import com.nasida.core.connector.Connector;

import java.util.List;

/**
 * @author yanasida
 * @date 2023/11/22 15:19
 * <p>
 * 管理器
 */
public interface ConnectorManager<K> {

    boolean put(K key, Connector connector);

    Connector remove(K key);

    Connector get(K key);

    Connector getActiveOne(K key);

    List<Connector> getAll();

    boolean contains(K key);

    Integer size();
}
