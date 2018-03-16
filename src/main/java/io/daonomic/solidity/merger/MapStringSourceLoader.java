package io.daonomic.solidity.merger;

import java.util.Map;

public class MapStringSourceLoader implements SourceLoader {
    private final Map<String, String> sources;

    public MapStringSourceLoader(Map<String, String> sources) {
        this.sources = sources;
    }

    @Override
    public boolean exists(String path) {
        return sources.containsKey(path);
    }

    @Override
    public String load(String path) {
        return sources.get(path);
    }
}
