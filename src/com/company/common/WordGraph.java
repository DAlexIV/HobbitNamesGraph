package com.company.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by dalexiv on 02.12.15.
 */
public class WordGraph {
    private Map<String, Map<String, Integer>> graph;

    public WordGraph() {
        graph = new HashMap<>();
    }

    public void updateList(List<String> nwList) {
        nwList.stream().forEach(key -> {
            if (!graph.containsKey(key)) {
                graph.put(key, nwList.stream().filter(value -> !value.equals(key))
                        .collect(Collectors.toMap(value -> value, value -> 1,
                                (value1, value2) -> {
                                    // Found duplicate key there, just skip and return first one
                                    return value1;
                                }));
            } else {
                nwList.stream().filter(value -> !value.equals(key)).forEach(value -> {
                    if (graph.get(key).containsKey(value))
                        graph.get(key).replace(value, graph.get(key).get(value) + 1);
                    else
                        graph.get(key).put(value, 1);
                });
            }
        });
    }

    public Map<String, Integer> get(String key) {
        return graph.containsKey(key) ? graph.get(key) : null;
    }

    public Map<String, Map<String, Integer>> getGraph() {
        return graph;
    }

    public void processToPairs() {

        // TODO refactor into streams?
        Map<String, Integer> mergedMap = new HashMap<>();
        for (Map.Entry<String, Map<String, Integer>> entry : graph.entrySet()) {
            for (Map.Entry<String, Integer> inner_entry : graph.get(entry.getKey()).entrySet())
                mergedMap.put(entry.getKey() + " - " + inner_entry.getKey(), inner_entry.getValue());
        }

        List<String> newList = mergedMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(100)
                .map(e -> e.getKey() + " " + e.getValue())
                .collect(Collectors.toList());

        IntStream.range(0, newList.size())
                .filter(n -> n % 2 == 0)
                .mapToObj(newList::get)
                .forEach(System.out::println);
    }

    @Override
    public String toString() {
        return graph.toString();
    }
}
