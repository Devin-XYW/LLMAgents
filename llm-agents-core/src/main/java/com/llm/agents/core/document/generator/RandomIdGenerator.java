package com.llm.agents.core.document.generator;

import com.llm.agents.core.document.Document;

import java.util.UUID;

/**
 * @Author Devin
 * @Date 2024/12/16 22:14
 * @Description:
 **/
public class RandomIdGenerator implements DocumentIdGenerator {
    @Override
    public Object generateId(Document document) {
        return UUID.randomUUID().toString();
    }
}
