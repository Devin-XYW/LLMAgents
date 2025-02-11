package com.llm.agents.core.rag.embed;

import com.llm.agents.core.rag.document.Document;
import com.llm.agents.core.rag.store.VectorData;

/**
 * @Author Devin
 * @Date 2024/12/17 22:46
 * @Description:
 **/
public interface EmbeddingModel {
    default VectorData embed(Document document) {
        return embed(document, EmbeddingOptions.DEFAULT);
    }

    VectorData embed(Document document, EmbeddingOptions options);

    default int dimensions() {
        return embed(Document.of("dimensions")).getVector().length;
    }
}
