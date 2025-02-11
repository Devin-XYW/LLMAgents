
package com.llm.agents.core.message.parser.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.llm.agents.core.message.func.FunctionMessage;
import com.llm.agents.core.message.parser.Parser;
import com.llm.agents.core.message.parser.FunctionMessageParser;
import com.llm.agents.core.util.StringUtil;

import java.util.Map;

public class DefaultFunctionMessageParser implements FunctionMessageParser {

    private String functionNamePath;
    private String functionArgsPath;
    private Parser<String, Map<String, Object>> functionArgsParser;

    public String getFunctionNamePath() {
        return functionNamePath;
    }

    public void setFunctionNamePath(String functionNamePath) {
        this.functionNamePath = functionNamePath;
    }

    public String getFunctionArgsPath() {
        return functionArgsPath;
    }

    public void setFunctionArgsPath(String functionArgsPath) {
        this.functionArgsPath = functionArgsPath;
    }

    public Parser<String, Map<String, Object>> getFunctionArgsParser() {
        return functionArgsParser;
    }

    public void setFunctionArgsParser(Parser<String, Map<String, Object>> functionArgsParser) {
        this.functionArgsParser = functionArgsParser;
    }

    @Override
    public FunctionMessage parse(JSONObject jsonObject) {
        String functionName = (String) JSONPath.eval(jsonObject, this.functionNamePath);
        if (StringUtil.noText(functionName)) {
            return null;
        }
        FunctionMessage functionMessage = new FunctionMessage();
        functionMessage.setFunctionName(functionName);
        Object argsResult = JSONPath.eval(jsonObject, this.functionArgsPath);
        if (argsResult instanceof String && this.functionArgsParser != null) {
            functionMessage.setArgs(this.functionArgsParser.parse((String) argsResult));
        } else if (argsResult instanceof Map) {
            functionMessage.setArgs((Map) argsResult);
        }
        return functionMessage;
    }
}
