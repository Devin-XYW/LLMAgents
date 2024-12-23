package com.llm.agents.core.store.condition;

public class Not extends Group {

    public Not(Condition condition) {
        super("NOT", condition);
    }

}
