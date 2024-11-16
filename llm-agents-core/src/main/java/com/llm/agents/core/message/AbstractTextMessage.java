package com.llm.agents.core.message;

/**
 * @Author Devin
 * @Date 2024/11/16 13:45
 * @Description: LLM 文本类 Message抽象类
 **/
public class AbstractTextMessage extends Message{

    protected String content;

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    @Override
    public Object getMessageContent() {
        return getContent();
    }
}
