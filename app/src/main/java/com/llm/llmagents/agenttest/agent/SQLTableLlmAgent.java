package com.llm.llmagents.agenttest.agent;

import com.llm.agents.core.agent.LLMAgent;
import com.llm.agents.core.agent.Output;
import com.llm.agents.core.llm.LLM;
import com.llm.agents.core.message.ai.AiMessage;
import com.llm.agents.core.prompt.template.TextPromptTemplate;

/**
 * @Author Devin
 * @Date 2024/12/1 12:34
 * @Description:
 **/
public class SQLTableLlmAgent extends LLMAgent {

    public static final String resultKey = "tableInfo";

    public SQLTableLlmAgent(LLM llm){
        super();
        this.id = "SQLTableLlmAgent";
        this.description = "SQL表格智能体";
        this.llm = llm;
        this.prompt = "您现在是一个 MySQL 数据库架构师，请根据如下的表结构信息，" +
                "帮我生成可以执行的 DDL 语句，以方便我用于创建 MySQL 的表结构。\n" +
                "注意：\n" +
                "请直接返回 DDL 内容，不需要解释，不需要以及除了 DDL 语句以外的其他内容。\n" +
                "\n以下是表信息的内容：\n\n{ddlInfo}" +
                "\n以下是索引信息：\n\n{index}";
        this.promptTemplate = new TextPromptTemplate(prompt);
    }

    @Override
    protected Output onMessage(AiMessage aiMessage) {
        String sqlContent = aiMessage.getContent()
                .replace("```sql", "")
                .replace("```", "");
        return Output.of(resultKey,sqlContent);
    }
}
