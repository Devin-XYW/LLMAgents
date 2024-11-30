package com.llm.agents.core.convert;

public interface IConverter<T> {

    /**
     * convert the given text to type <T>.
     *
     * @param text the text to convert.
     * @return the convert value or null.
     */
    T convert(String text) throws ConvertException;
}
