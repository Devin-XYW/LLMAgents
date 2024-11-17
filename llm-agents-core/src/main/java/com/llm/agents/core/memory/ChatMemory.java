package com.llm.agents.core.memory;

import com.llm.agents.core.message.Message;

import java.util.Collection;
import java.util.List;

/**
 * @Author Devin
 * @Date 2024/11/17 12:33
 * @Description:
 **/
public interface ChatMemory extends Memory{
    List<Message> getMessages();

    void addMessage(Message message);

    default void addMessages(Collection<Message> messages){
        for(Message message : messages){
            addMessage(message);
        }
    }

}
