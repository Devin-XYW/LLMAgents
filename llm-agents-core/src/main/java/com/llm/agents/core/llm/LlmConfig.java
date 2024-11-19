
package com.llm.agents.core.llm;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Consumer;

public class LlmConfig implements Serializable {

    private String model;

    private String endpoint;

    private String apiKey;

    private String apiSecret;

    private boolean debug;

    private Consumer<Map<String, String>> headersConfig;

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public Consumer<Map<String, String>> getHeadersConfig() {
        return headersConfig;
    }

    public void setHeadersConfig(Consumer<Map<String, String>> headersConfig) {
        this.headersConfig = headersConfig;
    }
}
