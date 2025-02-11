package com.llm.agents.core.chain.convert;

public class ByteConverter  implements IConverter<Byte> {
    @Override
    public Byte convert(String text) {
        return Byte.parseByte(text);
    }
}
