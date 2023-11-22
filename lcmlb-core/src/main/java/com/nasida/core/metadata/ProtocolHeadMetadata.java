package com.nasida.core.metadata;

/**
 * @author yanasida
 * @date 2023/11/22 15:07
 * <p>
 * 协议头部
 */
public abstract class ProtocolHeadMetadata {
    /**
     * 头部标识,魔数
     */
    protected String headMagic;

    /**
     * 版本号
     */
    protected String version;

    /**
     * 业务命令类型
     */
    protected short cmdType;

    /**
     * 数据长度
     */
    protected short dataLength;
}
