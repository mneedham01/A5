import java.util.*;

public class Postfix {

    public Double compute(ArrayDeque<Object> queue) {
        ArrayDeque<Double> stack = new ArrayDeque<Double>();
        for (Object obj : queue) {
            if (obj instanceof Double) {
                Double d = (Double)obj;
                stack.push(d);
            }
            if (obj instanceof Character) {
                if (stack.size() < 2) {
                    throw new RuntimeException("Not enough elements to process operator.");
                }
                Double num1 = stack.pop();
                Double num2 = stack.pop();
                if (obj.equals('+')) {
                    stack.push(num1 + num2);
                }
                if (obj.equals('-')) {
                    stack.push(num1 - num2);
                }
                if (obj.equals('*')) {
                    stack.push(num1 * num2);
                }
                if (obj.equals('/')) {
                    stack.push(num1 / num2);
                }
            }
        }
        if (stack.size() != 1) {
            throw new RuntimeException("Too many items on stack at the end.");
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        ArrayDeque <Object> queue = Tokenizer.readTokens(args[0]);
        Postfix fix = new Postfix();
        System.out.println("Answer = " + fix.compute(queue));
    }
}
