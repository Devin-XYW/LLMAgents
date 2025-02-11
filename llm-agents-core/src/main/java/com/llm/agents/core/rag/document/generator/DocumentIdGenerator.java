package com.llm.agents.core.rag.document.generator;

import com.llm.agents.core.rag.document.Document;

/**
 * @Author Devin
 * @Date 2024/12/11 23:10
 * @Description:
 **/
public interface DocumentIdGenerator {
    Object generateId(Document document);
}
