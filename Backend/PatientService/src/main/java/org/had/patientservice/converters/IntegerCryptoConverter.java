package org.had.accountservice.converters;


import jakarta.persistence.Converter;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

@Converter
public class IntegerCryptoConverter extends AbstractCryptoConverter<Integer>{

    public IntegerCryptoConverter() {
        this(new CipherInitializer());
    }

    public IntegerCryptoConverter(CipherInitializer cipherInitializer) {
        super(cipherInitializer);
    }

    @Override
    boolean isNotNullOrEmpty(Integer attribute) {
        return attribute != null;
    }

    @Override
    Integer stringToEntityAttribute(String dbData) {
        return isEmpty(dbData) ? null : Integer.parseInt(dbData);
    }

    @Override
    String entityAttributeToString(Integer attribute) {
        return attribute == null ? null : attribute.toString();
    }
}
