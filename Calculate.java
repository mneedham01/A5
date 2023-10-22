import java.util.*;

public class Calculate {
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
        if (token == null) {
            return false;
        }
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
                outputQ.addLast(token);
            }
            // If the token is an operator (the "queue operator"), then:
            if (isOperator(token)) {
                // while there is an operator token at the top of the stack (the "stack operator")
                // and the stack operator has greater precedence than the queue operator
                while (isOperator(stack.peekFirst()) && hasGreaterPrec(stack.peekFirst(), token)) {
                    outputQ.addLast(stack.pop());
                }
                // when no more high-precedence stack operators remain, finally push the queue operator onto the stack
                stack.push(token);
            }
            // If the token is a left parenthesis, then push it onto the stack
            if (token.equals('(')) {
                stack.push(token);
            }
            // If the token is a right paranthesis
            if (token.equals(')')) {
                // Until the token at the top of the stack is a left parenthesis
                while (! stack.peekFirst().equals('(')) {
                    if (stack.size() == 0) {
                        throw new RuntimeException("Mismatched parantheses.");
                    }
                    // pop operators off the stack onto the output queue
                    outputQ.addLast(stack.pop());
                }
                stack.pop();
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
                outputQ.addLast(stack.pop());
            }
        }
        return outputQ;
    }

    public static void main(String[] args) {
        // read in input and turn into queue
        ArrayDeque <Object> queue = Tokenizer.readTokens(args[0]);
        // turn into postfix
        Calculate infix = new Calculate();
        ArrayDeque <Object> postfix = infix.toPostfix(queue);
        // solve with postfix
        Postfix post = new Postfix();
        System.out.println("Answer = " + post.compute(postfix));
    }
}
