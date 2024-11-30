package com.llm.agents.core.convert;

public class ByteArrayConverter implements IConverter<byte[]> {
    @Override
    public byte[] convert(String text) {
        return text.getBytes();
    }
}
