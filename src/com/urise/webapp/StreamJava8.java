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
        int[] valArray = Arrays.stream(values).sorted().distinct().toArray();
        int length = valArray.length;
        int result = 0;
        for (int i = 0; i < length; i++) {
            int value = valArray[i];
            value = (int) (value * Math.pow(10, length - i - 1));
            result += value;
        }
        return result;
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Supplier<Stream<Integer>> streamSupplier = integers::stream;
        if (streamSupplier.get().mapToInt(Integer::intValue).sum() % 2 != 0) {
            return streamSupplier.get().filter((a) -> a % 2 == 0).collect(Collectors.toList());

        } else {
            return streamSupplier.get().filter((a) -> a % 2 != 0).collect(Collectors.toList());
        }
    }
}
