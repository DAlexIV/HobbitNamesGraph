package com.company.common.interfaces;

import com.company.common.TextUtils;
import com.company.common.WordGraph;

import java.util.List;

/**
 * Created by dalexiv on 6/14/16.
 */
public interface IWordGraphBuilder {
    WordGraph buildFromWords(List<String> book_words, List<String> words);

    default void addIfFit(List<String> cur_words, String word, List<String> words) {
        if (TextUtils.isBig(word) && !words.contains(word.toLowerCase()))
            cur_words.add(word);
    }
}
