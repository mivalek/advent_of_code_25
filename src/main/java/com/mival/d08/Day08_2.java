package com.mival.d08;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.mival.utils.Util;

public class Day08_2 {
    static class Box {
        int x;
        int y;
        int z;
        
        Box(String s) {
            String[] coords = s.split(",");
            this.x = Integer.parseInt(coords[0]);
            this.y = Integer.parseInt(coords[1]);
            this.z = Integer.parseInt(coords[2]);
        }

        public int getX() {
            return this.x;
        }

        public double getDistance(Box b) {
            return Math.sqrt(Math.pow(this.x - b.x, 2) + Math.pow(this.y - b.y, 2) + Math.pow(this.z - b.z, 2));
        }
    }
    public static void main(String[] args) {
        String fileName = "d08_1" + (args.length != 0 && args[0].equals("test") ? "_test" : "");
        List<String> input = new ArrayList<>();
        try {
            input = Util.readInput(fileName);
        } catch (IOException ioException) {
            System.err.print(ioException);
        }
        
        List<Box> allBoxes = new ArrayList<>();
        List<Set<Integer>> cluster = new ArrayList<>();
        Map<Double, List<Integer>> distances = new TreeMap<>();
        for (int i = 0; i < input.size(); i++) {
            Box b = new Box(input.get(i));
            for (int j = 0; j < allBoxes.size(); j++) {
                double dist = b.getDistance(allBoxes.get(j));
                distances.put(dist, Arrays.asList(i, j));
            }
            allBoxes.add(b);
            cluster.add(new HashSet<>(Arrays.asList(i)));
        }
        List<List<Integer>> edges = new ArrayList<>(distances.values());
        List<Integer> edge = new ArrayList<>();

        while (cluster.getFirst().size() < allBoxes.size()) {
            edge = edges.removeFirst();
            int b1 = edge.getFirst();
            int b2 = edge.getLast();
            Set<Integer> newClust = cluster
                .stream()
                .filter(x -> x.contains(b1) || x.contains(b2))
                .reduce(new HashSet<>(), (acc, el) -> {
                    acc.addAll(el);
                    return acc;
                });
            cluster = cluster
                .stream()
                .filter(x -> !x.contains(b1) && !x.contains(b2))
                .collect(Collectors.toList());
            cluster.add(newClust);
        }
        
        int b1 = edge.getFirst();
        int b2 = edge.getLast();
        long result = (long) allBoxes.get(b1).getX() * (long) allBoxes.get(b2).getX();
        System.out.println(result);
    }
}
