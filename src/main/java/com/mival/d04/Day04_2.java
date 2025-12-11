package com.mival.d04;

import java.io.IOException;
import java.util.List;

import com.mival.utils.Util;

public class Day04_2 {
    public static void main(String[] args) {
        String fileName = "d04_1" + (args.length != 0 && args[0].equals("test") ? "_test" : "");
        long result = 0;
        long prevResult = -1;
        try {
            List<String> input = Util.readInput(fileName);
            // pad all around to avoid of edge-cases
            int[][] data = new int[input.size() + 2][input.getFirst().length() + 2];
            int i = 0;

            // fill in data with 1s
            for (String line: input) {
                if (line.isBlank()) continue;

                for (int j = 0; j < line.length(); j++) {
                    if ('@' == line.charAt(j)) data[i + 1][j + 1] = 1;
                }
                i++;
            }
            while (prevResult != result) {
                prevResult = result;
                for (i = 1; i < data.length - 1; i++) {
                    for (int j = 1; j < data[0].length - 1; j++) {
                        if (data[i][j] == 0) {
                            continue;
                        }
                        // get neighbours
                        int nNeigbours = 0;
                        for (int h = -1; h < 2; h++) {
                            for (int v = -1; v < 2; v++) {
                                if (h == 0 && v == 0) continue;
                                nNeigbours += data[i + h][j + v];
                            }
                            // early stopping
                            if (nNeigbours > 3) break;
                        }
                        if (nNeigbours < 4) {
                            result++;
                            data[i][j] = 0; // remove roll
                        }
                    }
                }
            }
          } catch (IOException ioException) {
            System.err.print(ioException);
        }

        System.err.println(result);
    }
}

