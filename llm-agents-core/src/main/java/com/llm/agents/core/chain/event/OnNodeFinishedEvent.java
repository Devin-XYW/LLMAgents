package com.llm.agents.core.chain.event;

import com.llm.agents.core.chain.ChainEvent;
import com.llm.agents.core.chain.ChainNode;

import java.util.Map;

/**
 * @Author Devin
 * @Date 2024/11/24 15:14
 * @Description:
 **/
public class OnNodeFinishedEvent implements ChainEvent {

    private ChainNode node;
    private Map<String,Object> result;

    public OnNodeFinishedEvent(ChainNode node, Map<String, Object> result) {
        this.node = node;
        this.result = result;
    }

    public ChainNode getNode() {
        return node;
    }

    public void setNode(ChainNode node) {
        this.node = node;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "OnNodeFinishedEvent{" +
                "node=" + node +
                ", result=" + result +
                '}';
    }

}
