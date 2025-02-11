package com.llm.agents.core.rag.document;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Author Devin
 * @Date 2024/12/11 23:08
 * @Description:
 **/
public interface DocumentTransformer {
    Document transform(Document document);

    default List<Document> transformAll(List<Document> documents) {
        if (documents == null || documents.isEmpty()){
            return Collections.emptyList();
        }
        return documents.stream()
                .map(this::transform)
                .filter(Objects::nonNull)
                .collect(toList());
    }

}
