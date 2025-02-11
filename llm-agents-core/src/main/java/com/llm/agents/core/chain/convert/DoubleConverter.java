package com.llm.agents.core.chain.convert;

public class DoubleConverter implements IConverter<Double> {
    @Override
    public Double convert(String text) {
        return Double.parseDouble(text);
    }
}
