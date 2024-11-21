package com.llm.agents.core.agent;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Devin
 * @Date 2024/11/21 22:55
 * @Description:
 **/
public class Output extends HashMap<String, Object> {
    public static final String DEFAULT_VALUE_KEY = "default";

    public Output(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public Output(int initialCapacity) {
        super(initialCapacity);
    }

    public Output() {
    }

    public Output(Map<String, ?> m) {
        super(m);
    }

    public Object getValue() {
        return get(DEFAULT_VALUE_KEY);
    }


    public Output set(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public static Output of(OutputKey key, Object value) {
        return of(key.getKey(), value);
    }

    public static Output of(String key, Object value) {
        Output output = new Output();
        output.put(key, value);
        return output;
    }

    public static Output ofDefault(Object value) {
        return of(DEFAULT_VALUE_KEY, value);
    }

}
