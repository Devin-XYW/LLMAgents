package com.llm.doc.parser.pdfbox;

import com.llm.agents.core.document.Document;
import com.llm.agents.core.document.DocumentParser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author Devin
 * @Date 2024/12/23 23:33
 * @Description:
 **/
public class PdfBoxDocumentParser implements DocumentParser {
    @Override
    public Document parse(InputStream stream) {
        try (PDDocument pdfDocument = PDDocument.load(stream)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(pdfDocument);
            return new Document(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
