package com.llm.agents.core.document.loader;

import com.llm.agents.core.document.DocumentParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @Author Devin
 * @Date 2024/12/16 22:53
 * @Description:
 **/
public class FileDocumentLoader extends AbstractDocumentLoader {

    private final File file;

    public FileDocumentLoader(DocumentParser documentParser, File file) {
        super(documentParser);
        this.file = file;
    }

    @Override
    protected InputStream loadInputStream() {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
