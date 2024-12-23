package com.llm.agents.core.store.condition;

import java.io.Serializable;

/**
 * @Author Devin
 * @Date 2024/12/23 21:52
 * @Description:
 **/
public interface Operand extends Serializable {
    String toExpression(ExpressionAdaptor adaptor);

}
