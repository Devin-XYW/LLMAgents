package com.llm.agents.core.chain.convert;

public class BigDecimalConverter implements IConverter<java.math.BigDecimal> {
    @Override
    public java.math.BigDecimal convert(String text) {
        return new java.math.BigDecimal(text);
    }
}

