package com.llm.agents.core.message.human;

import com.llm.agents.core.message.AbstractTextMessage;

/**
 * @Author Devin
 * @Date 2024/11/16 14:15
 * @Description: 请求对话Message
 **/
public class HumanMessage extends AbstractTextMessage {
    public HumanMessage() {
    }

    public HumanMessage(String content) {
        setContent(content);
    }
}
