package com.llm.agents.core.prompt.template;

import java.util.Map;

/**
 * @Author Devin
 * @Date 2024/11/17 13:39
 * @Description:
 **/
public interface PromptTemplate<R> {
    R format(Map<String, Object> params);
}
