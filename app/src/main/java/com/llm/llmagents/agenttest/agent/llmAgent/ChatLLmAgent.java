package com.llm.llmagents.agenttest.agent.llmAgent;

import com.llm.agents.core.agent.LLMAgent;
import com.llm.agents.core.agent.Output;
import com.llm.agents.core.llm.LLM;
import com.llm.agents.core.message.ai.AiMessage;
import com.llm.agents.core.prompt.template.TextPromptTemplate;

import java.util.UUID;

/**
 * @Author Devin
 * @Date 2024/12/8 14:46
 * @Description:
 **/
public class ChatLLmAgent extends LLMAgent {
    public static final String resultKey = "answer";

    public ChatLLmAgent(LLM llm){
        super();
        this.id = "ChatLLmAgent-"+ UUID.randomUUID();
        this.description = "大模型对话智能体";
        this.llm = llm;
        this.prompt = "您现在是一个名为Devin的人工智能助手，请根据如下用户提问进行回答问题：" +
                "\n以下是用户对话信息：\n{askText}\n"+
                "\n当用户提问你无法回答时，请回复我不了解相关内容"+
                "\n当用户询问你的身份时，请回答我是Devin"+
                "\n注意请勿回答用户提问问题之外的内容，否则你将受到惩罚";
        this.promptTemplate = new TextPromptTemplate(prompt);
    }

    @Override
    protected Output onMessage(AiMessage aiMessage) {
        String sqlContent = aiMessage.getContent();
        return Output.of(resultKey, sqlContent);
    }
}
