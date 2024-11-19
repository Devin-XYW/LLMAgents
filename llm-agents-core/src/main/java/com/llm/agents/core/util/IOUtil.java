
package com.llm.agents.core.util;

import okio.BufferedSink;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class IOUtil {

    public static void writeBytes(byte[] bytes, File toFile) {
        try (FileOutputStream stream = new FileOutputStream(toFile)) {
            stream.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copy(InputStream inputStream, BufferedSink sink) throws IOException {
        byte[] buffer = new byte[1024];
        for (int len; (len = inputStream.read(buffer)) != -1; ) {
            sink.write(buffer, 0, len);
        }
    }

    public static void copy(InputStream inputStream, OutputStream outStream) throws IOException {
        byte[] buffer = new byte[1024];
        for (int len; (len = inputStream.read(buffer)) != -1; ) {
            outStream.write(buffer, 0, len);
        }
    }

    public static String readUtf8(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        copy(inputStream, outStream);
        return new String(outStream.toByteArray(), StandardCharsets.UTF_8);
    }
}
