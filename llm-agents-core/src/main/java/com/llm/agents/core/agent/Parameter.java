package com.llm.agents.core.agent;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Devin
 * @Date 2024/11/21 22:57
 * @Description: Agent参数对象
 **/
public class Parameter {
    private String name;
    private String description;
    private String type;
    private boolean required;
    private boolean isDefault;
    private String defaultValue;
    private List<Parameter> children;

    public Parameter() {
    }

    public Parameter(String name) {
        this.name = name;
    }

    public Parameter(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Parameter(String name, boolean required) {
        this.name = name;
        this.required = required;
    }

    public Parameter(String name, String type, boolean required) {
        this.name = name;
        this.type = type;
        this.required = required;
    }

    public Parameter(String name, String type, boolean required, boolean isDefault) {
        this.name = name;
        this.type = type;
        this.required = required;
        this.isDefault = isDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<Parameter> getChildren() {
        return children;
    }

    public void setChildren(List<Parameter> children) {
        this.children = children;
    }

    public void addChild(Parameter parameter) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(parameter);
    }


}
