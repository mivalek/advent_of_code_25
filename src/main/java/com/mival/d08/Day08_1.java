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
    public class Day08_1 {
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
            
            public double getDistance(Box b) {
                return Math.sqrt(Math.pow(this.x - b.x, 2) + Math.pow(this.y - b.y, 2) + Math.pow(this.z - b.z, 2));
            }
        }
        public static void main(String[] args) {
            boolean isTest = args.length != 0 && args[0].equals("test");
            String fileName = "d08_1" + (isTest ? "_test" : "");
            int MAX_ITER = isTest ? 10 : 1000;
            List<String> input = new ArrayList<>();
            List<Box> allBoxes = new ArrayList<>();
            List<Integer> allIDs = new ArrayList<>();
            List<Set<Integer>> clusters = new ArrayList<>();
            Map<Double, int[]> distances = new TreeMap<>();
            try {
                input = Util.readInput(fileName);
            } catch (IOException ioException) {
                System.err.print(ioException);
            }
            for (int i = 0; i < input.size(); i++) {
                Box b = new Box(input.get(i));
                for (int j = 0; j < allBoxes.size(); j++) {
                    double dist = b.getDistance(allBoxes.get(j));
                    distances.put(dist, new int[]{i, j});
                }
                allBoxes.add(b);
                allIDs.add(i);
            }

            int iter = 0;
            for (Map.Entry<Double, int[]> dist: distances.entrySet()) {
                if (iter == MAX_ITER) break;
                int[] val = dist.getValue();
                Set<Integer> c = new HashSet<>(Arrays.asList(val[0], val[1]));
                clusters.add(c);
                iter++;
            }
            while (allIDs.size() != 0) {
                int i = allIDs.removeFirst();
                Set<Integer> clust = clusters.stream().filter(c -> c.contains(i)).reduce(new HashSet<>(), (acc, el) -> {
                    acc.addAll(el);
                    return acc;
                });
                if (clust.size() == 0) continue;
                clusters = clusters.stream().filter(c -> !c.contains(i)).collect(Collectors.toList());
                clusters.add(clust);
            }
            long result = clusters
                .stream()
                .map(x -> x.size())
                .sorted((a, b) -> b - a)
                .limit(3)
                .reduce(1, Math::multiplyExact);
            System.out.println(result);
        }
    }
