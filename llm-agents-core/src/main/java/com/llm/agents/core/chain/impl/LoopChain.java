package com.llm.agents.core.chain.impl;

import com.llm.agents.core.chain.ChainEdge;
import com.llm.agents.core.chain.ChainEvent;
import com.llm.agents.core.chain.ChainNode;
import com.llm.agents.core.chain.event.OnNodeFinishedEvent;
import com.llm.agents.core.chain.node.StartNode;

/**
 * @Author Devin
 * @Date 2024/11/24 16:00
 * @Description: 循环链
 **/
public class LoopChain extends SequentialChain{
    private int maxLoopCount = Integer.MAX_VALUE;

    public LoopChain() {
        this.addNode(new StartNode());
    }

    public int getMaxLoopCount() {
        return maxLoopCount;
    }

    public void setMaxLoopCount(int maxLoopCount) {
        this.maxLoopCount = maxLoopCount;
    }

    public void close() {
        if (this.nodes.size() < 2) {
            return;
        }

        String sourceId = this.nodes.get(this.nodes.size() - 1).getId();
        String targetId = this.nodes.get(1).getId();

        ChainEdge edge = new ChainEdge();
        edge.setSource(sourceId);
        edge.setTarget(targetId);

        super.addEdge(edge);
    }

    @Override
    public void notifyEvent(ChainEvent event) {
        super.notifyEvent(event);
        if (event instanceof OnNodeFinishedEvent){
            ChainNode node = ((OnNodeFinishedEvent) event).getNode();
            Integer exeCount = (Integer) node.getMemory().get(CTX_EXEC_COUNT);
            if (exeCount != null && exeCount > maxLoopCount){
                stopNormal("Loop to the maxLoopCount limit");
            }
        }
    }

}
