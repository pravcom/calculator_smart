import org.example.Calculator;
import org.example.TokensSets;
import org.example.UnknownOperation;
import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {
    @Test
    public void simpleSum(){
        TokensSets token = new TokensSets();
        try {
            token.setInfixExp("2+3");
        } catch (UnknownOperation e) {
            throw new RuntimeException(e);
        }
        Calculator calculator = new Calculator(token);
        Assert.assertTrue(calculator.calc() == 5);
    }
    @Test
    public void simpleSubtract(){
        TokensSets token = new TokensSets();
        try {
            token.setInfixExp("2-3");
        } catch (UnknownOperation e) {
            throw new RuntimeException(e);
        }
        Calculator calculator = new Calculator(token);
        Assert.assertTrue(calculator.calc() == -1);
    }
    @Test
    public void simpleMultiply(){
        TokensSets token = new TokensSets();
        try {
            token.setInfixExp("2*3");
        } catch (UnknownOperation e) {
            throw new RuntimeException(e);
        }
        Calculator calculator = new Calculator(token);
        Assert.assertTrue(calculator.calc() == 6);
    }
    @Test
    public void simpleDivide(){
        TokensSets token = new TokensSets();
        try {
            token.setInfixExp("2/3");
        } catch (UnknownOperation e) {
            throw new RuntimeException(e);
        }
        Calculator calculator = new Calculator(token);
        double first = 2;
        double second = 3;
        double result = first/second;
        Assert.assertTrue(calculator.calc() == result);
    }
    @Test
    public void complicatedExpression(){
        TokensSets token = new TokensSets();
        try {
            token.setInfixExp("2 + (3 * (5 ^ (2 ^ 2)) / 3)");
        } catch (UnknownOperation e) {
            throw new RuntimeException(e);
        }
        Calculator calculator = new Calculator(token);
        double result = 627;
        Assert.assertTrue(calculator.calc() == result);
    }
    @Test
    public void testsUnknownOperation(){
        TokensSets token = new TokensSets();
        try {
            token.setInfixExp("2 + 345 + + + + 6");
        } catch (UnknownOperation e) {
            Assert.assertTrue(true);
        }
    }
}
