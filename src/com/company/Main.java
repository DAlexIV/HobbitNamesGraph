package com.company;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static final int NUMBER_OF_WORDS = 100000;
    static final int step = 1000;
    static final WordGraph mGraph = new WordGraph();
    static final Path path_book = Paths.get("/home/dalexiv/IdeaProjects/Hobbit2/data", "hobbit.txt");
    static final Path path_words = Paths.get("/home/dalexiv/IdeaProjects/Hobbit2/data", "words.txt");
    static final Charset charset = Charset.forName("ISO-8859-1");
    static List<String> book_lines;
    static List<String> words;
    static List<String> book_words;


    public static void main(String[] args) {

        try {
            book_lines = Files.readAllLines(path_book, charset);
            words = Files.readAllLines(path_words, charset);
            book_words = new ArrayList<>();

            System.out.println("Preproc done! Trimming ");
            for (String line : book_lines) {
                for (String word : line.split(" "))
                    book_words.add(word);
            }
            System.out.println("Trimming done! Words trimmed - "
                    + Integer.toString(Utils.trim(book_words)) + "Reading file");


            System.out.println("Reading file done! Matching words");
            System.out.println(Integer.toString(book_words.size()) + " left.");


            List<String> cur_words = new ArrayList<String>();

            for (int i = 0; i < book_words.size(); ++i) {
                String word = book_words.get(i);
                if (Utils.isPrep(word) && !cur_words.isEmpty()) {
                    addIfFit(cur_words, Utils.trimAll(word));
                    mGraph.updateList(cur_words);
                    cur_words.clear();
                } else
                    addIfFit(cur_words, Utils.trimAll(word));
                if (i % step == 0) System.out.println(Integer.toString(i) + " Done.");

            }
            System.out.println(mGraph.get("Mr").toString());
            mGraph.processToPairs();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void addIfFit(List<String> cur_words, String word) {
        if (Utils.isBig(word) && !words.contains(word.toLowerCase()))
            cur_words.add(word);
    }
}
