package com.mival.d11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mival.utils.Util;


public class Day11_1 {
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

    public static long dfs(Node startNode, String parentLabel) {
        String label = startNode.label;
        if (cache.containsKey(label)) {
            return cache.get(label);
        }
        if (label.equals("out")) {
                cache.put(parentLabel, 1L);
                return 1;
        }
        long out = startNode.children
                    .stream()
                    .map(x -> dfs(x, label))
                    .reduce(0L, Long::sum);
        cache.put(label, out);
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
        long res = dfs(data.get("you"), null);
        System.out.println(res);
    }
}
