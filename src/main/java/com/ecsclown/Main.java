package com.ecsclown;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input expression: ");
        String input = scanner.nextLine();
        Set<String> vars = Parser.getVars(input);
        Map<String, Double> varMap = new HashMap<>();
        for (String v : vars) {
            System.out.print("Value of var " + v + ": ");
            double value = scanner.nextDouble();
            varMap.put(v, value);
        }
        try {
            Parser parser = new Parser();
            INode root = parser.parse(input);
            double result = root.calc(varMap);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Error in expression: " + e.getMessage());
        }
    }
}