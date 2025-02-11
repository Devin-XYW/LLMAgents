package com.llm.agents.core.util;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Maps {

    public static Builder of() {
        return new Builder();
    }

    public static Builder of(String key, Builder value) {
        return of(key, value.build());
    }

    public static Builder of(String key, Object value) {
        return new Builder().put(key, value);
    }

    public static Builder ofNotNull(String key, Object value) {
        return new Builder().putIfNotNull(key, value);
    }

    public static Builder ofNotEmpty(String key, Object value) {
        return new Builder().putIfNotEmpty(key, value);
    }

    public static Builder ofNotEmpty(String key, Builder value) {
        return new Builder().putIfNotEmpty(key, value);
    }

    public static class Builder {
        private Map<String, Object> map = new HashMap<>();

        public Builder put(String key, Object value) {
            if (key.contains(".")) {
                String[] keys = key.split("\\.");
                Map<String, Object> currentMap = map;
                for (int i = 0; i < keys.length; i++) {
                    String currentKey = keys[i].trim();
                    if (currentKey.isEmpty()) {
                        continue;
                    }
                    if (i == keys.length - 1) {
                        currentMap.put(currentKey, value);
                    } else {
                        currentMap = (Map<String, Object>) currentMap.computeIfAbsent(currentKey, k -> new HashMap<>());
                    }
                }
            } else {
                map.put(key, value);
            }

            return this;
        }

        public Builder putOrDefault(String key, Object value, Object orDefault) {
            if (isNullOrEmpty(value)) {
                return this.put(key, orDefault);
            } else {
                return this.put(key, value);
            }
        }

        public Builder put(String key, Builder value) {
            this.put(key, value.build());
            return this;
        }

        public Builder putIf(boolean condition, String key, Builder value) {
            if (condition) put(key, value);
            return this;
        }

        public Builder putIf(boolean condition, String key, Object value) {
            if (condition) put(key, value);
            return this;
        }

        public Builder putIf(Function<Map<String, Object>, Boolean> func, String key, Object value) {
            if (func.apply(map)) put(key, value);
            return this;
        }

        public Builder putIfNotNull(String key, Object value) {
            if (value != null) put(key, value);
            return this;
        }

        public Builder putIfNotEmpty(String key, Builder value) {
            Map<String, Object> map = value.build();
            if (map != null && !map.isEmpty()) put(key, value);
            return this;
        }

        public Builder putIfNotEmpty(String key, Object value) {
            if (!isNullOrEmpty(value)) {
                put(key, value);
            }
            return this;
        }

        private boolean isNullOrEmpty(Object value) {
            if (value == null) {
                return true;
            }

            if (value instanceof Collection && ((Collection<?>) value).isEmpty()) {
                return true;
            }

            if (value instanceof Map && ((Map<?, ?>) value).isEmpty()) {
                return true;
            }

            if (value.getClass().isArray() && Array.getLength(value) == 0) {
                return true;
            }

            if (value instanceof String && ((String) value).trim().isEmpty()) {
                return true;
            }
            return false;
        }


        public Builder putIfContainsKey(String checkKey, String key, Object value) {
            if (map.containsKey(checkKey)) {
                this.put(key, value);
            }
            return this;
        }

        public Builder putIfContainsKey(String checkKey, String key, Builder value) {
            if (map.containsKey(checkKey)) {
                this.put(key, value);
            }
            return this;
        }

        public Builder putIfNotContainsKey(String checkKey, String key, Object value) {
            if (!map.containsKey(checkKey)) {
                this.put(key, value);
            }
            return this;
        }

        public Builder putIfNotContainsKey(String checkKey, String key, Builder value) {
            if (!map.containsKey(checkKey)) {
                this.put(key, value);
            }
            return this;
        }

        public Map<String, Object> build() {
            return map;
        }

        public Object get(String key) {
            return map.get(key);
        }

        public Map getAsMap(String key) {
            return (Map) map.get(key);
        }

        public String toJSON() {
            return JSON.toJSONString(map);
        }
    }

}
