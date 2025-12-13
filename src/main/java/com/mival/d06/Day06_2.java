package com.mival.d06;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mival.utils.Util;

public class Day06_2 {
    public static void main(String[] args) {
        String fileName = "d06_1" + (args.length != 0 && args[0].equals("test") ? "_test" : "");
        long result = 0;
        try {
            List<String> input = Util.readInput(fileName);
            // get ops
            List<String> ops = new ArrayList<>();

            for (int i = input.size() - 1; i >= 0; i--) {
                String line = input.removeLast();
                if (line.isBlank()) continue;
                ops = Arrays.asList(line.trim().split("\s+")).reversed();
                break;
            }

            // get numbers
            String[][] data = new String[input.get(0).length()][input.size()];
            int ncol = data[0].length;
            for (int i = 0; i < ncol; i++) {
                String[] chars = input.get(i).split("");
                for (int j = 0; j < chars.length; j++) {
                    data[chars.length - j - 1][i] = chars[j];
                }
            }

            int i = 0;
            long opResult = ops.get(i).equals("+") ? 0 : 1;
            for (String[] line: data) {
                String str = String.join("", line).trim();
                
                if (str.isBlank()) {
                    i++;
                    result += opResult;
                    opResult = ops.get(i).equals("+") ? 0 : 1;
                    continue;
                }
                Long num = Long.parseLong(str);

                if (ops.get(i).equals("+")) {
                    opResult += num;
                } else {
                    opResult *= num;
                }
            }
            result += opResult;            
        } catch (IOException ioException) {
            System.err.print(ioException);
        }

        System.err.println(result);
    }
}

