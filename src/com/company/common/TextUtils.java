package com.company.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dalexiv on 02.12.15.
 */
public class TextUtils {
    private static String[] symbForTrim = {".", ";", "!", "?", "(", ")"};

    /**
     * Trims given list of words
     * @param list initial list with words
     * @return trimmed list of the words
     */
    public static List<String> trim(List<String> list) {
        return list.stream().filter(word -> !word.equals(""))
                .collect(Collectors.toList());
    }

    public static boolean isBig(String word) {
        if (word == null || word.equals(""))
            return false;
        if (word.contains("http"))
            return false;
        if (word.matches(".*\\d+.*"))
            return false;
        return word.charAt(0) == word.toUpperCase().charAt(0);
    }

    public static boolean isPrep(String word) {
        for (String wrong : symbForTrim)
            if (word.contains(wrong))
                return true;
        return false;
    }

    public static String clearComma(String word) {
        if (word.length() < 2)
            return word;
        if (word.charAt(word.length() - 1) == ',')
            return word.substring(0, word.length() - 1);
        else
            return word;
    }

    public static String trimAll(String word) {
        return word.replaceAll("[^a-zA-Z]", "");
    }
}
