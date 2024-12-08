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
 * @Description: 文本Prompt配置器，其中处理Prompt的参数字符串拼接
 **/
public class TextPromptTemplate implements PromptTemplate<TextPrompt> {

    //存放当前可变参数的key值
    private final Set<String> keys = new HashSet<>();

    //将当前配置的Prompt字段将描述、可变参数对象进行区分存放
    //使用{}进行标识可变参数内容
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

    /**
     * 构造函数，其中将Prompt中文本描述和可变参数key值进行区分存放
     * 可变参数key值使用{}进行标识
     * @param template
     */
    public TextPromptTemplate(String template){
        boolean isCurrentInKeyword = false;
        StringBuilder keyword = null;
        StringBuilder content = null;

        //遍历当前Prompt中字段，提取Prompt中的可变参数key字段
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

    /**
     * Prompt参数匹配&拼接方法
     * @param params：外部传入的Prompt参数值
     * @return 拼接好的Prompt字段
     */
    public TextPrompt format(Map<String, Object> params) {
        StringBuilder result = new StringBuilder();
        //遍历
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
