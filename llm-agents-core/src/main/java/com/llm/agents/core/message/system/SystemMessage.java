package com.llm.agents.core.message.system;

import com.llm.agents.core.message.AbstractTextMessage;

/**
 * @Author Devin
 * @Date 2024/11/16 14:14
 * @Description: 系统Message消息，用于配置上下文环境前提
 **/
public class SystemMessage extends AbstractTextMessage {

    public SystemMessage(String content) {
        this.content = content;
    }

}
