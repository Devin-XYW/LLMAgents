package com.llm.agents.core.rag.store.condition;

public class Value implements Operand {

    private Condition condition;
    private Object value;

    public Value(Object value) {
        this.value = value;
    }

    public Value(Object... values){
        this.value = values;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public String toExpression(ExpressionAdaptor adaptor) {
        if (value instanceof Operand) {
            return adaptor.toRight(this);
        }
        return adaptor.toValue(condition, value);
    }
}
