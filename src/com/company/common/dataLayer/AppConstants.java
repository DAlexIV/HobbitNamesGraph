package com.company.common.dataLayer;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by dalexiv on 6/15/16.
 */
public class AppConstants {
    // Debug options
    public static final int NUMBER_OF_WORDS = 100000;
    public static final int STEP = 1000;

    public static final Path PATH_BOOK = Paths.get("/home/dalexiv/IdeaProjects/Hobbit2/data", "hobbit.txt");
    public static final Path PATH_WORDS = Paths.get("/home/dalexiv/IdeaProjects/Hobbit2/data", "words.txt");
    public static final Charset charset = Charset.forName("ISO-8859-1");
}
