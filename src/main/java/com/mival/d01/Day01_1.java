package com.mival.d01;

import java.io.IOException;
import java.util.List;

import com.mival.utils.Util;


public class Day01_1 {
    public static void main(String[] args) {
        try {
            String fileName = "d01_1";
            
            if (args.length > 0 && args[0].equals("test")) {
                fileName = fileName + "_test";
            }
            List<String> input = Util.readInput(fileName);
           int position = 50;
           int result = 0;
            for (int i = 0; i < input.size(); i++) {
                String line = input.get(i);
                position = (position + Integer.parseInt(line.replace("L", "-").replace("R", ""))) % 100;
            
                if (position < 0 ) {
                    position += 100;
                }

                if (position == 0) {
                    result += 1;
                }
            }
            System.out.println(result);
        } catch(IOException noFile) {
            System.out.println(noFile);
        }
        
    }
}
