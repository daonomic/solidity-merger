package io.daonomic.solidity.merger;

import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.*;

@Test
public class ResourceSourceLoaderTest {
    private ResourceSourceLoader testing = new ResourceSourceLoader("test1");

    public void exists() {
        assertTrue(testing.exists("contracts/Contract.sol"));
        assertFalse(testing.exists("contracts/error.sol"));
    }

    public void load() throws IOException {
        assertNotNull(testing.load("contracts/Contract.sol"));
        assertNull(testing.load("contracts/error.sol"));
    }
}
