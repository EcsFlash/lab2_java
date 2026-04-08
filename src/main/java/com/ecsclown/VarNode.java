package com.ecsclown;

import java.util.*;

public class VarNode implements INode{
    private final String name;

    public VarNode(String name) {
        this.name = name;
    }

    @Override
    public double calc(Map<String, Double> variables) {

        return variables.getOrDefault(name, Double.NEGATIVE_INFINITY);
    }
}
