package com.nasida.core.metadata;

/**
 * @author yanasida
 * @date 2023/11/22 14:26
 * <p>
 * 协议元数据
 */
public abstract class ProtocolMetadata implements Protocol {

    /**
     * 协议编号
     */
    private String protocolCode;

    /**
     * 协议版本
     */
    private String protocolVersion;

    /**
     * 序列化方式
     */
    private String serializationType;

    /**
     * 魔数
     */
    private String magic;

    /**
     * 协议头部长度
     */
    private Integer headLength;
}
