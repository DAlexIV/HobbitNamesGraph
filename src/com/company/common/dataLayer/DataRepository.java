package com.company.common.dataLayer;

import java.util.List;

/**
 * Created by dalexiv on 6/15/16.
 */
public class DataRepository {

    private List<String> bookLines;
    private List<String> words;
    private List<String> bookWordsAll;

    public List<String> getBookLines() {
        return bookLines;
    }

    public void setBookLines(List<String> bookLines) {
        this.bookLines = bookLines;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public List<String> getBookWordsAll() {
        return bookWordsAll;
    }

    public void setBookWordsAll(List<String> bookWordsAll) {
        this.bookWordsAll = bookWordsAll;
    }

    private long timeStart;

}
