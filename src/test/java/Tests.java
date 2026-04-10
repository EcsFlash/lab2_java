import com.ecsclown.*;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @Test
    public void testNumberNode() {
        INode node = new NumNode(42.5);
        assertEquals(42.5, node.calc(new HashMap<>()), 0.0001);
    }

    @Test
    public void testVariableNode() {
        INode node = new VarNode("x");
        Map<String, Double> vars = new HashMap<>();
        vars.put("x", 10.0);
        assertEquals(10.0, node.calc(vars), 0.0001);
    }

    @Test
    public void testBinaryAddition() {
        INode left = new NumNode(5);
        INode right = new NumNode(3);
        INode node = new BinNode(left, "+", right);
        assertEquals(8.0, node.calc(new HashMap<>()), 0.0001);
    }

    @Test
    public void testBinaryDivisionByZero() {
        INode left = new NumNode(1);
        INode right = new NumNode(0);
        INode node = new BinNode(left, "/", right);
        assertThrows(ArithmeticException.class, () -> node.calc(new HashMap<>()));
    }

    @Test
    public void testUnaryMinus() {
        INode operand = new NumNode(5);
        INode node = new UnNode("-", operand);
        assertEquals(-5.0, node.calc(new HashMap<>()), 0.0001);
    }

    @Test
    public void testFunctionSin() {
        INode arg = new NumNode(0);
        INode node = new FuncNode("sin", arg);
        assertEquals(0.0, node.calc(new HashMap<>()), 0.0001);
    }

    @Test
    public void testParserSimpleAddition() {
        Parser parser = new Parser();
        INode root = parser.parse("2+3");
        assertEquals(5.0, root.calc(new HashMap<>()), 0.0001);
    }

    @Test
    public void testParserPrecedence() {
        Parser parser = new Parser();
        INode root = parser.parse("2+3*4");
        assertEquals(14.0, root.calc(new HashMap<>()), 0.0001);
    }

    @Test
    public void testParserParentheses() {
        Parser parser = new Parser();
        INode root = parser.parse("(2+3)*4");
        assertEquals(20.0, root.calc(new HashMap<>()), 0.0001);
    }

    @Test
    public void testParserPowerRightAssociative() {
        Parser parser = new Parser();
        INode root = parser.parse("2^3^2");
        assertEquals(512.0, root.calc(new HashMap<>()), 0.0001);
    }

    @Test
    public void testParserUnaryAndFunction() {
        Parser parser = new Parser();
        INode root = parser.parse("-sin(3.1416)");
        assertEquals(0.0, root.calc(new HashMap<>()), 0.1);
    }

    @Test
    public void testParserWithVariable() {
        Parser parser = new Parser();
        INode root = parser.parse("x*2 + y");
        Map<String, Double> vars = new HashMap<>();
        vars.put("x", 5.0);
        vars.put("y", 3.0);
        assertEquals(13.0, root.calc(vars), 0.0001);
    }

    @Test
    public void testParserComplexExpression() {
        Parser parser = new Parser();
        INode root = parser.parse("sin(x)^2 + sqrt(16) / 2");
        Map<String, Double> vars = new HashMap<>();
        vars.put("x", 0.0);
        assertEquals(2.0, root.calc(vars), 0.0001);
    }

    @Test
    public void testExtractVariables() {
        Set<String> vars = Parser.getVars("x + y * sin(z) + abc");
        System.out.println(vars);
        assertEquals(4, vars.size());
        assertTrue(vars.contains("x"));
        assertTrue(vars.contains("y"));
        assertTrue(vars.contains("abc"));
    }

    @Test
    public void testErrorUnbalancedParentheses() {
        Parser parser = new Parser();
        assertThrows(IllegalArgumentException.class, () -> parser.parse("(2+3"));
    }

    @Test
    public void testErrorUnknownFunction() {
        Parser parser = new Parser();
        assertThrows(IllegalArgumentException.class, () -> parser.parse("foo(5)"));
    }

    @Test
    public void testErrorExtraCharacters() {
        Parser parser = new Parser();
        assertThrows(IllegalArgumentException.class, () -> parser.parse("2+3a"));
    }

    @Test
    public void testErrorInvalidNumber() {
        Parser parser = new Parser();
        assertThrows(IllegalArgumentException.class, () -> parser.parse("2..3"));
    }
}