package io.daonomic.solidity.merger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MultipleSourceLoader implements SourceLoader {
    private final List<SourceLoader> loaders;

    public MultipleSourceLoader(SourceLoader... loaders) {
        this(Arrays.asList(loaders));
    }

    public MultipleSourceLoader(List<SourceLoader> loaders) {
        this.loaders = loaders;
    }

    @Override
    public boolean exists(String path) {
        return loaders.stream().anyMatch(loader -> loader.exists(path));
    }

    @Override
    public String load(String path) throws IOException {
        for (SourceLoader loader : loaders) {
            String loaded = loader.load(path);
            if (loaded != null) {
                return loaded;
            }
        }
        return null;
    }
}
