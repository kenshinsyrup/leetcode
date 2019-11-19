package com.myleetcode.stack.basic_calculator_ii;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Solution {

  public int calculate(String s) {
    // return caculateByStack(s);
    return calculateByTwoStacks(s);
  }

  // https://leetcode.com/problems/basic-calculator-iii/
  private int calculateByTwoStacks(String str) {
    if (str == null || str.length() == 0) {
      return 0;
    }

    // two stacks for number and operator
    Deque<Integer> numStack = new ArrayDeque<>();
    Deque<Character> operatorStack = new ArrayDeque<>();

    // operator priority
    Map<Character, Integer> operatorPriorityMap = new HashMap<>();
    operatorPriorityMap.put('+', 0);
    operatorPriorityMap.put('-', 0);
    operatorPriorityMap.put('*', 1);
    operatorPriorityMap.put('/', 1);

    int len = str.length();
    int i = 0;
    while (i < len) {
      char curCh = str.charAt(i);

      if (Character.isDigit(curCh)) {
        int num = 0;
        while (i < len && Character.isDigit(str.charAt(i))) {
          curCh = str.charAt(i);
          num = num * 10 + (curCh - '0');

          i++;
        }
        numStack.push(num);
      } else if (curCh == '+' || curCh == '-' || curCh == '*' || curCh == '/') {
        while (!operatorStack.isEmpty()
            && operatorPriorityMap.get(operatorStack.peek()) >= operatorPriorityMap.get(curCh)) {
          int num2 = numStack.pop();
          int num1 = numStack.pop();
          int ans = getRes(num1, num2, operatorStack.pop());

          numStack.push(ans);
        }
        operatorStack.push(curCh);
        i++;
      } else {
        i++;
      }
    }

    // clear operator
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

  // here is a same idea solution but much clearer: https://leetcode.com/problems/basic-calculator-ii/discuss/63003/Share-my-java-solution/248657

  // TC: O(N)
  // SC: O(N)
  // intuition: * and / has higher priority than + and -
  // traverse the String, if cur char is
  // 1 space, next
  // 2 +, variable sign set to 1
  // 3 -, variable sign set to -1
  // 4 num, build num chars to a whole num, then multiply wit sign, push to stack
  // 5 *, pop the top of stack out and multiply next num with it, then push the product to stack
  // 6 /, pop the top of stack out and use it divide the next num, then push the quotient to stack
  // at last, pop out all nums in stack and plus them
  private int caculateByStack(String str) {
    if (str == null || str.length() == 0) {
      return 0;
    }

    Deque<Integer> numStack = new ArrayDeque<>();
    int len = str.length();
    int curIdx = 0;
    int sign = 1; // remember the + and - leading num

    // traverse the String
    while (curIdx < len) {
      char curChar = str.charAt(curIdx);

      if (curChar == ' ') {
        curIdx++;
        continue;
      }

      if (curChar == '+') {
        sign = 1;
        curIdx++;
        continue;
      }

      if (curChar == '-') {
        sign = -1;
        curIdx++;
        continue;
      }

      if (curChar <= '9' && curChar >= '0') {
        int num = 0;
        int i = curIdx;

        // build the whole num
        while (i < len && str.charAt(i) >= '0' && str.charAt(i) <= '9') {
          num = num * 10 + (str.charAt(i) - '0');
          i++;
        }
        // remember to move the curIdx
        curIdx = i;

        // real num
        num *= sign;

        // push the num to stack
        numStack.push(num);

        continue;
      }

      if (curChar == '*' || curChar == '/') {
        // find the num following:
        int num = 0;
        int i = curIdx + 1;

        // skip space
        while (str.charAt(i) == ' ') {
          i++;
        }

        // build the whole num
        while (i < len && str.charAt(i) >= '0' && str.charAt(i) <= '9') {
          num = num * 10 + (str.charAt(i) - '0'); // !!! how to get int by char
          i++;
        }
        // remember to move the curIdx
        curIdx = i;

        // since the problem said, the num are all positive, and string must be valid, so the num now is the real num, we caculate it with the num previous the * or /, and push back to stack
        int preNum = numStack.pop();
        if (curChar == '*') {
          numStack.push(preNum * num);
        } else {
          numStack.push(preNum / num);
        }
      }
    }

    // now all num, product, quotient are all in the stack, for the -, we have processed it with its following num as negative num
    int sum = 0;
    while (!numStack.isEmpty()) {
      sum += numStack.pop();
    }

    return sum;

  }
}
