package com.llm.agents.core.store.condition;

public enum Connector {


    /**
     * AND
     */
    AND(" AND "),

    /**
     * AND NOT
     */
    AND_NOT(" AND NOT "),

    /**
     * OR
     */
    OR(" OR "),

    /**
     * OR NOT
     */
    OR_NOT(" OR NOT "),

    /**
     * NOT
     */
    NOT(" NOT "),
    ;


    private final String value;

    Connector(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
