package com.llm.agents.core.rag.document;

import com.llm.agents.core.rag.store.VectorData;

/**
 * @Author Devin
 * @Date 2024/12/11 23:01
 * @Description:
 **/
public class Document extends VectorData {
    /**
     * Document ID
     */
    private Object id;

    /**
     * Document Content
     */
    private String content;


    public Document() {
    }

    public Document(String content) {
        this.content = content;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static Document of(String content){
        Document document = new Document();
        document.setContent(content);
        return document;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", metadataMap=" + metaDataMap +
                '}';
    }

}
