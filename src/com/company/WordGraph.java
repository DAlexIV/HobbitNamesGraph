package com.company;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dalexiv on 02.12.15.
 */
public class WordGraph {
    private HashMap<String, HashMap<String, Integer>> graph;

    public WordGraph(WordGraph[] graphs) {
        this();
        for (WordGraph gr : graphs)
        {
            gr.getGraph().forEach((k, v) -> graph.merge(k, v, (v1, v2) -> {
                v1.forEach((key, value) -> v2.merge(key, value, (val1, val2) -> val1 + val2));
                return v2;
            }));
        }
    }

    public WordGraph() {
        graph = new HashMap<>();
    }

    public void updateList(List<String> nwList) {
        for (String key : nwList) {
            if (!graph.containsKey(key)) {
                HashMap<String, Integer> toInsert = new HashMap<>();
                for (String value : nwList)
                    if (!value.equals(key))
                        toInsert.put(value, 1);
                graph.put(key, toInsert);
            } else {
                for (String value : nwList)
                    if (!value.equals(key)) {
                        if (graph.get(key).containsKey(value))
                            graph.get(key).replace(value, graph.get(key).get(value) + 1);
                        else
                            graph.get(key).put(value, 1);
                    }
            }
        }
    }

    public HashMap<String, Integer> get(String key) {
        return graph.containsKey(key) ? graph.get(key) : null;
    }

    public HashMap<String, HashMap<String, Integer>> getGraph() {
        return graph;
    }

    public void processToPairs()
    {

        HashMap<String, Integer> mergedMap = new HashMap<>();
        for (HashMap.Entry<String, HashMap<String, Integer>> entry : graph.entrySet())
        {
            for (HashMap.Entry<String, Integer> inner_entry : graph.get(entry.getKey()).entrySet())
                mergedMap.put(entry.getKey() + " - " + inner_entry.getKey(), inner_entry.getValue());
        }
        List<String> newList = mergedMap.entrySet().stream()
                .sorted(HashMap.Entry.<String, Integer>comparingByValue().reversed())
                .limit(100)
                .map(e -> e.getKey() + " " + e.getValue())
                .collect(Collectors.toList());
        for (int i = 0; i < newList.size(); ++i)
            if (i % 2 == 0)
                System.out.println(newList.get(i));



    }

    @Override
    public String toString() {
        return graph.toString();
    }
}
