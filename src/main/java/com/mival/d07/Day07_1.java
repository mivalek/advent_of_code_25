package com.mival.d07;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mival.utils.Util;

public class Day07_1 {
    public static void main(String[] args) {
        String fileName = "d07_1" + (args.length != 0 && args[0].equals("test") ? "_test" : "");
        long result = 0;
        List<String> input = new ArrayList<>();
        try {
            input = Util.readInput(fileName);
        } catch (IOException ioException) {
            System.err.print(ioException);
        }
        if (input.size() == 0) throw new Error("Input file is empty");
        // position of starting point
        Integer start = input.get(0).indexOf('S', 0);
        Set<Integer> positions = new HashSet<Integer>();
        positions.add(start);
        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);
            if (line.isBlank()) break;
            if (i % 2 != 0) continue;
            // System.out.println(line);
            for (Integer j = 0; j < line.length(); j++) {
                if (line.charAt(j) != '^') continue;
                if (positions.contains(j)) {
                    positions.remove(j);
                    result++;
                    if (j > 0) {
                        positions.add(j-1);
                    }
                    if (j < line.length()) {
                        positions.add(j+1);
                    }
                }
            }
            // System.out.println(positions);
        }
        System.err.println(result);
    }
}
