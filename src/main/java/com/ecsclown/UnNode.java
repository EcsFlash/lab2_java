package com.ecsclown;

import java.util.*;

/**
 * UnNode - node that keeps unary operator and operand node.
 */
public class UnNode implements INode {
    private final String operator;
    private final INode operand;

    public UnNode(String operator, INode operand) {
        this.operator = operator;
        this.operand = operand;
    }
    /**
     * This function calculates simple unary action with operand
     * @param variables  map with variables names and their values.
     * @return double result - result of action with operand
     * @throws  IllegalArgumentException if unexpected operator was in node
     */
    @Override
    public double calc(Map<String, Double> variables) {
        double val = operand.calc(variables);
        if ("-".equals(operator)) {
            return -val;
        }
        throw new IllegalArgumentException("Unexpected unary op");
    }
}
