package com.mival.d10;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ojalgo.optimisation.Expression;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Optimisation;
import org.ojalgo.optimisation.Variable;

import com.mival.utils.Util;

public class Day10_2 {
    public static void main(String[] args) {
        String fileName = "d10_1" + (args.length != 0 && args[0].equals("test") ? "_test" : "");
        List<String> input = new ArrayList<>();
        try {
            input = Util.readInput(fileName);
        } catch (IOException ioException) {
            System.err.print(ioException);
        }

        long result = 0;
        for (String line: input) {
            String[] elements = line.split(" ");
            String[] joltageStr = elements[elements.length - 1]
                .substring(1, elements[elements.length - 1].length() - 1)
                .split(",");
            int[] joltage = Arrays
                    .stream(joltageStr)
                    .mapToInt(Integer::parseInt)
                    .toArray();

            Integer[][] buttons = new Integer[elements.length - 2][];
            for (int i = 1; i < elements.length - 1; i++) {
                String[] bttnsString = elements[i]
                    .substring(1, elements[i].length() - 1)
                    .split(",");
                buttons[i - 1] = Arrays.stream(bttnsString)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .toArray(Integer[]::new);
            }

            ExpressionsBasedModel model = new ExpressionsBasedModel();
            List<Variable> vars = new ArrayList<>();
            List<Expression> equations = new ArrayList<>();
          
            for (int b = 0; b < buttons.length; b++) 
                vars.add(
                    model.addVariable("b_" + b)
                        .weight(1)
                        .lower(0) // non-negative constraint
                        .integer(true)
                    );
            for (int i = 0; i < joltage.length; i++) {
                // add equation
                equations.add(
                    model.addExpression()
                        .upper(joltage[i])
                        .lower(joltage[i])
                    );
                for (int b = 0; b < buttons.length; b++) {
                       equations
                        .get(i)
                        .set(
                            vars.get(b),
                            Arrays.asList(buttons[b]).contains(i) ? 1 : 0
                        ); 
                    
                }
            }
            Optimisation.Result res = model.minimise();
            result += Math.round(res.getValue());
        }
        System.out.println(result);
    }
}
