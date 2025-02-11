package com.llm.agents.core.chain.convert;

public class LongConverter  implements IConverter<Long> {
    @Override
    public Long convert(String text) {
        return Long.parseLong(text);
    }
}

