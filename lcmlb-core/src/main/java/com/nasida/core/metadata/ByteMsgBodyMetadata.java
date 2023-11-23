package com.nasida.core.metadata;

import java.util.List;

/**
 * @author yanasida
 * @date 2023/11/22 14:49
 */
public abstract class ByteMsgBodyMetadata<T> {
    private List<FieldMetadata> fieldList;

    private T realData;
}
