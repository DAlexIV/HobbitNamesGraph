package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dalexiv on 03.12.15.
 */
public class UtilsTest {

    @Test
    public void testTrimAll() throws Exception {
        assert(!Utils.trimAll("We,,...ll!!!!").equals("Well"));
    }
}