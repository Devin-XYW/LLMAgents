package com.llm.agents.core.message;

import java.io.Serializable;

/**
 * @Author Devin
 * @Date 2024/11/16 13:51
 * @Description: 消息状态，用于在流式结构返回的情况下
 *               用于标识当前信息的状态
 **/
public enum MessageStatus implements Serializable {

    //开始内容
    START(1),

    //流式过程中
    MIDDLE(2),

    //流式结束
    END(3),

    //其他内容
    UNKNOW(9),
    ;
    private int value;

    MessageStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
