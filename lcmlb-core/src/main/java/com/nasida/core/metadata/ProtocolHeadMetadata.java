package com.nasida.core.metadata;

import lombok.ToString;

/**
 * @author yanasida
 * @date 2023/11/22 15:07
 * <p>
 * 协议头部
 */
@ToString
public abstract class ProtocolHeadMetadata {
    /**
     * 头部标识,魔数
     */
    private String headMagic;

    /**
     * 版本号
     */
    private String version;

    /**
     * 业务命令类型
     */
    private short cmdType;

    /**
     * 数据长度
     */
    private short dataLength;

    public ProtocolHeadMetadata(String headMagic, String version, short cmdType) {
        this.headMagic = headMagic;
        this.version = version;
        this.cmdType = cmdType;
    }

    public String getHeadMagic() {
        return headMagic;
    }

    public String getVersion() {
        return version;
    }

    public short getCmdType() {
        return cmdType;
    }

    public short getDataLength() {
        return dataLength;
    }

    public abstract short getHeaderLength();
}
