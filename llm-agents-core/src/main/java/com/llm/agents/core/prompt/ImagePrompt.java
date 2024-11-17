package com.llm.agents.core.prompt;

import com.llm.agents.core.message.Message;
import com.llm.agents.core.message.human.HumanMessage;
import com.llm.agents.core.util.Maps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author Devin
 * @Date 2024/11/17 13:34
 * @Description:
 **/
public class ImagePrompt extends TextPrompt{

    private String imageUrl;

    public ImagePrompt(String content) {
        super(content);
    }

    public ImagePrompt(String content, String imageUrl) {
        super(content);
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public List<Message> toMessages() {
        return Collections.singletonList(new TextAndImageMessage(this));
    }


    @Override
    public String toString() {
        return "ImagePrompt{" +
                "imageUrl='" + imageUrl + '\'' +
                ", content='" + content + '\'' +
                ", metadataMap=" + metaDataMap +
                '}';
    }


    public static class TextAndImageMessage extends HumanMessage {
        private final ImagePrompt prompt;

        public TextAndImageMessage(ImagePrompt prompt) {
            this.prompt = prompt;
        }

        public ImagePrompt getPrompt() {
            return prompt;
        }

        @Override
        public Object getMessageContent() {
            List<Map<String, Object>> messageContent = new ArrayList<>();
            messageContent.add(Maps.of("type", "text").put("text", prompt.content).build());
            messageContent.add(Maps.of("type", "image_url").put("image_url", Maps.of("url", prompt.imageUrl).build()).build());
            return messageContent;
        }
    }

}
