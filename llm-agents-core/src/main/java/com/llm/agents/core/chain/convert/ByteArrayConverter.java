package com.llm.agents.core.chain.convert;

public class ByteArrayConverter implements IConverter<byte[]> {
    @Override
    public byte[] convert(String text) {
        return text.getBytes();
    }
}
