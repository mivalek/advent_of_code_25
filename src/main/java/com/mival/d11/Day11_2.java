package com.mival.d11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mival.utils.Util;


public class Day11_2 {
    static Map<String, Node> data;
    static Map<String, Long> cache = new HashMap<>();
    static class Node {
        String label;
        List<Node> children;

        public Node(String label) {
            this.label = label;
            children = new ArrayList<>();
        }

        public void addChild(Node child) {
            children.add(child);
        }
    }

    public static long dfs(Node from, String to, String parentLabel, List<String> avoid) {
        String label = from.label;
        if (cache.containsKey(label)) {
            return cache.get(label);
        }
        if (avoid.contains(label)) return 0;
        if (label.equals(to)) {
                cache.put(parentLabel, 1L);
                return 1;
        }
        long out = from.children
                    .stream()
                    .map(x -> dfs(x, to, label, avoid))
                    .reduce(0L, Long::sum);
        cache.put(label, out);
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
            String p = elems[0];
            boolean isP = data.containsKey(p);
            Node parent = isP ? data.get(p) : new Node(elems[0]);
            if (!isP) data.put(elems[0], parent);
            for (String c: elems[1].split(" ")) {
                boolean isC = data.containsKey(c);
                Node child = isC ? data.get(c) : new Node(c);
                parent.addChild(child);
                if (!isC) data.put(c, child);
            }
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
                long result = dfs(data.get(sp[0]), sp[1], null, avoid);
                subtotal *= result;
            }
            res += subtotal;
        }
        System.out.println(res);
    }
}
