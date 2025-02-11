package com.llm.agents.core.functions;

import com.llm.agents.core.chain.convert.ConvertService;
import com.llm.agents.core.functions.annotation.FunctionDef;
import com.llm.agents.core.functions.annotation.FunctionParam;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Function {
    private Class<?> clazz;
    private Object object;
    private Method method;
    private String name;
    private String description;
    private Parameter[] parameters;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;

        FunctionDef functionDef = method.getAnnotation(FunctionDef.class);
        this.name = functionDef.name();
        this.description = functionDef.description();

        List<Parameter> parameterList = new ArrayList<>();
        java.lang.reflect.Parameter[] methodParameters = method.getParameters();
        for (java.lang.reflect.Parameter methodParameter : methodParameters) {
            Parameter parameter = getParameter(methodParameter);
            parameterList.add(parameter);
        }
        this.parameters = parameterList.toArray(new Parameter[]{});
    }

    @NotNull
    private static Parameter getParameter(java.lang.reflect.Parameter methodParameter) {
        FunctionParam functionParam = methodParameter.getAnnotation(FunctionParam.class);
        Parameter parameter = new Parameter();
        parameter.setName(functionParam.name());
        parameter.setDescription(functionParam.description());
        parameter.setType(methodParameter.getType().getSimpleName().toLowerCase());
        parameter.setTypeClass(methodParameter.getType());
        parameter.setRequired(functionParam.required());
        parameter.setEnums(functionParam.enums());
        return parameter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    public void setParameters(Parameter[] parameters) {
        this.parameters = parameters;
    }

    public Object invoke(Map<String, Object> argsMap) {
        try {
            Object[] args = new Object[this.parameters.length];
            for (int i = 0; i < this.parameters.length; i++) {
                Object value = argsMap.get(this.parameters[i].getName());
                args[i] = ConvertService.convert(value, this.parameters[i].getTypeClass());
            }
            return method.invoke(object, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
