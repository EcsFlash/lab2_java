package com.ecsclown;

import java.util.*;

/**
 * Parser - class for parse string expression to AST.
 */
public class Parser {
    private String expr;
    private int pos;
    private static final Set<String> FUNCTIONS = Set.of(
            "sin", "cos", "tan", "sqrt", "log", "exp", "abs"
    );

    /**
     * This function returns true or false to check supported function in expression or not
     * @param name - name of func to check
     * @return boolean - true if func is supported and false otherwise
     */
    private static boolean isSupportedFunc(String name) {
        return FUNCTIONS.contains(name.toLowerCase());
    }

    /**
     * This function extracts variables from expression and gives a set of their names
     * @param input - expression
     * @return set of variables names
     */
    public static Set<String> getVars(String input) {
        String cleaned = input.replaceAll("\\s+", "");
        Set<String> vars = new HashSet<>();
        int i = 0;
        while (i < cleaned.length()) {
            if (Character.isLetter(cleaned.charAt(i))) {
                int start = i;
                while (i < cleaned.length() && Character.isLetter(cleaned.charAt(i))) {
                    i++;
                }
                String id = cleaned.substring(start, i);
                boolean isFunc = i < cleaned.length() && cleaned.charAt(i) == '(' && isSupportedFunc(id);
                if (!isFunc) {
                    vars.add(id);
                }
            } else {
                i++;
            }
        }
        return vars;
    }

    /**
     * This function prepare and parse expression into AST
     * @param input - expression
     * @return AST - tree that represents expression
     */
    public INode parse(String input) {
        this.expr = input.replaceAll("\\s+", "");
        this.pos = 0;
        INode node = parseExpression();
        if (pos < expr.length()) {
            throw new IllegalArgumentException("Unwanted chars at end of expression");
        }
        return node;
    }

    /**
    * This function parses expression with + and - operators
    * @return INode - node of parsed expression part
    */
    private INode parseExpression() {
        INode node = parseTerm();
        while (pos < expr.length()) {
            char c = expr.charAt(pos);
            if (c == '+' || c == '-') {
                pos++;
                INode right = parseTerm();
                node = new BinNode(node, String.valueOf(c), right);
            } else {
                break;
            }
        }
        return node;
    }

    /**
    * This function parses term with * and / operators
    * @return INode - node of parsed term
    */
    private INode parseTerm() {
        INode node = parsePower();
        while (pos < expr.length()) {
            char c = expr.charAt(pos);
            if (c == '*' || c == '/') {
                pos++;
                INode right = parsePower();
                node = new BinNode(node, String.valueOf(c), right);
            } else {
                break;
            }
        }
        return node;
    }

    /**
    * This function parses power operator ^
    * @return INode - node of parsed power expression
    */
    private INode parsePower() {
        INode node = parsePrimary();
        if (pos < expr.length() && expr.charAt(pos) == '^') {
            pos++;
            INode right = parsePower();
            node = new BinNode(node, "^", right);
        }
        return node;
    }

    /**
    * This function parses primary elements: number, variable, function, parentheses or unary +/-
    * @return INode - node of primary element
    * @throws IllegalArgumentException - if expression ended unexpectedly
    * @throws IllegalArgumentException - if closing bracket wasn't found
    * @throws IllegalArgumentException - if unexpected char was found
    */
    private INode parsePrimary() {
        if (pos >= expr.length()) {
            throw new IllegalArgumentException("Unexpected end of expression");
        }
        char c = expr.charAt(pos);
        if (Character.isDigit(c) || c == '.') {
            return parseNumber();
        } else if (Character.isLetter(c)) {
            return parseIdentifier();
        } else if (c == '(') {
            pos++;
            INode node = parseExpression();
            if (pos >= expr.length() || expr.charAt(pos) != ')') {
                throw new IllegalArgumentException("Expected ), but there is an unwanted char: " + c + "pos: " + pos);
            }
            pos++;
            return node;
        } else if (c == '-') {
            pos++;
            INode operand = parsePrimary();
            return new UnNode("-", operand);
        } else if (c == '+') {
            pos++;
            return parsePrimary();
        }
        throw new IllegalArgumentException("Unexpected char " + c);
    }
    /**
    * This function parses number (integer or decimal) from current position
    * @return NumNode - node with parsed number value
    */
    private INode parseNumber() {
        int start = pos;
        boolean hasDot = false;
        while (pos < expr.length()) {
            char c = expr.charAt(pos);
            if (Character.isDigit(c)) {
                pos++;
            } else if (c == '.' && !hasDot) {
                hasDot = true;
                pos++;
            } else {
                break;
            }
        }
        String numStr = expr.substring(start, pos);
        try {
            double val = Double.parseDouble(numStr);
            return new NumNode(val);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Wrong number format " + numStr);
        }
    }

    /**
     * This function parses identifier: variable or function call
     * @return VarNode or FuncNode depending on what was found
     */
    private INode parseIdentifier() {
        int start = pos;
        while (pos < expr.length() && Character.isLetter(expr.charAt(pos))) {
            pos++;
        }
        String id = expr.substring(start, pos);
        if (pos < expr.length() && expr.charAt(pos) == '(') {
            if (!isSupportedFunc(id)) {
                throw new IllegalArgumentException("Unexpected func: " + id);
            }
            pos++;
            INode arg = parseExpression();
            if (pos >= expr.length() || expr.charAt(pos) != ')') {
                throw new IllegalArgumentException("Expected ) after function arg");
            }
            pos++;
            return new FuncNode(id.toLowerCase(), arg);
        } else {
            return new VarNode(id);
        }
    }
}