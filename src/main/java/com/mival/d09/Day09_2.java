package com.mival.d09;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.mival.utils.Util;

public class Day09_2 {
    // for dimension compression
    // sorted sets of x and y coords
    public static TreeSet<Integer> xs = new TreeSet<>();
    public static TreeSet<Integer> ys = new TreeSet<>();
    public static int[][] points;
    
    // prefix sum array
    public static class Psa {
        int[][] data;

        public Psa(Grid grid) {
            data = new int[grid.ncol][grid.nrow];

            for (int i = 0; i < grid.ncol; i++) {
                for (int j = 0; j < grid.nrow; j++) {
                    int left = i > 0 ? data[i - 1][j]: 0;
                    int above = j > 0 ? data[i][j - 1]: 0;
                    int diag = i > 0 && j > 0 ? data[i - 1][j - 1]: 0;
                    data[i][j] = left + above - diag + grid.data[i][j];
                }
            }
        }

        // if shape consists of only 1s and outside grid of 0s
        // returned sum represents sum of grid fields for a
        // compressed rect given by input vertices
        public int getSum(int cx1, int cx2, int cy1, int cy2) {
            int left = cx1 > 0 ? data[cx1 - 1][cy2] : 0;
            int above = cy1 > 0 ? data[cx2][cy1 - 1] : 0;
            int diag = cx1 > 0 && cy1 > 0 ? data[cx1 - 1][cy1 - 1] : 0;
            return data[cx2][cy2] - left - above + diag;
        }
        // given diagonal vertices, returns true if rect
        // is fully inside shape
        public boolean validate(int x1, int x2, int y1, int y2) {
            List<Integer> cx = compress(x1, x2, xs);
            List<Integer> cy = compress(y1, y2, ys);
            int cx1 = cx.getFirst();
            int cx2 = cx.getLast();
            int cy1 = cy.getFirst();
            int cy2 = cy.getLast();

            int compressedArea = (cx2 - cx1 + 1) * (cy2 - cy1 + 1);
            int count = getSum(cx1, cx2, cy1, cy2);
            return count == compressedArea;
        }
        public void print() {
            for (int c = 0; c < data.length; c++) {
                for (int r = 0; r < data[0].length; r++) {
                    System.out.print(data[r][c] + "\t");
                }
                System.out.println();
            }
        }
    }

    public static class Grid {
        int[][] data;
        int nrow;
        int ncol;

        public Grid() {
            // each element in xs and ys - except last - represents
            // uncompressed column/row + compressed column/row, hence * 2 - 1
            data = new int[xs.size() * 2 - 1][ys.size() * 2 - 1];
            nrow = data[0].length;
            ncol = data.length;
            populate();
            floodFill();
        }

        void populate() {
            for (int i = 0; i < points.length; i++) {
                int x1 = points[i][0];
                int y1 = points[i][1];
                int x2 = points[(i + 1) % points.length][0];
                int y2 = points[(i + 1) % points.length][1];

                List<Integer> cx = compress(x1, x2, xs);
                List<Integer> cy = compress(y1, y2, ys);

                for (int c = cx.getFirst(); c < cx.getLast() + 1; c++) {
                    for (int r = cy.getFirst(); r < cy.getLast() + 1; r++) {
                        data[c][r] = 1;
                    }
                }
            }
        }

        void floodFill() {
            Set<List<Integer>> outside = new HashSet<>();
            outside.add(Arrays.asList(-1, -1));
            List<int[]> queue = new ArrayList<>();
            queue.add(new int[]{-1, -1});

            // get all points that lie outwith the shape
            while (queue.size() > 0) {
                // System.out.println(queue.size());
                int[] pos = queue.removeFirst();
                // all 4-way directions
                int[][] nextPos = {
                    {pos[0] - 1, pos[1]},
                    {pos[0] + 1, pos[1]},
                    {pos[0], pos[1] - 1},
                    {pos[0], pos[1] + 1}
                }; 
                for (int[] p: nextPos) {
                    // grid is padded by 1
                    if (p[0] < -1 || p[1] < -1 || p[0] > ncol || p[1] > nrow) continue;
                    if (0 <= p[0] && p[0] < ncol && 0 <= p[1] && p[1] < nrow && data[p[0]][p[1]] == 1) continue;
                    List<Integer> pList = Arrays.asList(p[0], p[1]);
                    if (outside.contains(pList)) continue;
                    outside.add(pList);
                    queue.add(p);
                }
            }

            for (int i = 0; i < ncol; i++) {
                for (int j = 0; j < nrow; j++) {
                    if (!outside.contains(Arrays.asList(i, j))) data[i][j] = 1;
                }
            }
        }

        public void print() {
            for (int c = 0; c < data.length; c++) {
                for (int r = 0; r < data[0].length; r++) {
                    System.out.print(data[r][c] == 0 ? '.' : '#');
                }
                System.out.println();
            }
        }
    };
 
    public static List<Integer> compress(int p1, int p2, TreeSet<Integer> set) {
        List<Integer> out = Arrays.asList(
                    // .headSet(x).size() finds index of x
                    // * 2 because each element represents
                    // 1 uncompressed + 1 compressed column/row
                    set.headSet(p1).size() * 2,
                    set.headSet(p2).size() * 2
                );
        out.sort(null);
        return out;
    }
    public static void main(String[] args) {
        String fileName = "d09_1" + (args.length != 0 && args[0].equals("test") ? "_test" : "");
        long result = 0;
        List<String> input = new ArrayList<>();
        try {
            input = Util.readInput(fileName);
        } catch (IOException ioException) {
            System.err.print(ioException);
        }

        points = new int[input.size()][];
        
        for (int i = 0; i < input.size(); i++) {
            int[] arr = Arrays
                .stream(input.get(i).split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
            
            points[i] = arr;
            xs.add(arr[0]);
            ys.add(arr[1]);
        };
     
        Grid grid = new Grid();
        // grid.print();

        Psa psa = new Psa(grid);
        // psa.print();

        for (int i = 0; i < points.length; i++) {
            int x1 = points[i][0];
            int y1 = points[i][1];
            for (int j = i+1; j <= points.length; j++ ) {
                if (i == j % points.length) continue;
                int x2 = points[j % points.length][0];
                int y2 = points[j % points.length][1];
                if (!psa.validate(x1, x2, y1, y2)) continue;
                long area = (long) (Math.abs(x2 - x1) + 1) * (long) (Math.abs(y2 - y1) + 1);
                if (area > result) result = area;
            }

        }
    System.out.println(result);
    }
}
