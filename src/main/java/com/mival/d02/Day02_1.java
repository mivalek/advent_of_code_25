package com.mival.d02;

import java.io.IOException;
import java.util.List;


import com.mival.utils.Util;

public class Day02_1 {

    static long[] getHalves(String input) {
        long[] out = new long[2];
        int halfLen = input.length() / 2;
        out[0] = Long.parseLong(input.substring(0, halfLen));
        out[1] = Long.parseLong(input.substring(halfLen));
        return out;
    };

    static long makeNumFromHalf(long half) {
        String halfStr = String.valueOf(half);
        return Long.parseLong(halfStr + halfStr);
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

                    boolean isStartLengthEven = start.length() / 2.0 == start.length() / 2;

                    boolean isStopLengthEven = stop.length() / 2.0 == stop.length() / 2;

                    if (!isStartLengthEven && !isStopLengthEven) continue;
                    long startNum = Long.parseLong(start);
                    long stopNum = Long.parseLong(stop);
                    if (start.length() > stop.length()){
                        throw new RuntimeException("invalid range: " + range);
                    } else if (!isStartLengthEven) {
                        startNum = Math.round(Math.pow(10, stop.length() - 1));
                        start = String.valueOf(startNum);
                    } else if (!isStopLengthEven) {
                        stopNum = Math.round(Math.pow(10, start.length())) - 1;
                        stop = String.valueOf(stopNum);
                    }
                    long[] candStartHalves = getHalves(start);
                    long[] candStopHalves = getHalves(stop);

                    if (candStartHalves[0] < candStartHalves[1]) {
                        candStartHalves[0] += 1;
                    }
                    if (candStopHalves[0] > candStopHalves[1]) {
                        candStopHalves[0] -= 1;
                    }
                   
                    for (long i = candStartHalves[0]; i <= candStopHalves[0]; i++) {
                        result += makeNumFromHalf(i);
                    }
                }
            }
        } catch (IOException ioException) {
            System.err.println("error: " + ioException);
        }
        
        System.err.println(result);
    }
}
