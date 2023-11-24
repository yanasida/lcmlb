package com.nasida.core.msg;

import com.nasida.core.metadata.ProtocolHeadMetadata;
import lombok.ToString;

/**
 * @author nasida
 * @date 2023/11/24 13:53
 */
@ToString(callSuper = true)
public class DefaultMessage<T> extends ProtocolHeadMetadata {

    public static final String DEFAULT_VERSION = "1.0";

    public static final String DEFAULT_MAGIC = "defaultMagic";

    public static final short HEADER_LENGTH = 16;

    private T data;

    public DefaultMessage(short cmdType, T data) {
        super(DEFAULT_MAGIC, DEFAULT_VERSION, cmdType);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public short getHeaderLength() {
        return HEADER_LENGTH;
    }
}
