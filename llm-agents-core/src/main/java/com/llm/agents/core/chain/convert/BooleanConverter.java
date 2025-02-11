package com.llm.agents.core.chain.convert;

public class BooleanConverter implements IConverter<Boolean> {
    @Override
    public Boolean convert(String text) {
        String value = text.toLowerCase();
        if ("true".equals(value) || "1".equals(value)) {
            return Boolean.TRUE;
        } else if ("false".equals(value) || "0".equals(value)) {
            return Boolean.FALSE;
        } else {
            throw new RuntimeException("Can not parse to boolean type of value: " + text);
        }
    }
}
