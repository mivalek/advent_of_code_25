package com.mival.d10;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.mival.utils.Util;

public class Day10_2 {
    private static Set<String> cache = new HashSet<>();
    private static Map<String, int[]> stateCache = new HashMap<>();

    static long good = 0;
    static long bad = 0;

    static class State {
        int[] value;
        int[] joltage;
        public State(int[] jolt) {
            value = new int[jolt.length];
            joltage = jolt;
        }

        public void update(int[] x) {
            for (int i: x) {
                value[i]++; 
            }
        }
        public boolean validate() {
            boolean success = true;
            for (int i = 0; i < joltage.length; i++) {
                if (joltage[i] != value[i]) {
                    success = false;
                }
            }
            return success;
        }
        public boolean overshot() {
            boolean out = false;
            for (int i = 0; i < joltage.length; i++) {
                if (joltage[i] < value[i]) {
                    out = true;
                }
            }
            return out;
        }

        public void print() {
            System.out.println(Arrays.toString(value));
        }
    }
    
    public static class Combinations {
        int n;
        int k;
        long max;
        long current;
        boolean finished;
        public Combinations(int n, int k) {
            this.n = n;
            this.k = k;
            finished = false;
            String maxStr = "";
            for (int i = 0; i < k; i++) {
                maxStr += n-1;
            }
            max = Integer.parseInt(maxStr, n);
            current = 0;
        }
        public int[] next() {
            int[] out = new int[k];
            
            String baseN = String.format("%0" + k + "d", Long.parseLong(Long.toString(current, n)));
            for (int i = 0; i < k; i++) {
                out[i] = Character.getNumericValue(baseN.charAt(i));
            }
            if (current == max) finished = true;
            current++;
            return out;
        }
        public static String toString(int[] x) {
            return Arrays.stream(x)
                    .sorted()
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining());
        }

        public static boolean isValid(String x) {
            for (String c: cache) {
                if (x.contains(c)) return false;
            }
            return true;
        }
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
            cache.clear();
            String[] elements = line.split(" ");
            String[] joltageStr = elements[elements.length - 1]
                .substring(1, elements[elements.length - 1].length() - 1)
                .split(",");
            int[] joltage = Arrays
                    .stream(joltageStr)
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int[][] buttons = new int[elements.length - 2][];
            for (int i = 1; i < elements.length - 1; i++) {
                String[] bttnsString = elements[i]
                    .substring(1, elements[i].length() - 1)
                    .split(",");
                buttons[i - 1] = Arrays.stream(bttnsString)
                    .mapToInt(Integer::parseInt)
                    .toArray();
            }

            int out = 0;
            int totalJolt = 0;
            for (int j: joltage) {
                totalJolt += j;
            }
            int maxJoltPerBtn = 0;
            for (int[] btn: buttons) {
                if (btn.length > maxJoltPerBtn) maxJoltPerBtn = btn.length;
            }
            int minK = Math.ceilDiv(totalJolt , maxJoltPerBtn);
            int i = minK;
            System.out.println(minK);
            mainLoop:
            while (true) {
                Combinations combinations = new Combinations(buttons.length, i);
                while (!combinations.finished) {
                    int[] comb = combinations.next();
                    String key = Combinations.toString(comb);
                    if (!Combinations.isValid(key)) {
                        // System.out.println(Arrays.toString(comb));
                        bad++;
                        continue;
                    }
                    State state = new State(joltage);
                        // System.out.println("comb:" + Arrays.toString(comb));

                    for (int idx = 0; idx < comb.length; idx++) {
                        int c = comb[idx];
                        int[] btn = buttons[c];
                        // System.out.println(Arrays.toString(btn));
                        state.update(btn);
                    System.out.print("k: " + i + "\tcomb: " + Arrays.toString(comb) + "\tgood: " + good + "\tbad: " + bad + "\r");
                        if (state.overshot()) {
                            // state.print();
                            // System.out.println(Arrays.toString(comb));
                            cache.add(key);
                            bad++;
                            break;
                        }
                        good++;
                        boolean success = state.validate();
                        if (success) {
                            out = i;
                            break mainLoop;
                        }
                    }
                }
                i++;
            }
            result += out == 0 ? buttons.length : out;
        }
        System.out.println(result);
    }
}
