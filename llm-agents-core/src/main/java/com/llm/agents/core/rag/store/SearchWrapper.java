package com.llm.agents.core.rag.store;

import com.llm.agents.core.rag.store.condition.ExpressionAdaptor;
import com.llm.agents.core.rag.store.condition.Condition;
import com.llm.agents.core.rag.store.condition.ConditionType;
import com.llm.agents.core.rag.store.condition.Connector;
import com.llm.agents.core.rag.store.condition.Group;
import com.llm.agents.core.rag.store.condition.Key;
import com.llm.agents.core.rag.store.condition.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @Author Devin
 * @Date 2024/12/17 22:59
 * @Description:
 **/
public class SearchWrapper extends VectorData {

    public static final int DEFAULT_MAX_RESULTS = 4;

    private String text;

    private Integer maxResults = DEFAULT_MAX_RESULTS;

    private Double minScore;

    private boolean withVector = true;

    private Condition condition;

    /**
     * query fields
     */
    private List<String> outputFields;

    /**
     * whether to output vector data
     */
    private boolean outputVector = false;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SearchWrapper text(String text) {
        setText(text);
        return this;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public SearchWrapper maxResults(Integer maxResults) {
        setMaxResults(maxResults);
        return this;
    }

    public Double getMinScore() {
        return minScore;
    }

    public void setMinScore(Double minScore) {
        this.minScore = minScore;
    }

    public SearchWrapper minScore(Double minScore) {
        setMinScore(minScore);
        return this;
    }

    public boolean isWithVector() {
        return withVector;
    }

    public void setWithVector(boolean withVector) {
        this.withVector = withVector;
    }

    public SearchWrapper withVector(Boolean withVector) {
        setWithVector(withVector);
        return this;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public List<String> getOutputFields() {
        return outputFields;
    }

    public void setOutputFields(List<String> outputFields) {
        this.outputFields = outputFields;
    }

    public SearchWrapper outputFields(Collection<String> outputFields) {
        setOutputFields(new ArrayList<>(outputFields));
        return this;
    }

    public SearchWrapper outputFields(String... outputFields) {
        setOutputFields(Arrays.asList(outputFields));
        return this;
    }

    public boolean isOutputVector() {
        return outputVector;
    }

    public void setOutputVector(boolean outputVector) {
        this.outputVector = outputVector;
    }

    public SearchWrapper outputVector(boolean outputVector) {
        setOutputVector(outputVector);
        return this;
    }


    public SearchWrapper eq(String key, Object value) {
        return eq(Connector.AND, key, value);
    }

    public SearchWrapper eq(Connector connector, String key, Object value) {
        if (this.condition == null) {
            this.condition = new Condition(ConditionType.EQ, new Key(key), new Value(value));
        } else {
            this.condition.connect(new Condition(ConditionType.EQ, new Key(key), new Value(value)), connector);
        }
        return this;
    }

    public SearchWrapper ne(String key, Object value) {
        return ne(Connector.AND, key, value);
    }

    public SearchWrapper ne(Connector connector, String key, Object value) {
        if (this.condition == null) {
            this.condition = new Condition(ConditionType.NE, new Key(key), new Value(value));
        } else {
            this.condition.connect(new Condition(ConditionType.NE, new Key(key), new Value(value)), connector);
        }
        return this;
    }

    public SearchWrapper gt(String key, Object value) {
        return gt(Connector.AND, key, value);
    }

    public SearchWrapper gt(Connector connector, String key, Object value) {
        if (this.condition == null) {
            this.condition = new Condition(ConditionType.GT, new Key(key), new Value(value));
        } else {
            this.condition.connect(new Condition(ConditionType.GT, new Key(key), new Value(value)), connector);
        }
        return this;
    }


    public SearchWrapper ge(String key, Object value) {
        return ge(Connector.AND, key, value);
    }

    public SearchWrapper ge(Connector connector, String key, Object value) {
        if (this.condition == null) {
            this.condition = new Condition(ConditionType.GE, new Key(key), new Value(value));
        } else {
            this.condition.connect(new Condition(ConditionType.GE, new Key(key), new Value(value)), connector);
        }
        return this;
    }


    public SearchWrapper lt(String key, Object value) {
        return lt(Connector.AND, key, value);
    }

    public SearchWrapper lt(Connector connector, String key, Object value) {
        if (this.condition == null) {
            this.condition = new Condition(ConditionType.LT, new Key(key), new Value(value));
        } else {
            this.condition.connect(new Condition(ConditionType.LT, new Key(key), new Value(value)), connector);
        }
        return this;
    }


    public SearchWrapper le(String key, Object value) {
        return le(Connector.AND, key, value);
    }

    public SearchWrapper le(Connector connector, String key, Object value) {
        if (this.condition == null) {
            this.condition = new Condition(ConditionType.LE, new Key(key), new Value(value));
        } else {
            this.condition.connect(new Condition(ConditionType.LE, new Key(key), new Value(value)), connector);
        }
        return this;
    }


    public SearchWrapper in(String key, Collection<?> values) {
        return in(Connector.AND, key, values);
    }

    public SearchWrapper in(Connector connector, String key, Collection<?> values) {
        if (this.condition == null) {
            this.condition = new Condition(ConditionType.IN, new Key(key), new Value(values.toArray()));
        } else {
            this.condition.connect(new Condition(ConditionType.IN, new Key(key), new Value(values.toArray())), connector);
        }
        return this;
    }

    public SearchWrapper min(String key, Object value) {
        return min(Connector.AND, key, value);
    }

    public SearchWrapper min(Connector connector, String key, Object value) {
        if (this.condition == null) {
            this.condition = new Condition(ConditionType.NIN, new Key(key), new Value(value));
        } else {
            this.condition.connect(new Condition(ConditionType.NIN, new Key(key), new Value(value)), connector);
        }
        return this;
    }

    public SearchWrapper between(String key, Object start, Object end) {
        return between(Connector.AND, key, start, end);
    }

    public SearchWrapper between(Connector connector, String key, Object start, Object end) {
        if (this.condition == null) {
            this.condition = new Condition(ConditionType.BETWEEN, new Key(key), new Value(start, end));
        } else {
            this.condition.connect(new Condition(ConditionType.BETWEEN, new Key(key), new Value(start, end)), connector);
        }
        return this;
    }


    public SearchWrapper group(SearchWrapper wrapper) {
        return group(wrapper.condition);
    }

    public SearchWrapper group(Condition condition) {
        if (this.condition == null) {
            this.condition = new Group(condition);
        } else {
            this.condition.connect(new Group(condition), Connector.AND);
        }
        return this;
    }

    public SearchWrapper group(Consumer<SearchWrapper> consumer) {
        SearchWrapper newWrapper = new SearchWrapper();
        consumer.accept(newWrapper);
        Condition condition = newWrapper.condition;
        if (condition != null) {
            group(condition);
        }
        return this;
    }

    public String toFilterExpression() {
        return toFilterExpression(ExpressionAdaptor.DEFAULT);
    }

    public String toFilterExpression(ExpressionAdaptor adaptor) {
        if (this.condition == null) {
            return null;
        } else {
            Objects.requireNonNull(adaptor, "adaptor must not be null");
            return this.condition.toExpression(adaptor);
        }
    }

}
