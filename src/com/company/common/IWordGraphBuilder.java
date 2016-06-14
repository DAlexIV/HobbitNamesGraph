package com.company.common;

import java.util.List;

/**
 * Created by dalexiv on 6/14/16.
 */
public interface IWordGraphBuilder {
    public WordGraph buildFromWords(List<String> book_words, List<String> words);

    public default void addIfFit(List<String> cur_words, String word, List<String> words) {
        if (Utils.isBig(word) && !words.contains(word.toLowerCase()))
            cur_words.add(word);
    }
}
