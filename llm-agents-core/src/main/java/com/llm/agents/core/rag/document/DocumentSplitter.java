package com.llm.agents.core.rag.document;

import static java.util.stream.Collectors.toList;

import com.llm.agents.core.rag.document.generator.DocumentIdGenerator;

import java.util.Collections;
import java.util.List;

/**
 * @Author Devin
 * @Date 2024/12/11 23:10
 * @Description:
 **/
public interface DocumentSplitter {
    List<Document> split(Document text, DocumentIdGenerator idGenerator);

    default List<Document> split(Document text) {
        return split(text, null);
    }

    default List<Document> splitAll(List<Document> documents, DocumentIdGenerator idGenerator) {
        if (documents == null || documents.isEmpty()) {
            return Collections.emptyList();
        }
        return documents.stream()
                .flatMap(document -> split(document, idGenerator).stream())
                .collect(toList());
    }
}
