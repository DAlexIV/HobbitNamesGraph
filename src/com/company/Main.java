package com.company;

import sun.plugin.javascript.navig.Array;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static final int NUMBER_OF_WORDS = 100000;
    static final int step = 1000;
    static final Path PATH_BOOK = Paths.get("/home/dalexiv/IdeaProjects/Hobbit2/data", "hobbit.txt");
    static final Path PATH_WORDS = Paths.get("/home/dalexiv/IdeaProjects/Hobbit2/data", "words.txt");
    static final Charset charset = Charset.forName("ISO-8859-1");
    static List<String> bookLines;
    static List<String> words;
    static List<String> bookWordsAll;
    static List<ArrayList<String>> bookWordsPerThread;
    static WordGraph[] graphs;

    public static void main(String[] args) {

        try {
            long time_start = System.currentTimeMillis();
            bookLines = Files.readAllLines(PATH_BOOK, charset);
            words = Files.readAllLines(PATH_WORDS, charset);
            bookWordsAll = new ArrayList<>();

            System.out.println("Preproc done! Trimming ");
            for (String line : bookLines) {
                for (String word : line.split(" "))
                    bookWordsAll.add(word);
            }
            System.out.println("Trimming done! Words trimmed - "
                    + Integer.toString(Utils.trim(bookWordsAll)) + " Reading file");


            System.out.println("Reading file done! Matching words");
            System.out.println(Integer.toString(bookWordsAll.size()) + " left.");
            long time_reading = System.currentTimeMillis();
            System.out.println("Time elapsed " + Long.toString(time_reading - time_start));

            int CORES = Runtime.getRuntime().availableProcessors();
            graphs = new WordGraph[CORES];
            int DATA_SIZE = bookWordsAll.size() / CORES;
            ExecutorService executor = Executors.newFixedThreadPool(CORES);
            CountDownLatch latch = new CountDownLatch(CORES);

            bookWordsPerThread = new ArrayList<>();

            for (int i = 0; i < CORES; ++i) {
                ArrayList<String> currentWords = new ArrayList<>();
                for (int k = DATA_SIZE * i; k < DATA_SIZE * (i + 1); ++k)
                    currentWords.add(bookWordsAll.get(k));
                bookWordsPerThread.add(currentWords);
                graphs[i] = new WordGraph();
                executor.execute(new ProcessPartOfArray(latch, graphs[i], bookWordsPerThread.get(i), words));
            }

            System.out.println("All process were started by time "
                    + Long.toString(System.currentTimeMillis() - time_start));
            latch.await();
            executor.shutdown();

            WordGraph mergedGraph = new WordGraph(graphs);
            mergedGraph.processToPairs();
            long time_finish = System.currentTimeMillis();
            System.out.println("Time elapsed " + Long.toString(time_finish - time_start));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
