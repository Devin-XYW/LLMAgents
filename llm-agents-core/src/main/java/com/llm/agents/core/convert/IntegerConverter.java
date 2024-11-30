package com.llm.agents.core.convert;

public class IntegerConverter implements IConverter<Integer>{
    @Override
    public Integer convert(String text) throws ConvertException {
        return Integer.parseInt(text);
    }
}
