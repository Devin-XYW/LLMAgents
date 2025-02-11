package com.llm.agents.core.rag.store.condition;

import com.llm.agents.core.util.StringUtil;

public class Group extends Condition {

    private String prevOperand = "";
    private final Condition childCondition;

    public Group(Condition condition) {
        this.childCondition = condition;
    }

    public Group(String prevOperand, Condition childCondition) {
        this.prevOperand = prevOperand;
        this.childCondition = childCondition;
    }


    @Override
    public boolean checkEffective() {
        boolean effective = super.checkEffective();
        if (!effective) {
            return false;
        }
        Condition condition = this.childCondition;
        while (condition != null) {
            if (condition.checkEffective()) {
                return true;
            }
            condition = condition.next;
        }
        return false;
    }


    @Override
    public String toExpression(ExpressionAdaptor adaptor) {
        StringBuilder expr = new StringBuilder();
        if (checkEffective()) {
            String childExpr = childCondition.toExpression(adaptor);
            Condition prevEffectiveCondition = getPrevEffectiveCondition();
            if (prevEffectiveCondition != null && this.connector != null) {
                childExpr = adaptor.toConnector(this.connector) + this.prevOperand + adaptor.toGroupStart(this) + childExpr + adaptor.toGroupEnd(this);
            } else if (StringUtil.hasText(childExpr)) {
                childExpr = this.prevOperand + adaptor.toGroupStart(this) + childExpr + adaptor.toGroupEnd(this);
            }
            expr.append(childExpr);
        }

        if (this.next != null) {
            expr.append(next.toExpression(adaptor));
        }
        return expr.toString();
    }
}
