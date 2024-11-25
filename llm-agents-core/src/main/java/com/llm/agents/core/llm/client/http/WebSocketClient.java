package com.llm.agents.core.llm.client.http;

import com.llm.agents.core.exception.LlmException;
import com.llm.agents.core.llm.LlmConfig;
import com.llm.agents.core.llm.client.LlmClient;
import com.llm.agents.core.llm.client.LlmClientListener;
import com.llm.agents.core.util.StringUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * @Author Devin
 * @Date 2024/11/25 22:38
 * @Description:
 **/
public class WebSocketClient extends WebSocketListener implements LlmClient {
    private WebSocket webSocket;
    private LlmClientListener listener;
    private LlmConfig config;
    private boolean isStop = false;
    private String payload;

    @Override
    public void start(String url, Map<String, String> headers, String payload, LlmClientListener listener, LlmConfig config) {
        this.listener = listener;
        this.payload = payload;
        this.config = config;

        OkHttpClient client = OkHttpClientUtil.buildDefaultClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        this.isStop = false;
        this.webSocket = client.newWebSocket(request, this);
    }

    @Override
    public void stop() {
        try {
            tryToStop();
        } finally {
            tryToCloseWebSocket();
        }
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        webSocket.send(payload);
        this.listener.onStart(this);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        this.listener.onMessage(this, text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        this.onMessage(webSocket, bytes.utf8());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        try {
            tryToStop();
        } finally {
            tryToCloseWebSocket();
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        try {
            try {
                Throwable failureThrowable = getFailureThrowable(t, response);
                this.listener.onFailure(this, failureThrowable);
            } finally {
                tryToStop();
            }
        } finally {
            tryToCloseWebSocket();
        }
    }

    public Throwable getFailureThrowable(Throwable t, Response response) {
        if (t != null) {
            return t;
        }

        if (response != null) {
            String errMessage = "Response code: " + response.code();
            String message = response.message();
            if (StringUtil.hasText(message)) {
                errMessage += ", message: " + message;
            }
            try (ResponseBody body = response.body()) {
                if (body != null) {
                    String string = body.string();
                    if (StringUtil.hasText(string)) {
                        errMessage += ", body: " + string;
                    }
                }
            } catch (IOException e) {
                // ignore
            }
            t = new LlmException(errMessage);
        }

        return t;

    }

    private void tryToCloseWebSocket() {
        if (this.webSocket != null) {
            this.webSocket.close(1000, "");
            this.webSocket = null;
        }
    }

    private boolean tryToStop() {
        if (!this.isStop) {
            this.isStop = true;
            this.listener.onStop(this);
            return true;
        }
        return false;
    }
}
