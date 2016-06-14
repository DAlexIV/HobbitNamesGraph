package com.company.streams;

import com.company.common.IWordGraphBuilder;
import com.company.common.Utils;
import com.company.common.WordGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dalexiv on 6/13/16.
 */
public class WordGraphBuilderStream implements IWordGraphBuilder {
    public WordGraph buildFromWords(List<String> book_words, List<String> words) {
        WordGraph graph = new WordGraph();
        List<String> cur_words = new ArrayList<String>();
        for (String word : book_words) {
            if (Utils.isPrep(word) && !cur_words.isEmpty()) {
                addIfFit(cur_words, Utils.trimAll(word), words);
                graph.updateList(cur_words);
                cur_words.clear();
            } else {
                addIfFit(cur_words, Utils.trimAll(word), words);
            }
        }
        return graph;
    }


}
