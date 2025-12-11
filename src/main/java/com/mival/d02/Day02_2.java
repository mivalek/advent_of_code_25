package com.mival.d02;

import java.io.IOException;
import java.util.List;


import com.mival.utils.Util;

public class Day02_2 {

    static boolean isIdInvalid(long id) {
        boolean out = false;
        String idStr = String.valueOf(id);
        for (int i = 1; i <= idStr.length() / 2; i++) {
            String sub1 = idStr.substring(0, i);
            String sub2 = idStr.substring(i);
            boolean contains = sub2.contains(sub1);
            boolean hasOther = sub2.replaceAll(sub1, "").length() > 0;
            out = contains && !hasOther;
            if (out) break;
        }
        return out;
    }
    public static void main (String[] args) {
        String fileName = "d02_1" + (
            args.length != 0 && args[0].equals("test")
            ? "_test"
            : ""
        );
        long result = 0;
        try {
            List<String> input = Util.readInput(fileName);
            for (String line: input) {
                String[] ranges = line.split(",");

                for (String range: ranges) {
                    String[] rangeValues = range.split("-");
                    String start = rangeValues[0];
                    String stop = rangeValues[1];

                    long startNum = Long.parseLong(start);
                    long stopNum = Long.parseLong(stop);
                    if (start.length() > stop.length()){
                        throw new RuntimeException("invalid range: " + range);
                    }
                   
                    for (long i = startNum; i <= stopNum; i++) {
                        if (isIdInvalid(i)) {
                            result += i;
                        }
                    }
                }
            }
        } catch (IOException ioException) {
            System.err.println("error: " + ioException);
        }
        
        System.err.println(result);
    }
}
