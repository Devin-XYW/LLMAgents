package com.llm.agents.core.memory;

import com.llm.agents.core.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author Devin
 * @Date 2024/11/17 12:52
 * @Description:
 **/
public class DefaultChatMemory implements ChatMemory{

    private final Object id;
    private final List<Message> messages = new ArrayList<>();

    public DefaultChatMemory() {
        this.id = UUID.randomUUID().toString();
    }

    public DefaultChatMemory(Object id) {
        this.id = id;
    }

    @Override
    public Object id() {
        return id;
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public void addMessage(Message message) {
        messages.add(message);
    }
}
