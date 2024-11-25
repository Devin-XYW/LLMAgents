package com.llm.agents.core.llm.client.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.llm.agents.core.exception.LlmException;
import com.llm.agents.core.llm.LlmConfig;
import com.llm.agents.core.llm.client.LlmClient;
import com.llm.agents.core.llm.client.LlmClientListener;
import com.llm.agents.core.util.StringUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;

/**
 * @Author Devin
 * @Date 2024/11/25 22:29
 * @Description:
 **/
public class SseClient extends EventSourceListener implements LlmClient {
    private OkHttpClient okHttpClient;
    private EventSource eventSource;
    private LlmClientListener listener;
    private LlmConfig config;
    private boolean isStop = false;


    @Override
    public void start(String url, Map<String, String> headers, String payload, LlmClientListener listener, LlmConfig config) {
        this.listener = listener;
        this.config = config;
        this.isStop = false;

        Request.Builder builder = new Request.Builder().url(url);

        if(headers != null && !headers.isEmpty()){
            headers.forEach(builder::addHeader);
        }
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(payload, mediaType);
        Request request = builder.post(body).build();

        this.okHttpClient = OkHttpClientUtil.buildDefaultClient();

        EventSource.Factory factory = EventSources.createFactory(this.okHttpClient);
        this.eventSource = factory.newEventSource(request, this);

        this.listener.onStart(this);
    }

    @Override
    public void stop() {
        tryToStop();
    }

    @Override
    public void onClosed(@NotNull EventSource eventSource) {
        tryToStop();
    }

    @Override
    public void onEvent(@NonNull EventSource eventSource, @Nullable String id, @Nullable String type, @NonNull String data) {
        this.listener.onMessage(this, data);
    }

    @Override
    public void onFailure(@NonNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
        try {
            this.listener.onFailure(this, getFailureThrowable(t, response));
        } finally {
            tryToStop();
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

    private boolean tryToStop() {
        if (!this.isStop) {
            try {
                this.isStop = true;
                this.listener.onStop(this);
            } finally {
                if (eventSource != null) {
                    eventSource.cancel();
                }
                if (okHttpClient != null) {
                    okHttpClient.dispatcher().executorService().shutdown();
                }
            }
            return true;
        }
        return false;
    }
}
