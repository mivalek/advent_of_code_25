package com.mival.d12;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mival.utils.Util;

public class Day12_1 {
    public static void main(String[] args) {
        String fileName = "d12_1" + (args.length != 0 && args[0].equals("test") ? "_test" : "");
        long result = 0;
        List<String> input = new ArrayList<>();
        try {
            input = Util.readInput(fileName);
        } catch (IOException ioException) {
            System.err.print(ioException);
        }

        for (String line: input) {
            String[] elems = line.split(": ");
            int capacity = Arrays.asList(elems[0].split("x"))
                .stream()
                .map(x -> Integer.parseInt(x) / 3)
                .reduce(1, (acc, res) -> acc * res);
            int nPresents = Arrays.asList(elems[1].split(" "))
                .stream()
                .map(Integer::parseInt)
                .reduce(0, (acc, res) -> acc + res);

            if (capacity >= nPresents) result++;
        }
        System.out.println(result);
    }
}
