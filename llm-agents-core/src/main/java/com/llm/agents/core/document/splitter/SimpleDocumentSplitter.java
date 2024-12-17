package com.llm.agents.core.document.splitter;

import com.llm.agents.core.document.Document;
import com.llm.agents.core.document.DocumentSplitter;
import com.llm.agents.core.document.generator.DocumentIdGenerator;
import com.llm.agents.core.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author Devin
 * @Date 2024/12/17 22:25
 * @Description:
 **/
public class SimpleDocumentSplitter implements DocumentSplitter {
    private int chunkSize;
    private int overlapSize;

    public SimpleDocumentSplitter(int chunkSize) {
        this.chunkSize = chunkSize;
        if (this.chunkSize <= 0) {
            throw new IllegalArgumentException("chunkSize must be greater than 0, chunkSize: " + this.chunkSize);
        }
    }

    public SimpleDocumentSplitter(int chunkSize, int overlapSize) {
        this.chunkSize = chunkSize;
        this.overlapSize = overlapSize;

        if (this.chunkSize <= 0) {
            throw new IllegalArgumentException("chunkSize must be greater than 0, chunkSize: " + this.chunkSize);
        }
        if (this.overlapSize >= this.chunkSize) {
            throw new IllegalArgumentException("overlapSize must be less than chunkSize, overlapSize: " + this.overlapSize + ", chunkSize: " + this.chunkSize);
        }
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public int getOverlapSize() {
        return overlapSize;
    }

    public void setOverlapSize(int overlapSize) {
        this.overlapSize = overlapSize;
    }

    @Override
    public List<Document> split(Document document, DocumentIdGenerator idGenerator) {
        if (document == null || StringUtil.noText(document.getContent())) {
            return Collections.emptyList();
        }

        String content = document.getContent();
        int index = 0, currentIndex = index;
        int maxIndex = content.length();

        List<Document> chunks = new ArrayList<>();

        while (currentIndex < maxIndex) {
            int endIndex = Math.min(currentIndex + chunkSize, maxIndex);
            String chunk = content.substring(currentIndex, endIndex).trim();
            currentIndex = currentIndex + chunkSize - overlapSize;

            if (chunk.isEmpty()) {
                continue;
            }

            Document newDocument = new Document();
            newDocument.addMetaData(document.getMetaDataMap());
            newDocument.setContent(chunk);

            newDocument.setId(idGenerator == null ? null : idGenerator.generateId(newDocument));
            chunks.add(newDocument);
        }

        return chunks;
    }

    @Override
    public List<Document> split(Document text) {
        return DocumentSplitter.super.split(text);
    }

    @Override
    public List<Document> splitAll(List<Document> documents, DocumentIdGenerator idGenerator) {
        return DocumentSplitter.super.splitAll(documents, idGenerator);
    }
}
