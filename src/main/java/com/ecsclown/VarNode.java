package com.ecsclown;

import java.util.*;

/**
 * VarNode - node that keeps variable of expression
 */
public class VarNode implements INode{
    private final String name;

    public VarNode(String name) {
        this.name = name;
    }
    /**
     * This function returns value of variable in expression
     * @param variables  map with variables names and their values.
     * @return double result - value of variable, or -infinity if variable not found
     */
    @Override
    public double calc(Map<String, Double> variables) {
        return variables.getOrDefault(name, Double.NEGATIVE_INFINITY);
    }
}
