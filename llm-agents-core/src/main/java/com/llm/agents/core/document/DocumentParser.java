package com.llm.agents.core.document;

import java.io.InputStream;

/**
 * @Author Devin
 * @Date 2024/12/11 23:07
 * @Description:
 **/
public interface DocumentParser {
    Document parse(InputStream stream);
}
