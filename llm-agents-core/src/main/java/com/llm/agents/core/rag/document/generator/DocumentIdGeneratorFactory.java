package com.llm.agents.core.rag.document.generator;

/**
 * @Author Devin
 * @Date 2024/12/16 22:27
 * @Description:
 **/
public abstract class DocumentIdGeneratorFactory {
    private static DocumentIdGeneratorFactory factory = new DocumentIdGeneratorFactory(){
        final MD5IdGenerator randomIdGenerator = new MD5IdGenerator();

        @Override
        public DocumentIdGenerator createGenerator() {
            return randomIdGenerator;
        }
    };

    public static DocumentIdGeneratorFactory getFactory() {
        return factory;
    }

    public static void setFactory(DocumentIdGeneratorFactory factory) {
        if (factory == null) {
            throw new NullPointerException("factory can not be null");
        }
        DocumentIdGeneratorFactory.factory = factory;
    }

    public static DocumentIdGenerator getDocumentIdGenerator() {
        return factory.createGenerator();
    }

    abstract DocumentIdGenerator createGenerator();

}
