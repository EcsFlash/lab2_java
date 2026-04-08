package com.ecsclown;

import java.util.*;

public class NumNode implements INode{
    private final double value;
    public NumNode(double val) {
        this.value = val;
    }
    @Override
    public double calc(Map<String, Double> variables) {
        return value;
    }
}
