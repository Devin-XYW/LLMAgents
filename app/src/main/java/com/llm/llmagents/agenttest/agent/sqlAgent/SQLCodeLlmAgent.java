package com.llm.llmagents.agenttest.agent.sqlAgent;

import com.llm.agents.core.agent.LLMAgent;
import com.llm.agents.core.agent.Output;
import com.llm.agents.core.llm.LLM;
import com.llm.agents.core.message.ai.AiMessage;
import com.llm.agents.core.prompt.template.TextPromptTemplate;

/**
 * @Author Devin
 * @Date 2024/12/1 12:59
 * @Description:
 **/
public class SQLCodeLlmAgent extends LLMAgent {
    public static final String resultKey = "sqlInfo";

    public SQLCodeLlmAgent(LLM llm){
        super();
        this.id = "SQLCodeLlmAgent";
        this.description = "SQL语句智能体";
        this.llm = llm;
        this.prompt = "您现在是一个 MySQL 数据库架构师，请根据如下的表结构信息和数据操作要求，" +
                "帮我生成SQL语句，以便我用于数据库数据查询、删除、更新等操作。\n" +
                "注意：\n" +
                "请直接返回 SQL 语句 内容，不需要解释，不需要以及除了 SQL 语句以外的其他内容。否则你将受到惩罚。\n" +
                "\n以下是表结构信息：\n\n{tableInfo}" +
                "\n以下是数据操作要求：\n\n{option}";
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
