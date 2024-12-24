package com.llm.doc.parser.poi;

import com.llm.agents.core.document.Document;
import com.llm.agents.core.document.DocumentParser;

import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.extractor.POITextExtractor;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author Devin
 * @Date 2024/12/23 23:32
 * @Description:
 **/
public class PoiDocumentParser implements DocumentParser {

    @Override
    public Document parse(InputStream stream) {
        try (POITextExtractor extractor = ExtractorFactory.createExtractor(stream)) {
            String text = extractor.getText();
            return new Document(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
