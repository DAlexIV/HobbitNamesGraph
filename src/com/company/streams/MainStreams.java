package com.company.streams;

import com.company.common.Utils;
import com.company.common.WordGraph;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainStreams {
    // Debug options
    private static final int NUMBER_OF_WORDS = 100000;
    private static final int STEP = 1000;

    private static final Path PATH_BOOK = Paths.get("/home/dalexiv/IdeaProjects/Hobbit2/data", "hobbit.txt");
    private static final Path PATH_WORDS = Paths.get("/home/dalexiv/IdeaProjects/Hobbit2/data", "words.txt");
    private static final Charset charset = Charset.forName("ISO-8859-1");

    private static List<String> bookLines;
    private static List<String> words;
    private static List<String> bookWordsAll;
    private static List<ArrayList<String>> bookWordsPerThread;
    private static WordGraph[] graphs;

    private static long timeStart;


    public static void main(String[] args) {
        timeStart = System.currentTimeMillis();
        readInputFiles();
        System.out.println("Preprocessing done at " + timeLap());

        bookWordsAll = new ArrayList<>();
        bookLines.stream().forEach(line ->
                Collections.addAll(bookWordsAll, line.split(" ")));
        System.out.println("Initially text contained " + bookWordsAll.size()
                + " words. " + "\n" + "Trimming started...");

        bookWordsAll = Utils.trim(bookWordsAll);
        System.out.println(bookWordsAll.size() + " words left.");
        System.out.println("Trimming done at " + timeLap());
        System.out.println("Matching words..." + "\n");

        WordGraph graph = new WordGraphBuilderStream().buildFromWords(bookWordsAll, words);
        graph.processToPairs();

        System.out.println("Processed in " + timeLap());
    }

    private static void readInputFiles() {
        try {
            bookLines = Files.readAllLines(PATH_BOOK, charset);
            words = Files.readAllLines(PATH_WORDS, charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String timeLap() {
        return (System.currentTimeMillis() - timeStart) / 1000.0 + " sec";
    }
}
