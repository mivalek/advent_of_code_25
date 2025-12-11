package com.mival.d03;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mival.utils.Util;

public class Day03_2 {
    
    public static void main(String[] args) {
        String fileName = "d03_1" + (args.length != 0 && args[0].equals("test") ? "_test" : "");
        long result = 0;
        int size = 12;
        try {
            List<String> input = Util.readInput(fileName);
            for (String line: input) {
                if (line.isBlank()) continue;

                int len = line.length();
                int startIdx = 0;
                List<Integer> joltDigits = new ArrayList<>(Collections. nCopies(size, 0));
                int[] digits = new int[len];

                // chars to digits
                 for (int i = 0; i < len; i++) {
                    int digit =  Integer.parseInt(line.substring(i, i+1));
                    digits[i] = digit;
                }

                // for each of size elements...
                for (int i = 0; i < size; i++) {
                    // ..for each of valid positions..
                    for (int j = startIdx; j <= len - size + i; j++) {
                        int digit = digits[j];
                        // find highest digit
                        if (digit > joltDigits.get(i)) {
                            joltDigits.set(i, digit);
                            // left-limit valid positions to search for next element
                            startIdx = j + 1;
                        }
                    }
                }

                // [6, 2, 1] -> 621
                long jolt = 0;
                for (int i = 0; i < size; i++) {
                    jolt += joltDigits.get(i) * Math.pow(10, size - i - 1);
                }

                result += jolt;
            }

        } catch (IOException ioException) {
            System.err.print(ioException);
        }

        System.err.println(result);
    }
}
