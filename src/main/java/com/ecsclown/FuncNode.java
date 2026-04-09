package com.ecsclown;

import java.util.*;

/**
 * FuncNode - node that contains function and argument
 */
public class FuncNode implements INode{
    private final String funcName;
    private final INode arg;

    public FuncNode(String funcName, INode arg) {
        this.funcName = funcName;
        this.arg = arg;
    }

    /** This function calculates result of function with given argument
    * @param variables  map with variables names and their values.
    * @return double result - result of math function with given argument
    * @throws  IllegalStateException if unexpected function was in node
    */
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
