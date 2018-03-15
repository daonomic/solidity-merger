package io.daonomic.solidity.merger;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
