package com.llm.agents.core.chain.node;

import com.llm.agents.core.agent.Agent;
import com.llm.agents.core.agent.Parameter;
import com.llm.agents.core.chain.Chain;
import com.llm.agents.core.chain.ChainNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Devin
 * @Date 2024/11/21 22:47
 * @Description:
 **/
public class AgentNode extends ChainNode {

    private Agent agent;
    private Map<String,String> outputMapping;

    public AgentNode() {
    }

    public AgentNode(Agent agent) {
        this.agent = agent;
    }

    @Override
    public String getId() {
        if (this.id != null) {
            return this.id;
        }

        Object agentId = agent.getId();
        if (agentId == null) {
            return null;
        }
        return agentId.toString();
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Map<String, String> getOutputMapping() {
        return outputMapping;
    }

    public void setOutputMapping(Map<String, String> outputMapping) {
        this.outputMapping = outputMapping;
    }

    @Override
    protected Map<String, Object> execute(Chain chain) {
        Map<String,Object> variables = new HashMap<>();
        List<Parameter> requiredParameters = null;
        List<Parameter> inputParameters = agent.getInputParameters();

        if (inputParameters.isEmpty()) {
            //TODO  Agent 未定义输入参数
            //variables.putAll(chain.getMemory().getAll());
        }else {
            // Agent 定义了固定的输入参数
//            for(Parameter parameter:inputParameters){
//                Object value = chain.get(parameter.getName())
//
//            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "AgentNode{" +
                "agent=" + agent +
                ", id=" + id +
                '}';
    }
}
