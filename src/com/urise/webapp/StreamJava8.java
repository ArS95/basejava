package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamJava8 {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{8, 9, 7, 4, 1, 2, 3}));
        System.out.println(oddOrEven(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9))));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).sorted().distinct().reduce(0, (a, b) -> 10 * a + b);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Supplier<Stream<Integer>> streamSupplier = integers::stream;
        return getList(streamSupplier, streamSupplier.get().mapToInt(Integer::intValue).sum() % 2 != 0);
    }

    private static List<Integer> getList(Supplier<Stream<Integer>> streamSupplier, boolean sumResult) {
        return streamSupplier.get().filter(a -> (a % 2 == 0) == sumResult).collect(Collectors.toList());
    }
}
