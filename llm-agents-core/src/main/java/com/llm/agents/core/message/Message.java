package com.llm.agents.core.message;

import com.llm.agents.core.bean.MetaData;

/**
 * @Author Devin
 * @Date 2024/11/16 13:46
 * @Description: Message抽象对象
 **/
public abstract class Message extends MetaData {

    //获取Message内容
    public abstract Object getMessageContent();

}
