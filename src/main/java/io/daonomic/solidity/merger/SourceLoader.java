package io.daonomic.solidity.merger;

import java.io.IOException;

public interface SourceLoader {
    boolean exists(String path);

    String load(String path) throws IOException;
}
