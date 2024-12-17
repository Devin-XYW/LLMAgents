package com.llm.agents.core.embed;

import com.llm.agents.core.document.Document;
import com.llm.agents.core.store.VectorData;

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
