package com.mival.d11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mival.utils.Util;


public class Day11_2 {
    static Map<String, String[]> data;
    static Map<String, Long> cache = new HashMap<>();

    public static long dfs(String from, String to, String parentLabel, List<String> avoid) {
        if (cache.containsKey(from)) {
            return cache.get(from);
        }
        if (avoid.contains(from)) return 0;
        if (from.equals(to)) {
                cache.put(parentLabel, 1L);
                return 1;
        }
        String children[] = data.get(from);
        long out = Arrays.asList(children)
                    .stream()
                    .map(x -> dfs(x, to, from, avoid))
                    .reduce(0L, Long::sum);
        cache.put(from, out);
        return out;
    }
    

    public static void main(String[] args) {
        String fileName = "d11_2" + (args.length != 0 && args[0].equals("test") ? "_test" : "");
        List<String> input = new ArrayList<>();
        try {
            input = Util.readInput(fileName);
        } catch (IOException ioException) {
            System.err.print(ioException);
        }

        data = new HashMap<>();
        for (String line: input) {
            String[] elems = line.split(": ");
            String parent = elems[0];
            String[] children = elems[1].split(" ");
            data.put(parent, children);
        }
        long res = 0;

        String[][] paths = new String[][] {
            {"svr", "fft", "dac", "out"},
            {"svr", "dac", "fft", "out"},
        };
        for (String[] p: paths) {
            String[][] subpaths = new String[3][2];
            for (int i = 0; i < p.length - 1; i++) {
                subpaths[i][0] = p[i];
                subpaths[i][1] = p[i + 1];
            }
            long subtotal = 1;
            for (String[] sp: subpaths) {
                cache.clear();
                List<String> avoid = new ArrayList<>();
                for (String a: new String[]{"fft", "dac", "out"}) {
                    if (a != sp[0] && a != sp[1]) avoid.add(a);
                }
                long result = dfs(sp[0], sp[1], null, avoid);
                subtotal *= result;
            }
            res += subtotal;
        }
        System.out.println(res);
    }
}
