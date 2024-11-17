package com.llm.agents.core.prompt;

import com.llm.agents.core.bean.MetaData;
import com.llm.agents.core.llm.MessageResponse;
import com.llm.agents.core.message.Message;

import java.util.List;


/**
 * @Author Devin
 * @Date 2024/11/17 12:20
 * @Description:
 **/
public abstract class Prompt<M extends MessageResponse<?>> extends MetaData {

    public abstract List<Message> toMessages();

}
