package io.daonomic.solidity.merger;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileSourceLoader implements SourceLoader {
    private final String root;

    public FileSourceLoader(String root) {
        this.root = root;
    }

    @Override
    public boolean exists(String path) {
        return new File(root + "/" + path).exists();
    }

    @Override
    public String load(String path) {
        try (InputStream in = new FileInputStream(root + "/" + path)) {
            return IOUtils.toString(in, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
