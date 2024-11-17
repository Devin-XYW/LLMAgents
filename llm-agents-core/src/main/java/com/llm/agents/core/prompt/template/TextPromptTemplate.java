package com.llm.agents.core.prompt.template;

import com.llm.agents.core.prompt.TextPrompt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author Devin
 * @Date 2024/11/17 13:40
 * @Description:
 **/
public class TextPromptTemplate implements PromptTemplate<TextPrompt> {

    private final Set<String> keys = new HashSet<>();

    private final List<String> parts = new ArrayList<String>(){
        @Override
        public boolean add(String query) {
            if(query.charAt(0) == '{' && query.length()>2
                    && query.charAt(query.length() -1) == '}'){
                keys.add(query.substring(1, query.length() - 1));
            }
            return super.add(query);
        }
    };

    public TextPromptTemplate(String template){
        boolean isCurrentInKeyword = false;
        StringBuilder keyword = null;
        StringBuilder content = null;

        for(int index=0;index < template.length();index++){
            char c = template.charAt(index);
            if(c == '{' && !isCurrentInKeyword){
                isCurrentInKeyword = true;
                keyword = new StringBuilder("{");

                if(content != null){
                    parts.add(content.toString());
                    content = null;
                }
                continue;
            }

            if (c == '}' && isCurrentInKeyword) {
                isCurrentInKeyword = false;
                keyword.append("}");
                parts.add(keyword.toString());
                keyword = null;
                continue;
            }

            if (isCurrentInKeyword) {
                if (!Character.isWhitespace(c)) {
                    keyword.append(c);
                }
                continue;
            }

            if (content == null) {
                content = new StringBuilder();
            }
            content.append(c);
        }

        if (keyword != null) {
            parts.add(keyword.toString());
        }

        if (content != null) {
            parts.add(content.toString());
        }
    }

    public static TextPromptTemplate create(String template) {
        return new TextPromptTemplate(template);
    }

    public TextPrompt format(Map<String, Object> params) {
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            if (part.charAt(0) == '{' && part.charAt(part.length() - 1) == '}') {
                if (part.length() > 2) {
                    String key = part.substring(1, part.length() - 1);
                    Object value = getParams(key, params);
                    result.append(value == null ? "" : value);
                }
            } else {
                result.append(part);
            }
        }
        return new TextPrompt(result.toString());
    }

    public Set<String> getKeys() {
        return keys;
    }

    public List<String> getParts() {
        return parts;
    }

    private Object getParams(String keysString, Map<String, Object> params) {
        return params != null ? params.get(keysString) : null;
    }

    @Override
    public String toString() {
        return "SimplePromptTemplate{" +
                "keys=" + keys +
                ", parts=" + parts +
                '}';
    }

}
