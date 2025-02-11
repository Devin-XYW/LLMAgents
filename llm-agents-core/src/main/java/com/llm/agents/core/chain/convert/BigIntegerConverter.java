package com.llm.agents.core.chain.convert;

public class BigIntegerConverter implements IConverter<java.math.BigInteger> {
    @Override
    public java.math.BigInteger convert(String text) {
        return new java.math.BigInteger(text);
    }
}
