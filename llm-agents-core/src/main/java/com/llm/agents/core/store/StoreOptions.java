package com.llm.agents.core.store;

import com.llm.agents.core.bean.MetaData;
import com.llm.agents.core.embed.EmbeddingOptions;
import com.llm.agents.core.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author Devin
 * @Date 2024/12/17 22:58
 * @Description:
 **/
public class StoreOptions extends MetaData {
    public static final StoreOptions DEFAULT = new StoreOptions() {
        @Override
        public void setCollectionName(String collectionName) {
            throw new IllegalStateException("Can not set collectionName to the default instance.");
        }

        @Override
        public void setPartitionNames(List<String> partitionNames) {
            throw new IllegalStateException("Can not set partitionName to the default instance.");
        }

        @Override
        public void setEmbeddingOptions(EmbeddingOptions embeddingOptions) {
            throw new IllegalStateException("Can not set embeddingOptions to the default instance.");
        }
    };

    /**
     * store collection name
     */
    private String collectionName;

    /**
     * store index name
     */
    private String indexName;

    /**
     * store partition name
     */
    private List<String> partitionNames;

    /**
     * store embedding options
     */
    private EmbeddingOptions embeddingOptions = EmbeddingOptions.DEFAULT;


    public String getCollectionName() {
        return collectionName;
    }

    public String getCollectionNameOrDefault(String other) {
        return StringUtil.hasText(collectionName) ? collectionName : other;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public List<String> getPartitionNames() {
        return partitionNames;
    }

    public String getPartitionName() {
        return partitionNames != null && !partitionNames.isEmpty() ? partitionNames.get(0) : null;
    }

    public List<String> getPartitionNamesOrEmpty() {
        return partitionNames == null ? Collections.emptyList() : partitionNames;
    }

    public void setPartitionNames(List<String> partitionNames) {
        this.partitionNames = partitionNames;
    }

    public StoreOptions partitionName(String partitionName) {
        if (this.partitionNames == null) {
            this.partitionNames = new ArrayList<>(1);
        }
        this.partitionNames.add(partitionName);
        return this;
    }


    public EmbeddingOptions getEmbeddingOptions() {
        return embeddingOptions;
    }

    public void setEmbeddingOptions(EmbeddingOptions embeddingOptions) {
        this.embeddingOptions = embeddingOptions;
    }


    public static StoreOptions ofCollectionName(String collectionName) {
        StoreOptions storeOptions = new StoreOptions();
        storeOptions.setCollectionName(collectionName);
        return storeOptions;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexNameOrDefault(String other) {
        return StringUtil.hasText(indexName) ? indexName : other;
    }
}
