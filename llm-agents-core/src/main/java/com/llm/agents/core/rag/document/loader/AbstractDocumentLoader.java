package com.llm.agents.core.rag.document.loader;

import com.llm.agents.core.rag.document.Document;
import com.llm.agents.core.rag.document.DocumentLoader;
import com.llm.agents.core.rag.document.DocumentParser;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author Devin
 * @Date 2024/12/16 22:51
 * @Description:
 **/
public abstract class AbstractDocumentLoader implements DocumentLoader {
    protected DocumentParser documentParser;

    public AbstractDocumentLoader(DocumentParser documentParser) {
        this.documentParser = documentParser;
    }

    @Override
    public Document load() {
        try (InputStream stream = loadInputStream()){
            return documentParser.parse(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract InputStream loadInputStream();

}
