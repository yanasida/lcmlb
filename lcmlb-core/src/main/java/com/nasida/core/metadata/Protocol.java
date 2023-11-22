package com.nasida.core.metadata;

/**
 * @author yanasida
 */
public interface Protocol {

    /**
     * 获取协议版本
     *
     * @return 协议版本
     */
    String getVersion();

    /**
     * 获取协议名称
     *
     * @return 协议名称
     */
    String getName();

    /**
     * 获取请求头长度
     *
     * @return 请求头
     */
    Integer getHeadLength();

}
