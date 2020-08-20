package com.myleetcode.stack.basic_calculator_iii;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int calculate(String s) {
        return caculateByStack(s);
    }

    /*
    https://leetcode.com/problems/basic-calculator-iii/discuss/202979/A-generic-solution-for-Basic-Calculator-I-II-III/322675

    Here we assume that all the expressions are valid.
    We use two stacks to store our operators and oprands that haven't been processed.
    When we get a new number, we just push it into numStack and wait for an operator to process it. Each operator has a priority and when we get a new operator, we can exhaust all the oprators on the top of the stack that has higher or equal priority to the current operator.
    By the way, only ')' can exhaust '(', so when we peek '(' on the top of opStack, we will stop the exhausting.
    At last, we clear the operator stack to get the final answer.

    For negative numbers, we change them to 0 minus positive numbers to use this solution.

    This could solve:
        224. Basic Calculator
        227. Basic Calculator II
        772. Basic Calculator III
    */
    private int caculateByStack(String str) {
        // Special case.
        if (str == null || str.length() == 0) {
            return 0;
        }

        // Two stacks for number and operator
        Deque<Integer> numStack = new ArrayDeque<>();
        Deque<Character> operatorStack = new ArrayDeque<>();

        // Operator priority map <Operator, Priority>
        Map<Character, Integer> operatorPriorityMap = new HashMap<>();
        operatorPriorityMap.put('+', 0);
        operatorPriorityMap.put('-', 0);
        operatorPriorityMap.put('*', 1);
        operatorPriorityMap.put('/', 1);
        operatorPriorityMap.put('(', 2);
        operatorPriorityMap.put(')', -1);


        // Pre-process the str to make it have no negative by change negative to 0 minus positive.
        StringBuilder sb = new StringBuilder();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            // -1*2/3*(-1+2)-1
            if ((i == 0 && str.charAt(i) == '-') || (i > 0 && str.charAt(i - 1) == '(' && str.charAt(i) == '-')) {
                sb.append(0);
            }
            sb.append(str.charAt(i));
        }

        str = sb.toString();
        len = str.length();
        int i = 0;
        while (i < len) {
            char curCh = str.charAt(i);

            if (curCh == '(') { // (
                operatorStack.push(curCh);

                i++;
            } else if (Character.isDigit(curCh)) { // number
                int num = 0;
                while (i < len && Character.isDigit(str.charAt(i))) {
                    curCh = str.charAt(i);
                    num = num * 10 + (curCh - '0');

                    i++;
                }
                numStack.push(num);
            } else if (curCh == '+' || curCh == '-' || curCh == '*' || curCh == '/') { // + - * /
                // while operatorStack is not empty, we calculate if the operator on top of operatorStack has higher or equal priority than current operator.
                // for example, if current operator is +, we should get the calculation done if top of operatorStack is * / + -; if current operator is *, we should get the calculation done if top of operatorStack is * /.
                // By this way, we are keeping calculate the operator with higher priority first which is necessary.
                // But only ) could exhaust (, so when met ( we stop this operation.
                while (!operatorStack.isEmpty() && (operatorStack.peek() != '(' && operatorPriorityMap.get(operatorStack.peek()) >= operatorPriorityMap.get(curCh))) {
                    int num2 = numStack.pop();
                    int num1 = numStack.pop();
                    int ans = getRes(num1, num2, operatorStack.pop());

                    numStack.push(ans);
                }
                operatorStack.push(curCh); // Since all operator with higher or equal priority than curCh has been exhausted, we could push curCh in safely, wait for other incomming operator to exhaust it or clear operation at last.

                i++;
            } else if (curCh == ')') { // )
                // Only ) could exhaust (, so when met ( we stop this operation.
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    int num1 = numStack.pop();
                    int num2 = numStack.pop();
                    numStack.push(getRes(num2, num1, operatorStack.pop()));
                }
                operatorStack.pop(); // Pop out (.

                i++;
            } else { // other chars
                i++;
            }
        }

        // Clear all operatos.
        if (!operatorStack.isEmpty()) {
            while (!operatorStack.isEmpty()) {
                int num2 = numStack.pop();
                int num1 = numStack.pop();
                int ans = getRes(num1, num2, operatorStack.pop());

                numStack.push(ans);
            }
        }

        return numStack.pop();

    }

    private int getRes(int i, int j, char op) {
        switch (op) {
            case '+':
                return i + j;
            case '-':
                return i - j;
            case '*':
                return i * j;
            case '/':
                return i / j;
        }

        return Integer.MIN_VALUE;
    }
}
