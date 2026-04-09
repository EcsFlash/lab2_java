package com.ecsclown;

import java.util.*;

/**
 * NumNode - node that contains concrete number of type double
 */
public class NumNode implements INode{
    private final double value;
    public NumNode(double val) {
        this.value = val;
    }

    /** This function returns value of node
     * @param variables  map with variables names and their values.
     * @return value - number
     */
    @Override
    public double calc(Map<String, Double> variables) {
        return value;
    }
}
