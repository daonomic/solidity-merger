package io.daonomic.solidity.merger;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Collections;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class MultipleSourceLoaderTest {
    private SourceLoader testing = new MultipleSourceLoader(
        new MapStringSourceLoader(Collections.emptyMap()),
        new MapStringSourceLoader(Collections.singletonMap("id", "source"))
    );

    public void exists() {
        assertTrue(testing.exists("id"));
        assertFalse(testing.exists("error"));
    }

    public void load() throws IOException {
        assertEquals(testing.load("id"), "source");
        assertEquals(testing.load("none"), null);
    }
}
