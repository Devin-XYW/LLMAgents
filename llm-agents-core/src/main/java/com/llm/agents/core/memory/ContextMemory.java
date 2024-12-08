package com.llm.agents.core.memory;

import java.util.Map;

/**
 * @Author Devin
 * @Date 2024/11/17 12:44
 * @Description: 上下文记忆
 **/
public interface ContextMemory extends Memory{
    Object get(String key);

    Map<String,Object> getAll();

    void put(String key,Object value);

    void putAll(Map<String,Object> context);

    void remove(String key);

    void clear();
}
