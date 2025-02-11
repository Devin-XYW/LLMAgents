package com.llm.agents.core.rag.store;

import com.llm.agents.core.bean.MetaData;

import java.util.List;

/**
 * @Author Devin
 * @Date 2024/12/11 22:58
 * @Description:
 **/
public class StoreResult extends MetaData {

    private final boolean success;
    private List<Object> ids;

    public StoreResult(boolean success) {
        this.success = success;
    }


    public boolean isSuccess() {
        return success;
    }

    public List<Object> ids() {
        return ids;
    }

    public static StoreResult fail() {
        return new StoreResult(false);
    }

    public static StoreResult success() {
        return new StoreResult(true);
    }


    @Override
    public String toString() {
        return "StoreResult{" +
                "success=" + success +
                ", ids=" + ids +
                '}';
    }

}
