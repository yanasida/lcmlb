package com.nasida.core.connector;

/**
 * @author yanasida
 */
public interface Connector {

    /**
     * 建立连接
     *
     * @param host 主机名或IP地址
     * @param port 端口号
     * @throws Exception 连接建立失败时抛出异常
     */
    boolean connect(String host, int port) throws Exception;

    /**
     * 关闭连接
     *
     */
    boolean close();

    /**
     * 是否可用
     *
     * @return boolean
     */
    boolean isActive();
}
