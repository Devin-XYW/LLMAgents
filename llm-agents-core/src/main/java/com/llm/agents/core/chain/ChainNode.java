package com.llm.agents.core.chain;

import com.llm.agents.core.memory.ContextMemory;
import com.llm.agents.core.memory.DefaultContextMemory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Devin
 * @Date 2024/11/19 23:03
 * @Description:
 **/
public abstract class ChainNode implements Serializable {

    protected String id;
    protected String name;
    protected boolean async;
    protected List<ChainEdge> inwardEdges;
    protected List<ChainEdge> outwardEdges;

    protected ChainCondition condition;

    protected ContextMemory memory = new DefaultContextMemory();
    protected ChainNodeStatus nodeStatus = ChainNodeStatus.READY;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public List<ChainEdge> getInwardEdges() {
        return inwardEdges;
    }

    public void setInwardEdges(List<ChainEdge> inwardEdges) {
        this.inwardEdges = inwardEdges;
    }

    public List<ChainEdge> getOutwardEdges() {
        return outwardEdges;
    }

    public void setOutwardEdges(List<ChainEdge> outwardEdges) {
        this.outwardEdges = outwardEdges;
    }

    public ChainCondition getCondition() {
        return condition;
    }

    public void setCondition(ChainCondition condition) {
        this.condition = condition;
    }

    public ContextMemory getMemory() {
        return memory;
    }

    public void setMemory(ContextMemory memory) {
        this.memory = memory;
    }

    public ChainNodeStatus getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(ChainNodeStatus nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    protected abstract Map<String, Object> execute(Chain chain);

    protected void addOutwardEdge(ChainEdge edge) {
        if (this.outwardEdges == null) {
            this.outwardEdges = new ArrayList<>();
        }
        this.outwardEdges.add(edge);
    }

    protected void addInwardEdge(ChainEdge edge) {
        if (this.inwardEdges == null) {
            this.inwardEdges = new ArrayList<>();
        }
        this.inwardEdges.add(edge);
    }

}