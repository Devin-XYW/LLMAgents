package com.llm.agents.core.agent;

import com.llm.agents.core.chain.Chain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Devin
 * @Date 2024/11/24 16:20
 * @Description:
 **/
public abstract class DefaultAgent extends Agent{

    public DefaultAgent() {
    }

    public DefaultAgent(Object id) {
        super(id);
    }

    public DefaultAgent(Object id, String name) {
        super(id, name);
    }


    @Override
    public List<Parameter> defineInputParameter() {
        List<Parameter> parameters = new ArrayList<>(1);
        parameters.add(new Parameter(Output.DEFAULT_VALUE_KEY, true));
        return parameters;
    }

    @Override
    public Output execute(Map<String, Object> variables, Chain chain) {
        Object value;
        if (variables == null || variables.isEmpty()) {
            value = null;
        } else if (variables.containsKey(Output.DEFAULT_VALUE_KEY)) {
            value = variables.get(Output.DEFAULT_VALUE_KEY);
        } else {
            String key = variables.keySet().iterator().next();
            value = variables.get(key);
        }
        List<OutputKey> outputKeys = getOutputKeys();
        if (outputKeys != null && outputKeys.size() == 1) {
            return Output.of(outputKeys.get(0), execute(value, chain));
        }

        return Output.ofDefault(execute(value, chain));
    }

    public abstract Object execute(Object parameter, Chain chain);


}
