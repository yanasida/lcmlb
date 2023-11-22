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
    void connect(String host, int port) throws Exception;

    /**
     * 发送数据
     *
     * @param data 要发送的数据
     * @throws Exception 发送数据失败时抛出异常
     */
    void send(Object data) throws Exception;

    /**
     * 接收数据
     *
     * @return 接收到的数据
     * @throws Exception 接收数据失败时抛出异常
     */
    Object receive() throws Exception;

    /**
     * 关闭连接
     *
     * @throws Exception 关闭连接失败时抛出异常
     */
    void close() throws Exception;

    /**
     * 是否可用
     *
     * @return boolean
     */
    boolean isActive();
}
