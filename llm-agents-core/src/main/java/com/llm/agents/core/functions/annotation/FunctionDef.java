package com.llm.agents.core.functions.annotation;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface FunctionDef {
    String name() default "";
    String description();
}
