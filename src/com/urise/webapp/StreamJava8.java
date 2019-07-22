package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamJava8 {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{8, 9, 7, 4, 1, 2, 3}));
        System.out.println(oddOrEven(new ArrayList<>(Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9))));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).sorted().distinct().reduce(0, (a, b) -> 10 * a + b);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int x = integers.stream()
                .mapToInt(Integer::intValue)
                .sum() % 2;
        return integers.stream()
                .filter(a -> a % 2 != x)
                .collect(Collectors.toList());
    }
}
