package com.company.streams;

import com.company.common.TextUtils;
import com.company.common.WordGraph;
import com.company.common.interfaces.IWordGraphBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dalexiv on 6/13/16.
 */
public class WordGraphBuilderStream implements IWordGraphBuilder {
    public WordGraph buildFromWords(List<String> book_words, List<String> words) {
        WordGraph graph = new WordGraph();
        List<String> cur_words = new ArrayList<>();
        book_words.stream()
                .peek(word -> addIfFit(cur_words, TextUtils.trimAll(word), words))
                .filter(word -> TextUtils.isPrep(word) && !cur_words.isEmpty())
                .forEach(word -> {
                    graph.updateList(cur_words);
                    cur_words.clear();
                });
        return graph;

    }
}
