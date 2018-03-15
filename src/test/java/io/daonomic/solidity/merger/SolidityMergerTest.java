package io.daonomic.solidity.merger;

import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

@Test
public class SolidityMergerTest {
    public void test1() throws IOException {
        ResourceSourceLoader loader = new ResourceSourceLoader("test1");
        assertEquals(new SolidityMerger(loader).merge("contracts/Contract.sol"), loader.load("contracts/Merged.sol"));
    }
}
