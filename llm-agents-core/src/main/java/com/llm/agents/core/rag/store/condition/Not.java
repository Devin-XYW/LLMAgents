package com.llm.agents.core.rag.store.condition;

public class Not extends Group {

    public Not(Condition condition) {
        super("NOT", condition);
    }

}
