package com.mival.d07;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mival.utils.Util;

record Position ( int row, int col  ) {}

class Node {
        long nRoutes;
        Position position;
        Node parent;
        int visitedChildren;

        public Node(Position position, Node parent) {
            this.nRoutes = 0;
            this.position = position;
            this.parent = parent;
            this.visitedChildren = 0;
        }

        // marks child node as visited and returns:
        // a) self if only one child has been visited
        // b) parent (retursively...)
        // c) self if all done and this is root node
        public Node setVisitedChild() {
            this.visitedChildren++;
            if (this.visitedChildren == 2 && this.parent != null) {
                return this.parent.setVisitedChild();
            } else {
                return this;
            }
        }
        // adds n routes to node and propagates upwards
        public void addRoutes(long n) {
            this.nRoutes += n;
            if (this.parent != null) {
                this.parent.addRoutes(n);
            }
        }

    }

public class Day07_2 {
    public static void main(String[] args) {
        String fileName = "d07_1" + (args.length != 0 && args[0].equals("test") ? "_test" : "");
        List<String> input = new ArrayList<>();
        try {
            input = Util.readInput(fileName);
        } catch (IOException ioException) {
            System.err.print(ioException);
        }
        if (input.size() == 0) throw new Error("Input file is empty");
        // position of starting point
        int row = 0;
        Integer col = input.get(row).indexOf('S', 0);
        Map<Position, Node> visitedNodes = new HashMap<>();
        Node currentParent = null;
        List<Position> stack = new ArrayList<>();
        
        stack.add(new Position(row, col));

        while (!stack.isEmpty()) {
            Position position = stack.removeLast();
            col = position.col();
            row = position.row();
            if (row == input.size()) {
                currentParent.addRoutes(1);
                currentParent = currentParent.setVisitedChild();
                continue;
            }
            String line = input.get(row);
            if (line.charAt(col) == '^') {
                Node previouslyVisited = visitedNodes.get(position);
                if (previouslyVisited != null) {
                    currentParent.addRoutes(previouslyVisited.nRoutes);
                    currentParent = currentParent.setVisitedChild();
                    continue;
                }
                Node thisNode = new Node(position, currentParent);
                visitedNodes.put(position, thisNode);
                currentParent = thisNode;
                if (col < line.length() - 1) {
                    stack.add(new Position(row + 1, col + 1));
                }
                if (col > 0) {
                    stack.add(new Position(row + 1, col - 1));
                }
            } else {
                stack.add(new Position(row + 1, col));
            }
        }
        System.out.println(currentParent.nRoutes);
    }
}
