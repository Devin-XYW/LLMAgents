package com.llm.agents.core.agent;

/**
 * @Author Devin
 * @Date 2024/11/21 22:54
 * @Description:
 **/
public class OutputKey {
    private String key;
    private String type;
    private String description;

    public OutputKey() {
    }

    public OutputKey(String key) {
        this.key = key;
    }

    public OutputKey(String key, String type) {
        this.key = key;
        this.type = type;
    }


    public OutputKey(String key, String type, String description) {
        this.key = key;
        this.type = type;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
