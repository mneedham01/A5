import java.util.*;

public class Infix {
    // method to change operators to numbers to calculate precedence
    /** @returns method to  */
    public int opToNum (Object obj) {
        if (obj.equals('+') || obj.equals('-')) {
            return 0;
        }
        if (obj.equals('*') || obj.equals('/')) {
            return 1;
        }
        throw new RuntimeException("Invalid operator.");
    }

    /** @returns if stackOp has greater precedence than the queueOp */
    public boolean hasGreaterPrec (Object stackOp, Object queueOp) {
        int stackNum = opToNum(stackOp);
        int queueNum = opToNum(queueOp);
        return (stackNum > queueNum) ? true : false;
    }

    /** @return if Object is an operator */
    public boolean isOperator(Object token) {
        return token.equals('+') || token.equals('-') || token.equals('*') || token.equals('/');
    }


    /** @return a queue in order to put it in postfix */
    public ArrayDeque<Object> toPostfix(ArrayDeque<Object> inputQ) {
        // establish the stack to use within method
        ArrayDeque<Object> stack = new ArrayDeque<>();
        // estabish output queue
        ArrayDeque<Object> outputQ = new ArrayDeque<>();
        // while there are tokens to be read
        while (inputQ.size() != 0) {
            // read a token
            Object token = inputQ.pop();
            // If the token is a number, then add it to the output queue
            if (token instanceof Double) {
                outputQ.push(token);
            }
            // If the token is an operator (the "queue operator"), then:
            if (isOperator(token)) {
                // while there is an operator token at the top of the stack (the "stack operator")
                // and the stack operator has greater precedence than the queue operator
                while (stack.size() > 0 && isOperator(stack.getFirst()) && hasGreaterPrec(stack.pop(), token)) {
                    outputQ.push(stack.pop());
                }
                // when no more high-precedence stack operators remain, finally push the queue operator onto the stack
                stack.push(token);
            }
            // If the token is a left parenthesis, then push it onto the stack
            if (token.equals("(")) {
                stack.push(token);
            }
            // If the token is a right paranthesis
            if (token.equals(")")) {
                // Until the token at the top of the stack is a left parenthesis
                // pop operators off the stack onto the output queue
                while (stack.pop().equals("(")) {
                    // If the stack runs out without finding a left parenthesis, then there are mismatched parentheses
                    if (stack.size() == 0) {
                        throw new RuntimeException("Mismatched parantheses.");
                    }
                    outputQ.push(stack.pop());
                }
            }
        }
        // When there are no more tokens to read
        // While there are still tokens in the stack
        while (stack.size() != 0) {
            // If the token on the top of the stack is a parenthesis, then there are mismatched parentheses
            if (stack.getFirst().equals("(") || stack.getFirst().equals(")")) {
                throw new RuntimeException("Mismatched parantheses.");
            }
            if (isOperator(stack.getFirst())) {
                outputQ.push(stack.pop());
            }
        }
        return outputQ;
    }

    public static void main(String[] args) {
        // read in input and turn into queue
        ArrayDeque <Object> queue = Tokenizer.readTokens(args[0]);
        System.out.println("Queue= " + queue);
        // turn into postfix
        Infix infix = new Infix();
        ArrayDeque <Object> postfix = infix.toPostfix(queue);
        System.out.println("Postfix version= " + postfix);
        // solve with postfix
        Postfix post = new Postfix();
        System.out.println("Answer = " + post.compute(queue));
    }
}