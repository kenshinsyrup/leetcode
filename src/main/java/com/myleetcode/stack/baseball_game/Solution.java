package com.myleetcode.stack.baseball_game;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    public int calPoints(String[] ops) {
        return calPointsByStack(ops);
    }

    private int calPointsByStack(String[] ops) {
        if (ops == null || ops.length == 0) {
            return 0;
        }

        int sum = 0;
        int len = ops.length;
        Deque<String> opStack = new ArrayDeque<>();
        for (int i = 0; i < len; i++) {
            String op = ops[i];

            if (op.equals("C")) {
                if (!opStack.isEmpty()) {
                    int num = Integer.parseInt(opStack.pop());
                    sum -= num;
                }
            } else if (op.equals("D")) {
                if (!opStack.isEmpty()) {
                    int num = Integer.parseInt(opStack.peek()) * 2;
                    sum += num;

                    opStack.push(Integer.toString(num));
                }
            } else if (op.equals("+")) {
                if (!opStack.isEmpty() && opStack.size() >= 2) {
                    int num1 = Integer.parseInt(opStack.pop());
                    int num2 = Integer.parseInt(opStack.pop());
                    sum += num1 + num2;

                    opStack.push(Integer.toString(num2));
                    opStack.push(Integer.toString(num1));
                    opStack.push(Integer.toString(num1 + num2));
                }
            } else {
                int num = Integer.parseInt(op);
                sum += num;

                opStack.push(Integer.toString(num));
            }

        }

        return sum;
    }
}
