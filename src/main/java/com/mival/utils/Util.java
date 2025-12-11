package com.mival.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<String> readInput(String fileName) throws IOException {
        String inputFile = "input/" + fileName + ".txt";

        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        List<String> out = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
            out.add(line);
        }
        br.close();

        return out;
    }
}
