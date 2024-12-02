package com.llm.saprk;

import com.llm.agents.core.llm.LlmConfig;

/**
 * @Author Devin
 * @Date 2024/12/2 22:31
 * @Description:
 **/
public class SparkLlmConfig extends LlmConfig {
    private String appId;
    private String apiSecret;
    private String apiKey;
    private String version = "v3.5";

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
