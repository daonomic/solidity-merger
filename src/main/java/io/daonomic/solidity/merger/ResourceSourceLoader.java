package io.daonomic.solidity.merger;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ResourceSourceLoader implements SourceLoader {
    private final String root;

    public ResourceSourceLoader(String root) {
        this.root = root;
    }

    @Override
    public boolean exists(String path) {
        return getClass().getClassLoader().getResource(root + "/" + path) != null;
    }

    @Override
    public String load(String path) throws IOException {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(root + "/" + path)) {
            if (in == null) {
                return null;
            } else {
                return IOUtils.toString(in, StandardCharsets.UTF_8);
            }
        }
    }
}
