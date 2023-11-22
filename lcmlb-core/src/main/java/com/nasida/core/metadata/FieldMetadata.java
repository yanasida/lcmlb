package com.nasida.core.metadata;

/**
 * @author yanasida
 * @date 2023/11/22 14:42
 * <p>
 * 字段原数据：对于序列化处理直接按位编码的解析使用
 */
public abstract class FieldMetadata {

    /**
     * 字段名称,即变量名
     */
    protected String fieldName;

    /**
     * 字段描述
     */
    protected String description;

    /**
     * 数据类型（Integer, Short, Long, Double, String）
     */
    protected String dataType;

    /**
     * 编码数据长度
     */
    protected Integer encodeLength;

    /**
     * 编码类型: BIN、BCD、HEX
     */
    protected String encodeType;
}
