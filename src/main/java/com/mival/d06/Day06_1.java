package com.mival.d06;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import com.mival.utils.Util;

public class Day06_1 {
    public static void main(String[] args) {
        String fileName = "d06_1" + (args.length != 0 && args[0].equals("test") ? "_test" : "");
        long result = 0;
        try {
            List<String> input = Util.readInput(fileName);
            // get ops
            List<String> ops = new ArrayList<>();
            for (String line: input.reversed()) {
                if (line.isBlank()) continue;
                ops = Arrays.asList(line.trim().split("\s+"));
                break;
                
            }

            // get numbers
            Long[][] data = new Long[ops.size()][input.size() - 1];
            for (int i = 0; i < input.size(); i++) {
                String line = input.get(i);
                if (line.isBlank()) break;
                String[] splitLine = line.trim().split("\s+");

                for (int j = 0; j < splitLine.length; j++) {
                    try {
                    Long parsed = Long.parseLong(splitLine[j]); 
                    data[j][i] = parsed;
                    } catch (NumberFormatException exception) {
                        break;
                    }
                }
            }

            if (ops.size() != data.length) {
                throw new Error("ops and data not commutable");
            }

            for (int i = 0; i < ops.size(); i++) {
                String op = ops.get(i);
                long lineResult = 0;
                if (op.equals("*")) {
                    lineResult = Stream.of(data[i]).reduce(1L, (a, b) -> a * b);
                } else if (op.equals("+")) {
                    lineResult = Stream.of(data[i]).reduce(0L, (a, b) -> a + b);
                }
                result += lineResult;
            }
            
        } catch (IOException ioException) {
            System.err.print(ioException);
        }

        System.err.println(result);
    }
}

