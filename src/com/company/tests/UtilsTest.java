package com.company.tests;

import com.company.common.TextUtils;
import org.junit.Test;

/**
 * Created by dalexiv on 03.12.15.
 */
public class UtilsTest {

    @Test
    public void testTrimAll() throws Exception {
        assert(!TextUtils.trimAll("We,,...ll!!!!").equals("Well"));
    }
}