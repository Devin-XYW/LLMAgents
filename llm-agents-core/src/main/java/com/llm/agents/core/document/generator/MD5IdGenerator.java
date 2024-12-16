package com.llm.agents.core.document.generator;

import com.llm.agents.core.document.Document;
import com.llm.agents.core.util.HashUtil;

/**
 * @Author Devin
 * @Date 2024/12/16 22:11
 * @Description:
 **/
public class MD5IdGenerator implements DocumentIdGenerator {

    @Override
    public Object generateId(Document document) {
        return document.getContent() != null ? HashUtil.md5(document.getContent()) : null;
    }
}
