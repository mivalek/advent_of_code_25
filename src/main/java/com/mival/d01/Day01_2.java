package com.mival.d01;

import java.io.IOException;
import java.util.List;

import com.mival.utils.Util;


public class Day01_2 {
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
                int delta = Integer.parseInt(
                    line.replace("L", "-").replace("R", "")
                );

                int direction = delta < 0 ? -1 : 1;

                for (int j = 0; j < Math.abs(delta); j++) {
                    position += direction;
                    if (position < 0) {
                        position = 99;
                        if (j != 0) result += 1;
                    } else if (position > 99) {
                        position = 0;
                        result += 1;
                    }
                }
                if (position == 0 && delta < 0) {
                    result += 1;
                }
            }
            System.out.println(result);
        } catch(IOException noFile) {
            System.out.println(noFile);
        }
        
    }
}
