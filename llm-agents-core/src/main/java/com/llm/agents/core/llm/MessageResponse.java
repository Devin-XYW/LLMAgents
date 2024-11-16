package com.llm.agents.core.llm;

import com.llm.agents.core.message.ai.AiMessage;

/**
 * @Author Devin
 * @Date 2024/11/16 14:21
 * @Description: 大模型返回结果抽象接口类
 **/
public interface MessageResponse<T extends AiMessage> {

    T getMessage();

}
