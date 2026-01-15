package com.mival.d11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mival.utils.Util;


public class Day11_1 {
    static Map<String, String[]> data;
    static Map<String, Long> cache = new HashMap<>();

    public static long dfs(String from, String parentLabel) {
        if (cache.containsKey(from)) {
            return cache.get(from);
        }
        if (from.equals("out")) {
                cache.put(parentLabel, 1L);
                return 1;
        }
        String children[] = data.get(from);
        long out = Arrays.asList(children)
                    .stream()
                    .map(x -> dfs(x, from))
                    .reduce(0L, Long::sum);
        cache.put(from, out);
        return out;
    }
    

    public static void main(String[] args) {
        String fileName = "d11_1" + (args.length != 0 && args[0].equals("test") ? "_test" : "");
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
        long res = dfs("you", null);
        System.out.println(res);
    }
}
