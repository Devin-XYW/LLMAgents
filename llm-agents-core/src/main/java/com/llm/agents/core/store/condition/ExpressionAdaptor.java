package com.llm.agents.core.store.condition;

import java.util.StringJoiner;

/**
 * @Author Devin
 * @Date 2024/12/23 21:52
 * @Description:
 **/
public interface ExpressionAdaptor {
    ExpressionAdaptor DEFAULT = new ExpressionAdaptor() {
    };

    default String toCondition(Condition condition) {
        return toLeft(condition.left)
                + toOperationSymbol(condition.type)
                + toRight(condition.right);
    }

    default String toLeft(Operand operand) {
        return operand.toExpression(this);
    }

    default String toOperationSymbol(ConditionType type) {
        return type.getDefaultSymbol();
    }

    default String toRight(Operand operand) {
        return operand.toExpression(this);
    }

    default String toValue(Condition condition, Object value) {
        // between
        if (condition.getType() == ConditionType.BETWEEN) {
            Object[] values = (Object[]) value;
            return "\"" + values[0] + "\" AND \"" + values[1] + "\"";
        }

        // in
        else if (condition.getType() == ConditionType.IN) {
            Object[] values = (Object[]) value;
            StringJoiner stringJoiner = new StringJoiner(",", "(", ")");
            for (Object v : values) {
                if (v != null) {
                    stringJoiner.add("\"" + v + "\"");
                }
            }
            return stringJoiner.toString();
        }

        return value == null ? "" : "\"" + value + "\"";
    }


    default String toConnector(Connector connector) {
        return connector.getValue();
    }

    default String toGroupStart(Group group) {
        return "(";
    }

    default String toGroupEnd(Group group) {
        return ")";
    }
}
