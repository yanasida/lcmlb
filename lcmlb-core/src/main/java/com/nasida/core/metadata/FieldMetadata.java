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
    private String fieldName;

    /**
     * 字段描述
     */
    private String description;

    /**
     * 数据类型（Integer, Short, Long, Double, String）
     */
    private String dataType;

    /**
     * 编码数据长度
     */
    private Integer encodeLength;

    /**
     * 编码类型: BIN、BCD、HEX
     */
    private String encodeType;
}
