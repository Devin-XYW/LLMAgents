/*
 *  Copyright (c) 2023-2025, Agents-Flex (fuhai999@gmail.com).
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.llm.qwen;

import com.llm.agents.core.agent.Agent;
import com.llm.agents.core.agent.DefaultAgent;
import com.llm.agents.core.agent.Output;
import com.llm.agents.core.agent.Parameter;
import com.llm.agents.core.chain.Chain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimpleAgent1 extends DefaultAgent {
    public SimpleAgent1(Object id){
        super(id);
    }

    @Override
    public Object execute(Object parameter, Chain chain) {
        return id + ":" + parameter;
    }
}
