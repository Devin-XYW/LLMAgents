package com.llm.agents.core.chain.convert;

public class FloatConverter implements IConverter<Float> {
    @Override
    public Float convert(String text) {
        return Float.parseFloat(text);
    }
}
