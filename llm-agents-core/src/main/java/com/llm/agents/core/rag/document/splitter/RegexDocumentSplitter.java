package com.llm.agents.core.rag.document.splitter;

import com.llm.agents.core.rag.document.Document;
import com.llm.agents.core.rag.document.DocumentSplitter;
import com.llm.agents.core.rag.document.generator.DocumentIdGenerator;
import com.llm.agents.core.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author Devin
 * @Date 2024/12/17 22:38
 * @Description:
 **/
public class RegexDocumentSplitter implements DocumentSplitter {
    private final String regex;

    public RegexDocumentSplitter(String regex) {
        this.regex = regex;
    }

    @Override
    public List<Document> split(Document document, DocumentIdGenerator idGenerator) {
        if (document == null || StringUtil.noText(document.getContent())) {
            return Collections.emptyList();
        }
        String[] textArray = document.getContent().split(regex);
        List<Document> chunks = new ArrayList<>(textArray.length);
        for (String textString : textArray) {
            Document newDocument = new Document();
            newDocument.addMetaData(document.getMetaDataMap());
            newDocument.setContent(textString);

            //we should invoke setId after setContent
            newDocument.setId(idGenerator == null ? null : idGenerator.generateId(newDocument));
            chunks.add(newDocument);
        }
        return chunks;
    }
}
