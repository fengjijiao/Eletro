package com.fjj.eletro.utils;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MapUtils {
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, int limitSize) {
        return map.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(limitSize)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}