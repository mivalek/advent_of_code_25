package com.mival.d07;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mival.utils.Util;

public class Day07_2 {
    private static Map<String, Long> memo = new HashMap<>();

    public static long solve(int r, int c, char[][] data) {
        if (r == data.length) return 1;
        String key = r + "," + c;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        long out;
        if (data[r][c] == '^') {
            out = solve(r + 1, c + 1, data) + solve(r + 1, c - 1, data);
        } else out = solve(r+1, c, data);

        memo.put(key, out);
        return out;
    }

    public static void main(String[] args) {
        String fileName = "d07_1" + (args.length != 0 && args[0].equals("test") ? "_test" : "");
        List<String> input = new ArrayList<>();
        try {
            input = Util.readInput(fileName);
        } catch (IOException ioException) {
            System.err.print(ioException);
        }
        if (input.size() == 0) throw new Error("Input file is empty");
        int nrow = input.size();
        int ncol = input.getFirst().length();
        char[][] grid = new char[nrow][ncol];
        int start = input.getFirst().indexOf('S', 0);
        for (int i = 0; i < nrow; i++) {
            String line = input.get(i);
            for (int j = 0; j < ncol; j++) {
                grid[i][j] = line.charAt(j);
            }
        }
        long result = solve(0, start, grid);
        System.out.println(result);
    }
}