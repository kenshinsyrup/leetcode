package com.myleetcode.stack.basic_calculator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Solution {

  public int calculate(String s) {
    // return caculateByStack(s); // wrong
    // return caculateByStackII(s);

    // return calculateByGroup(s);

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
    operatorPriorityMap.put('(', 2);
    operatorPriorityMap.put(')', -1);

    int len = str.length();
    int i = 0;
    while (i < len) {
      char curCh = str.charAt(i);

      if (curCh == '(') {
        operatorStack.push(curCh);
        i++;
      } else if (Character.isDigit(curCh)) {
        int num = 0;
        while (i < len && Character.isDigit(str.charAt(i))) {
          curCh = str.charAt(i);
          num = num * 10 + (curCh - '0');

          i++;
        }
        numStack.push(num);
      } else if (curCh == '+' || curCh == '-') {
        // calculate the operators with higher priority than curCh before push it in
        while (!operatorStack.isEmpty() && operatorStack.peek() != '('
            && operatorPriorityMap.get(operatorStack.peek()) >= operatorPriorityMap.get(curCh)) {
          int num2 = numStack.pop();
          int num1 = numStack.pop();
          int ans = getRes(num1, num2, operatorStack.pop());

          numStack.push(ans);
        }
        operatorStack.push(curCh);
        i++;
      } else if (curCh == ')') {
        // calculate the () pair
        while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
          int num2 = numStack.pop();
          int num1 = numStack.pop();
          int ans = getRes(num1, num2, operatorStack.pop());

          numStack.push(ans);
        }
        operatorStack.pop();
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
    }

    return Integer.MIN_VALUE;
  }

  private int calculateByGroup(String str) {
    if (str == null || str.length() == 0) {
      return 0;
    }

    Deque<Integer> calStack = new ArrayDeque<>();

    // curRet is the ret of a context, ie a group of (). At last, it will be the final ret of the total expression since we could see the whole expression as a context.
    int curRet = 0;
    // sign before a num or context
    int sign = 1;

    int len = str.length();
    int i = 0;
    while (i < len) {
      char ch = str.charAt(i);

      if (ch == ' ') { // skip space
        i++;
        continue;
      } else if (ch == '-') { // meet a -, then set sign to -1
        sign = -1;
        i++;
        continue;
      } else if (ch == '+') { // meet a +, then set the sign to 1
        sign = 1;
        i++;
        continue;
      } else if (ch
          == '(') { // meet a (, means we need to start calculate a new context, so we push current curRet to stack and push the sign to stack, then we reset the curRet and sign
        calStack.push(curRet);
        calStack.push(sign);

        curRet = 0;
        sign = 1;
        i++;
        continue;
      } else if (ch
          == ')') { // meet a ), means we have just done the calculation of a context, then we should combine the previous sign with it and then add this context result with previous result, this is the new curRet. eg, 1-(2+3), previous ret is 1 and previous is -1 and they are in stack, after we get the result of (2+3), we combine the 5 with sign -1 to get -5 and add it with 1 and get -4.
        curRet *= calStack.pop();
        curRet += calStack.pop();

        i++;
        continue;
      } else if (ch >= '0' && ch
          <= '9') { // if meet num, we get the num and combine with sign in front of it, then add with curRet.
        int j = i;
        while (j < len && str.charAt(j) <= '9' && str.charAt(j) >= '0') {
          j++;
        }

        int num = Integer.valueOf(str.substring(i, j));
        curRet = curRet + (num * sign);

        i = j;
      }
    }

    return curRet;
  }

  // solid code problem, same type of valid num problem, keep clear mind and good luck

  //     https://leetcode.com/problems/basic-calculator/discuss/62361/Iterative-Java-solution-with-stack
    /*
    Simple iterative solution by identifying characters one by one. One important thing is that the input is valid, which means the parentheses are always paired and in order.
Only 5 possible input we need to pay attention:

digit: it should be one digit from the current number
'+': number is over, we can add the previous number and start a new number
'-': same as above
'(': push the previous result and the sign into the stack, set result to 0, just calculate the new result within the parenthesis.
')': pop out the top two numbers from stack, first one is the sign before this pair of parenthesis, second is the temporary result before this pair of parenthesis. We add them together.
Finally if there is only one number, from the above solution, we haven't add the number to the result, so we do a check see if the number is zero.
    */
  private int caculateByStackII(String s) {
    Stack<Integer> stack = new Stack<Integer>();
    int result = 0;
    int number = 0;
    int sign = 1;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (Character.isDigit(c)) {
        number = 10 * number + (c - '0');
      } else if (c == '+') {
        result += sign * number;
        number = 0;
        sign = 1;
      } else if (c == '-') {
        result += sign * number;
        number = 0;
        sign = -1;
      } else if (c == '(') {
        //we push the result first, then sign;
        stack.push(result);
        stack.push(sign);
        //reset the sign and result for the value in the parenthesis
        sign = 1;
        result = 0;
      } else if (c == ')') {
        // result in ()
        result += sign * number;
        number = 0;
        result *= stack.pop();    //stack.pop() is the sign before the parenthesis (
        result += stack
            .pop();   //stack.pop() now is the result calculated before the parenthesis ()

      }
    }
    if (number != 0) {
      result += sign * number;
    }
    return result;
  }

  // Wrong
  // intuition: because has parentheses, so we could use a stack to help us, we traverse the str, if we meet a num or a sign, then we caculates the value of them, if we meet a (, then we push it to the stack and keep push everything we meet until meet a ), then we pop and caculate ths value of sign and num until pop out the correct (, then if stack is not empty, we repeats this again
  private int caculateByStack(String str) {
    if (str == null || str.length() == 0) {
      return 0;
    }

    // first remove all empty space
    str = str.replaceAll(" ", "");

    // stack help deal with parentheses
    Deque<String> strStack = new ArrayDeque<>();
    // total sum
    int sum = 0;
    for (int i = str.length() - 1; i >= 0; i--) {
      char curChar = str.charAt(i);
      if (curChar <= '9' && curChar >= '0') {
        String numStr = "";
        while (i >= 0) {
          if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
            numStr += str.charAt(i);
          } else {
            break;
          }

          i--;
        }

        int num = Integer.valueOf(numStr);

        if (i >= 0 && str.charAt(i) == '-') {
          sum -= num;
        } else {
          sum += num;
        }
      } else if (curChar == ')' || !strStack.isEmpty()) {
        strStack.push("" + curChar);
      } else if (curChar == '(') {
        boolean isPositive = true;
        int tempSum = 0;
        while (strStack.peek() != ")") {
          String top = strStack.pop();
          int num = 0;

          if (top != "+" && top != "-") {
            num = Integer.valueOf(top);
          } else {
            if (top == "-") {
              isPositive = false;
            }
          }
          if (isPositive) {
            tempSum += num;
          } else {
            tempSum -= num;
          }
        }
        strStack.pop();

        i--;
        if (i >= 0 && str.charAt(i) == '-') {
          sum -= tempSum;
        } else {
          sum += tempSum;
        }
      }
    }

    return sum;
  }
}