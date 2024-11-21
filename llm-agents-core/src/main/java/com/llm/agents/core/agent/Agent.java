package com.llm.agents.core.agent;

import com.llm.agents.core.chain.Chain;
import com.llm.agents.core.memory.ContextMemory;

import java.util.*;

/**
 * @Author Devin
 * @Date 2024/11/19 23:27
 * @Description:
 **/
public abstract class Agent {

    protected Object id;
    protected String name;
    protected String description;
    private ContextMemory memory;
    private List<Parameter> inputParameters;
    private List<OutputKey> outputKeys;

    public Agent() {
        this.id = UUID.randomUUID().toString();
    }

    public Agent(Object id) {
        this.id = id;
    }

    public Agent(Object id, String name) {
        this.id = id;
        this.name = name;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
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

    public ContextMemory getMemory() {
        return memory;
    }

    public void setMemory(ContextMemory memory) {
        this.memory = memory;
    }

    public List<Parameter> getInputParameters() {
        if (this.inputParameters == null) {
            this.inputParameters = defineInputParameter();
            if (this.inputParameters == null) {
                this.inputParameters = Collections.emptyList();
            }
        }
        return this.inputParameters;
    }

    protected List<Parameter> defineInputParameter() {
        return Collections.emptyList();
    }

    public List<OutputKey> getOutputKeys() {
        return outputKeys;
    }

    public void setOutputKeys(List<OutputKey> outputKeys) {
        this.outputKeys = outputKeys;
    }

    public Agent output(String... keys) {
        if (this.outputKeys == null) {
            this.outputKeys = new ArrayList<>();
        }

        for (String key : keys) {
            this.outputKeys.add(new OutputKey(key));
        }

        return this;
    }

    public Output execute(Map<String, Object> variables) {
        return execute(variables, null);
    }

    public abstract Output execute(Map<String, Object> variables, Chain chain);

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
