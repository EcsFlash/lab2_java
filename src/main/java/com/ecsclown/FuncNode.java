package com.ecsclown;

import java.util.*;

public class FuncNode implements INode{
    private final String funcName;
    private final INode arg;

    public FuncNode(String funcName, INode arg) {
        this.funcName = funcName;
        this.arg = arg;
    }

    @Override
    public double calc(Map<String, Double> variables) {
        double argVal = arg.calc(variables);
        return switch (funcName){
            case "sin" -> Math.sin(argVal);
            case "cos" -> Math.cos(argVal);
            case "tan" -> Math.tan(argVal);
            case "sqrt" -> Math.sqrt(argVal);
            case "log" -> Math.log(argVal);
            case "exp" -> Math.exp(argVal);
            case "abs" -> Math.abs(argVal);
            default -> throw new IllegalStateException("Unexpected value: " + funcName);
        };
    }
}
