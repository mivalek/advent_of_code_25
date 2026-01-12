package com.mival.d09;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.mival.utils.Util;

public class Day09_1 {
    public static void main(String[] args) {
        String fileName = "d09_1" + (args.length != 0 && args[0].equals("test") ? "_test" : "");
        long result = 0;
        List<String> input = new ArrayList<>();
        try {
            input = Util.readInput(fileName);
        } catch (IOException ioException) {
            System.err.print(ioException);
        }

        long[][] data = new long[input.size()][];
        for (int i = 0; i < input.size(); i++) {
            long[] arr = Arrays.stream(input.get(i).split(","))
                        .mapToLong(Long::parseLong)
                        .toArray();
            for (int j = 0; j < data.length; j++) {
                if (data[j] == null) break;
                long area = (Math.abs(arr[0] - data[j][0]) + 1) * (Math.abs(arr[1] - data[j][1]) + 1);
                if (area > result) result = area;
            }
            data[i] = arr;
        };
        System.out.println(result);
    }
}
