package com.llm.agents.core.chain.node;

import com.llm.agents.core.agent.Agent;
import com.llm.agents.core.agent.Output;
import com.llm.agents.core.agent.OutputKey;
import com.llm.agents.core.agent.Parameter;
import com.llm.agents.core.chain.Chain;
import com.llm.agents.core.chain.ChainNode;

import java.util.ArrayList;
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
            //Agent 未定义输入参数
            variables.putAll(chain.getMemory().getAll());
        }else {
            // Agent 定义了固定的输入参数
            for(Parameter parameter:inputParameters){
                Object value = chain.get(parameter.getName());
                //当只有一个参数时，或者当前参数为默认参数时，尝试使用default数据
                if(value == null && (parameter.isDefault()
                        || inputParameters.size() == 1)){
                    value = chain.get(Output.DEFAULT_VALUE_KEY);
                }
                if(value == null && parameter.isRequired()){
                    if(requiredParameters == null){
                        requiredParameters = new ArrayList<>();
                    }
                    requiredParameters.add(parameter);
                }else{
                    variables.put(parameter.getName(),value);
                }
            }
        }

        if(requiredParameters != null){
            return null;
        }

        Output output = agent.execute(variables,chain);
        List<OutputKey> outputKeys = agent.getOutputKeys();
        if(outputKeys == null || outputKeys.isEmpty()
                || outputMapping == null || outputMapping.isEmpty()){
            return output;
        }

        Map<String,Object> newResult = new HashMap<>(outputKeys.size());
        for(OutputKey outputKey:outputKeys){
            String oldKey = outputKey.getKey();
            String newKey = outputMapping.getOrDefault(oldKey,oldKey);
            newResult.put(newKey,output.get(oldKey));
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
