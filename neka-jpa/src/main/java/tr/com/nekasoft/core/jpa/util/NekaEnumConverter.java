package tr.com.nekasoft.core.jpa.util;


import tr.com.nekasoft.core.common.data.enumeration.NekaEnum;

import javax.persistence.AttributeConverter;

public abstract class NekaEnumConverter<T extends NekaEnum> implements AttributeConverter<T, Integer> {

    @Override
    public Integer convertToDatabaseColumn(T attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.code();
    }
}
