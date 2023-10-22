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
        if (token == null) {
            return false;
        }
        return token.equals('+') || token.equals('-') || token.equals('*') || token.equals('/');
    }


    /** @return a queue in order to put it in postfix */
    public ArrayDeque<Object> toPostfix(ArrayDeque<Object> inputQ) {
        System.out.println("**In toPostfix method**");
        // establish the stack to use within method
        System.out.println("inputQ = "+ inputQ);
        ArrayDeque<Object> stack = new ArrayDeque<>();
        System.out.println("stack =  "+ stack);
        // estabish output queue
        ArrayDeque<Object> outputQ = new ArrayDeque<>();
        System.out.println("outputQ =  "+ outputQ);
        // while there are tokens to be read
        while (inputQ.size() != 0) {
            // read a token
            Object token = inputQ.pop();
            System.out.println("New token =  "+ token);
            // If the token is a number, then add it to the output queue
            if (token instanceof Double) {
                System.out.println("Token is instance of double.");
                outputQ.addLast(token);
                System.out.println("Pushed "+ token+ " onto outputQ.");
                System.out.println("inputQ = "+ inputQ);
                System.out.println("stack =  "+ stack);
                System.out.println("outputQ =  "+ outputQ);
            }
            // If the token is an operator (the "queue operator"), then:
            if (isOperator(token)) {
                System.out.println("Token is an operator.");
                // while there is an operator token at the top of the stack (the "stack operator")
                // and the stack operator has greater precedence than the queue operator
                while (isOperator(stack.peekFirst()) && hasGreaterPrec(stack.peekFirst(), token)) {
                    System.out.println("There is an operator on the top of the stack and the stack operator has greater precedence.");
                    outputQ.addLast(stack.pop());
                    System.out.println("Pushed operator onto outputQ.");
                    System.out.println("inputQ = "+ inputQ);
                    System.out.println("stack =  "+ stack);
                    System.out.println("outputQ =  "+ outputQ);
                }
                // when no more high-precedence stack operators remain, finally push the queue operator onto the stack
                System.out.println("No more high-precedence stack operators remain. Push onto stack.");
                stack.push(token);
                System.out.println("inputQ = "+ inputQ);
                System.out.println("stack =  "+ stack);
                System.out.println("outputQ =  "+ outputQ);
            }
            // If the token is a left parenthesis, then push it onto the stack
            if (token.equals('(')) {
                System.out.println("Token = ( . Push onto stack.");
                stack.push(token);
                System.out.println("inputQ = "+ inputQ);
                System.out.println("stack =  "+ stack);
                System.out.println("outputQ =  "+ outputQ);
            }
            // If the token is a right paranthesis
            if (token.equals(')')) {
                System.out.println("Token = ) .");
                // Until the token at the top of the stack is a left parenthesis
                while (! stack.peekFirst().equals('(')) {
                    if (stack.size() == 0) {
                        throw new RuntimeException("Mismatched parantheses.");
                    }
                    System.out.println("in the while loop. stack = " +stack);
                    // pop operators off the stack onto the output queue
                    System.out.println("peekFirst is an operator. Push onto outputQ");
                    outputQ.addLast(stack.pop());
                    System.out.println("Popped off. New stack = "+ stack);
                    System.out.println("New outputQ = "+ outputQ);
                }
                System.out.println("Found left paranthesis. Now popping off of stack.");
                stack.pop();
                System.out.println("inputQ = "+ inputQ);
                System.out.println("stack =  "+ stack);
                System.out.println("outputQ =  "+ outputQ);
            }
        }
        System.out.println("Done with tokens");
        // When there are no more tokens to read
        // While there are still tokens in the stack
        while (stack.size() != 0) {
            System.out.println("There are still tokens in stack.");
            // If the token on the top of the stack is a parenthesis, then there are mismatched parentheses
            if (stack.getFirst().equals("(") || stack.getFirst().equals(")")) {
                throw new RuntimeException("Mismatched parantheses.");
            }
            if (isOperator(stack.getFirst())) {
                System.out.println("if isOperator -> push onto outputQ");
                outputQ.addLast(stack.pop());
                System.out.println("inputQ = "+ inputQ);
                System.out.println("stack =  "+ stack);
                System.out.println("outputQ =  "+ outputQ);
            }
        }
        System.out.println("Done. OutputQ = "+ outputQ);
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
