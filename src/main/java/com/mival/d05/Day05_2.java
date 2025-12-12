package com.mival.d05;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.mival.utils.Util;

public class Day05_2 {
    public static void main(String[] args) {
        String fileName = "d05_1" + (args.length != 0 && args[0].equals("test") ? "_test" : "");
        long result = 0;
        try {
            List<String> input = Util.readInput(fileName);
            // get ranges
            List<Long[]> rawRanges = new ArrayList<>();
            for (String line: input) {
                if (line.isBlank()) break;

                Long[] range = Stream.of(line.split("-"))
                        .map(Long::parseLong)
                        .toArray(Long[]::new);
                rawRanges.add(range);
            }
            rawRanges.sort((a, b) -> a[0].compareTo(b[0]));
            
            // simplify ranges
            List<Long[]> ranges = new ArrayList<>();
            ranges.add(rawRanges.getFirst());
            for (int i = 1; i < rawRanges.size(); i++) {
                Long[] prevRange = ranges.getLast();
                Long[] thisRange = rawRanges.get(i);
                if (thisRange[0] <= prevRange[1]) {
                    if(thisRange[1] > prevRange[1]) {
                        prevRange[1] = thisRange[1];
                        ranges.set(ranges.size() - 1, prevRange);
                    }
                } else {
                    ranges.add(thisRange);
                }
            }
         
            for (Long[] range: ranges){
                result += range[1] - range[0] + 1;
            }
        } catch (IOException ioException) {
            System.err.print(ioException);
        }

        System.err.println(result);
    }
}

