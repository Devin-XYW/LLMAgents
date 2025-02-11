package com.llm.agents.core.rag.store.condition;

public class Key implements Operand {

    private Object key;

    public Key(Object key) {
        this.key = key;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    @Override
    public String toExpression(ExpressionAdaptor adaptor) {
        return key != null ? key.toString() : "";
    }
}
