package com.mival.d10;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mival.utils.Util;

public class Day10_1 {
    public static List<List<Integer>> binaryCombinations(int n, int k) {
        int nBin = (int) (1 << n) - 1;

        List<List<Integer>> out = new ArrayList<>();
        for (int i = 1; i < nBin; i++) {
            List<Integer> bits = Arrays.asList(Integer.toBinaryString(i).split(""))
                .stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
            while (bits.size() < n) bits.addFirst(0);
                
            int nOnes = bits.stream().reduce(0, Integer::sum);
            if (nOnes == k) {
                out.add(bits);
            }
        }
        return out;
    }
    public static void main(String[] args) {
        String fileName = "d10_1" + (args.length != 0 && args[0].equals("test") ? "_test" : "");
        List<String> input = new ArrayList<>();
        try {
            input = Util.readInput(fileName);
        } catch (IOException ioException) {
            System.err.print(ioException);
        }

        long result = 0;
        for (String line: input) {
            String[] elements = line.split(" ");
            String lightsStr = elements[0]
                .substring(1, elements[0].length() - 1)
                .replaceAll("\\.", "0")
                .replaceAll("#", "1");
            int lights = Integer.parseInt(lightsStr, 2);

            // for N lights, a button that toggles ith light from the left
            // is base >> i
            // base is an N-bit binary with 1 on the left and 0s else:
            // N = 5 -> base = 16 (binary 10000)
            int base = (int) Math.pow(2, lightsStr.length() - 1);
            int[] buttons = new int[elements.length - 2];
            for (int i = 1; i < elements.length - 1; i++) {
                String[] bttnsString = elements[i]
                    .substring(1, elements[i].length() - 1)
                    .split(",");
                buttons[i - 1] = Arrays
                    .stream(bttnsString)
                    .map(Integer::parseInt)
                    .reduce(0, (acc, x) -> acc + (base >> x));
            }

            int out = 0;
            mainLoop:
            for (int i = 1; i < buttons.length; i++) {
                List<List<Integer>> combs = binaryCombinations(buttons.length, i);
                for (List<Integer> comb: combs) {
                    int state = 0;
                    for (int idx = 0; idx < comb.size(); idx++) {
                        int c = comb.get(idx);
                        if (c == 0) continue;
                        state ^= buttons[idx];
                        if (state == lights) {
                            out = i;
                            break mainLoop;
                        }
                    }
                }
            }
            result += out == 0 ? buttons.length : out;
        }
        System.out.println(result);
    }
}
