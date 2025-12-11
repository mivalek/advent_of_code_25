package com.mival.d03;

import java.io.IOException;
import java.util.List;

import com.mival.utils.Util;

public class Day03_1 {
    
    public static void main(String[] args) {
        String fileName = "d03_1" + (args.length != 0 && args[0].equals("test") ? "_test" : "");
        int result = 0;
        try {
            List<String> input = Util.readInput(fileName);
            for (String line: input) {
                if (line.isBlank()) continue;

                int len = line.length();
                String[] chars = line.split("");
                int[] digits = new int[len];
                for (int i = 0; i < len; i++) {
                    digits[len - 1 - i] = Integer.parseInt(chars[i]);
                }
                int firstDigit = 0;
                int firstDigitIndex = 0;
                int secondDigit = 0;
                // first pass to get 1st digit
                for (int i = 1; i < digits.length; i++) {
                    int f = digits[i];

                    if (f >= firstDigit) {
                        firstDigit = f;
                        firstDigitIndex = i;
                    }
                }
                // second pass - 2nd digit
                for (int i = 0; i < digits.length - 1; i++) {
                    if (firstDigitIndex == i) break;
                    int s = digits[i];
                    
                    // System.out.println(firstDigit);
                    if (s > secondDigit) secondDigit = s;
                }
                int jolt = firstDigit * 10 + secondDigit;
                result += jolt;
            }

        } catch (IOException ioException) {
            System.err.print(ioException);
        }

        System.err.println(result);
    }
}
