public class Infix {
    // method to change symbols to numbers to calculate precedence


    // method to calculate the precedence of two operators using previous method

    // method to take in a queue, use a stack, and return a queue in order to put it in postfix
//     * While there are tokens to be read:
//   * Read a token.
//   * If the token is a number, then add it to the output queue.
//   * If the token is an operator (the "queue operator") then:
//     * while there is an operator token at the top of the stack (the "stack operator"), and the stack operator has greater precedence than the queue operator,
//       * pop the stack operator off the stack and add it to the output queue;
//     * when no more high-precedence stack operators remain, finally push the queue operator onto the stack.
//   * If the token is a left parenthesis, then push it onto the stack.
//   * If the token is a right parenthesis:
//     * Until the token at the top of the stack is a left parenthesis, pop operators off the stack onto the output queue.
//     * Pop the left parenthesis from the stack, but not onto the output queue.
//     * If the stack runs out without finding a left parenthesis, then there are mismatched parentheses.
// * When there are no more tokens to read:
//   * While there are still tokens in the stack:
//     * If the token on the top of the stack is a parenthesis, then there are mismatched parentheses.
//     * If it is an operator, pop it onto the output queue.
// * Exit.
}
