package com.llm.agents.core.chain.impl;

import com.llm.agents.core.chain.Chain;
import com.llm.agents.core.chain.ChainEdge;
import com.llm.agents.core.chain.ChainNode;

/**
 * @Author Devin
 * @Date 2024/11/24 15:58
 * @Description: 顺序链
 **/
public class SequentialChain extends Chain {

    @Override
    public void addNode(ChainNode chainNode) {
        super.addNode(chainNode);

        if (this.nodes.size() < 2) {
            return;
        }

        String sourceId = this.nodes.get(this.nodes.size() - 2).getId();
        String targetId = this.nodes.get(this.nodes.size() - 1).getId();

        ChainEdge edge = new ChainEdge();
        edge.setSource(sourceId);
        edge.setTarget(targetId);

        super.addEdge(edge);
    }


}
