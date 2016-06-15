package com.company;

import com.company.common.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by dalexiv on 14.12.15.
 */
public class ProcessPartOfArray implements Runnable{
    CountDownLatch latch;

    public ProcessPartOfArray(CountDownLatch latch, WordGraph mGraph, List<String> book_words, List<String> words) {
        this.latch = latch;
        this.mGraph = mGraph;
        this.book_words = book_words;
        this.words = words;
    }

    WordGraph mGraph = new WordGraph();
    List<String> book_words;
    List<String> words;

    @Override
    public void run() {
        List<String> cur_words = new ArrayList<String>();
        for (String word : book_words) {
            if (TextUtils.isPrep(word) && !cur_words.isEmpty()) {
                addIfFit(cur_words, TextUtils.trimAll(word));
                mGraph.updateList(cur_words);
                cur_words.clear();
            } else {
                addIfFit(cur_words, TextUtils.trimAll(word));
            }
        }
        latch.countDown();
    }


    private void addIfFit(List<String> cur_words, String word) {
        if (TextUtils.isBig(word) && !words.contains(word.toLowerCase()))
            cur_words.add(word);
    }
}
