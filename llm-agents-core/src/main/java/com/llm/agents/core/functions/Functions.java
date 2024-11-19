package com.llm.agents.core.functions;

import com.llm.agents.core.functions.annotation.FunctionDef;
import com.llm.agents.core.util.ArrayUtil;
import com.llm.agents.core.util.ClassUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Functions extends ArrayList<Function> {

    public static Functions from(Object object, String... methodNames) {
        return from(object.getClass(), object, methodNames);
    }

    public static Functions from(Class<?> clazz, String... methodNames) {
        return from(clazz, null, methodNames);
    }

    private static Functions from(Class<?> clazz, Object object, String... methodNames) {
        clazz = ClassUtil.getUsefulClass(clazz);
        List<Method> methodList = ClassUtil.getAllMethods(clazz, method -> {
            if (object == null && !Modifier.isStatic(method.getModifiers())) {
                return false;
            }
            if (method.getAnnotation(FunctionDef.class) == null) {
                return false;
            }
            if (methodNames.length > 0) {
                return ArrayUtil.contains(methodNames, method.getName());
            }
            return true;
        });

        Functions functions = new Functions();

        for (Method method : methodList) {
            Function function = new Function();
            function.setClazz(clazz);
            function.setMethod(method);

            if (!Modifier.isStatic(method.getModifiers())) {
                function.setObject(object);
            }

            functions.add(function);
        }

        return functions;
    }


}
