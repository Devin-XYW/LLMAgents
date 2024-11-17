package com.llm.agents.core.prompt;

import com.llm.agents.core.llm.response.AiMessageResponse;
import com.llm.agents.core.message.Message;
import com.llm.agents.core.message.human.HumanMessage;

import java.util.Collections;
import java.util.List;

/**
 * @Author Devin
 * @Date 2024/11/17 12:25
 * @Description: 文本类Prompt
 **/
public class TextPrompt extends Prompt<AiMessageResponse> {
    protected String content;

    public TextPrompt(){

    }

    public TextPrompt(String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public List<Message> toMessages() {
        return Collections.singletonList(new HumanMessage(content));
    }

    @Override
    public String toString() {
        return "TextPrompt{" +
                "content='" + content + '\'' +
                ", metadataMap=" + metaDataMap +
                '}';
    }
}
