package com.llm.agents.core.chain;

/**
 * @Author Devin
 * @Date 2024/11/19 23:01
 * @Description:
 **/
public class ChainEdge {
    private String source;
    private String target;
    private ChainCondition condition;
    private int weight;
    private boolean isDefault;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public ChainCondition getCondition() {
        return condition;
    }

    public void setCondition(ChainCondition condition) {
        this.condition = condition;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }


}
