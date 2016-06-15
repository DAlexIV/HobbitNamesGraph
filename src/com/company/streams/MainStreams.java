package com.company.streams;

import com.company.common.dataLayer.AppConstants;
import com.company.common.dataLayer.DataRepository;
import com.company.common.TextUtils;
import com.company.common.WordGraph;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;

public class MainStreams {
    private static DataRepository dataRepository;

    public static void main(String[] args) {
        dataRepository = new DataRepository();

        dataRepository.setTimeStart(System.currentTimeMillis());

        readInputFiles(dataRepository);
        System.out.println("Preprocessing done at " + timeLap(dataRepository));

        dataRepository.setBookWordsAll(new ArrayList<>());
        dataRepository.getBookLines().stream().forEach(line ->
                Collections.addAll(dataRepository.getBookWordsAll(), line.split(" ")));
        System.out.println("Initially text contained " + dataRepository.getBookWordsAll().size()
                + " words. " + "\n" + "Trimming started...");

        dataRepository.setBookWordsAll(TextUtils.trim(dataRepository.getBookWordsAll()));
        System.out.println(dataRepository.getBookWordsAll().size() + " words left.");
        System.out.println("Trimming done at " + timeLap(dataRepository));
        System.out.println("Matching words..." + "\n");

        WordGraph graph = new WordGraphBuilderStream()
                .buildFromWords(dataRepository.getBookWordsAll(), dataRepository.getWords());
        graph.processToPairs(100);

        System.out.println("Processed in " + timeLap(dataRepository));
    }

    private static void readInputFiles(DataRepository dataRepository) {
        try {
            dataRepository.setBookLines(Files.readAllLines(AppConstants.PATH_BOOK, AppConstants.charset));
            dataRepository.setWords(Files.readAllLines(AppConstants.PATH_WORDS, AppConstants.charset));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String timeLap(DataRepository dataRepository) {
        return (System.currentTimeMillis() - dataRepository.getTimeStart()) / 1000.0 + " sec";
    }
}
