
package com.llm.agents.core.message.parser;

/**
 * 解析器，用于解析输入的内容，并按输出的格式进行输出
 *
 * @param <I> 输入内容
 * @param <O> 输出内容
 */
public interface Parser<I, O> {

    /**
     * 解析输入的内容
     *
     * @param content 输入的内容
     * @return 输出的内容
     */
    O parse(I content);
}
