package com.mival.d11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mival.utils.Util;


public class Day11_2 {
    public static void main(String[] args) {
        String fileName = "d11_1" + (args.length != 0 && args[0].equals("test") ? "_test" : "");
        long result = 0;
        List<String> input = new ArrayList<>();
        try {
            input = Util.readInput(fileName);
        } catch (IOException ioException) {
            System.err.print(ioException);
        }


    }
}
