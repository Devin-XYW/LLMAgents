package com.llm.agents.core.rag.store;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @Author Devin
 * @Date 2024/12/17 22:55
 * @Description:
 **/
public abstract class VectorStore<T extends VectorData> {

    public StoreResult store(T vectorData) {
        return store(vectorData, StoreOptions.DEFAULT);
    }

    public StoreResult store(T vectorData, StoreOptions options) {
        return store(Collections.singletonList(vectorData), options);
    }

    public StoreResult store(List<T> vectorDataList) {
        return store(vectorDataList, StoreOptions.DEFAULT);
    }

    public abstract StoreResult store(List<T> vectorDataList, StoreOptions options);

    public StoreResult delete(String... ids) {
        return delete(Arrays.asList(ids), StoreOptions.DEFAULT);
    }

    public StoreResult delete(Number... ids) {
        return delete(Arrays.asList(ids), StoreOptions.DEFAULT);
    }

    public StoreResult delete(Collection<?> ids) {
        return delete(ids, StoreOptions.DEFAULT);
    }

    public abstract StoreResult delete(Collection<?> ids, StoreOptions options);

    public StoreResult update(T vectorData) {
        return update(vectorData, StoreOptions.DEFAULT);
    }

    public StoreResult update(T vectorData, StoreOptions options) {
        return update(Collections.singletonList(vectorData), options);
    }

    public StoreResult update(List<T> vectorDataList) {
        return update(vectorDataList, StoreOptions.DEFAULT);
    }

    public abstract StoreResult update(List<T> vectorDataList, StoreOptions options);

    public List<T> search(SearchWrapper wrapper) {
        return search(wrapper, StoreOptions.DEFAULT);
    }

    public abstract List<T> search(SearchWrapper wrapper, StoreOptions options);
}
