package io.daonomic.solidity.merger;

import java.io.IOException;

public interface IOExceptionFunction<T, R> {
    R apply(T t) throws IOException;
}
