package com.company.common;

import com.company.common.interfaces.ISumOperator;

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
                                })));
            } else {
                nwList.stream().filter(value -> !value.equals(key)).forEach(value -> {
                    Map<String, Integer> innerMap = graph.get(key);

                    if (innerMap.containsKey(value))
                        innerMap.replace(value, innerMap.get(value) + 1);
                    else
                        innerMap.put(value, 1);
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

    public void processToPairs(int numberOfDisplayedPairs) {

        ISumOperator<Integer> sumValues = (value1, value2) -> value1 + value2;

        // TODO refactor into streams? - done
        List<String> outList =
                graph.entrySet().stream().map(entry ->
                        entry.getValue().entrySet().stream()
                                .collect(Collectors.toMap(innerEntry ->
                                                entry.getKey() + " - " + innerEntry.getKey(),
                                        Map.Entry::getValue,
                                        sumValues)))
                        .flatMap(map -> map.entrySet().stream())
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                Map.Entry::getValue,
                                sumValues))
                        .entrySet().stream()
                        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                        .limit(2 * numberOfDisplayedPairs)
                        .map(e -> e.getKey() + " " + e.getValue())
                        .collect(Collectors.toList());

        IntStream.range(0, outList.size()).
                filter(n -> n % 2 == 0).
                mapToObj(outList::get).
                forEach(System.out::println);

    }

    @Override
    public String toString() {
        return graph.toString();
    }
}
