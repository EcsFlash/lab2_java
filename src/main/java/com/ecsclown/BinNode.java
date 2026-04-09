package com.ecsclown;
import java.util.*;

/**
 * BinNode - node with two operands and one operator.
 * @see for example: 1 + 4. 1 will be NumNode, + will be BinNode, 4 will be NumNode
 */
public class BinNode implements INode{
    private final INode left;
    private final String operator;
    private final INode right;

    public BinNode(INode left, String operator, INode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    /**
     * This function calculates simple action between two operands
     * @param variables  map with variables names and their values.
     * @return double result - result of action between two operands
     * @throws ArithmeticException if there was a division by zero
     * @throws  IllegalArgumentException if unexpected operator was in node
     */
    @Override
    public double calc(Map<String, Double> variables) {
        double leftVal = left.calc(variables);
        double rightVal = right.calc(variables);
        return switch (operator) {
            case "+" -> leftVal + rightVal;
            case "-" -> leftVal - rightVal;
            case "*" -> leftVal * rightVal;
            case "/" -> {
                if (rightVal == 0.0) {
                    throw new ArithmeticException("Div by 0");
                }
                yield leftVal / rightVal;
            }
            case "^" -> Math.pow(leftVal, rightVal);
            default -> throw new IllegalArgumentException("Unexpected operator");
        };
    }
}
