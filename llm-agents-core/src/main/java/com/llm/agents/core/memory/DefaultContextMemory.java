package com.llm.agents.core.memory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Devin
 * @Date 2024/11/17 12:57
 * @Description:
 **/
public class DefaultContextMemory implements ContextMemory{

    protected Map<String,Object> context = new ConcurrentHashMap<>();

    @Override
    public Object id() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Object get(String key) {
        return context.get(key);
    }

    @Override
    public Map<String, Object> getAll() {
        return context;
    }

    @Override
    public void put(String key, Object value) {
        if(value == null){
            this.context.remove(key);
        }else {
            context.put(key,value);
        }
    }

    @Override
    public void putAll(Map<String, Object> map) {
        if (map == null) {
            return;
        }
        map.forEach((string, object) -> {
            if (object == null) {
                DefaultContextMemory.this.context.remove(string);
            } else {
                DefaultContextMemory.this.context.put(string, object);
            }
        });
    }

    @Override
    public void remove(String key) {
        this.context.remove(key);
    }

    @Override
    public void clear() {
        context.clear();
    }
}
