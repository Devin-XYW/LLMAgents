package com.llm.qwen;

import com.llm.agents.core.llm.LlmConfig;

/**
 * @Author Devin
 * @Date 2024/11/26 22:10
 * @Description: 阿里千问协议文档：https://help.aliyun.com/zh/dashscope/developer-reference/api-details?spm=a2c4g.11186623.0.0.1ff6fa70jCgGRc#b8ebf6b25eul6
 **/
public class QwenLLmConfig extends LlmConfig {

    private static final String DEFAULT_MODEL = "qwen-turbo";
    private static final String DEFAULT_ENDPOINT = "https://dashscope.aliyuncs.com";

    public QwenLLmConfig(){
        setEndpoint(DEFAULT_ENDPOINT);
        setModel(DEFAULT_MODEL);
    }

}
