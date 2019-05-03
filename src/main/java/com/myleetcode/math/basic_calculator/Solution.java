package com.myleetcode.math.basic_calculator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

class Solution {
    public int calculate(String s) {
        // return caculateByStack(s); // wrong
        return caculateByStackII(s);
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
    private int caculateByStackII(String s){
        Stack<Integer> stack = new Stack<Integer>();
        int result = 0;
        int number = 0;
        int sign = 1;
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(Character.isDigit(c)){
                number = 10 * number + (int)(c - '0');
            }else if(c == '+'){
                result += sign * number;
                number = 0;
                sign = 1;
            }else if(c == '-'){
                result += sign * number;
                number = 0;
                sign = -1;
            }else if(c == '('){
                //we push the result first, then sign;
                stack.push(result);
                stack.push(sign);
                //reset the sign and result for the value in the parenthesis
                sign = 1;
                result = 0;
            }else if(c == ')'){
                // result in ()
                result += sign * number;
                number = 0;
                result *= stack.pop();    //stack.pop() is the sign before the parenthesis (
                result += stack.pop();   //stack.pop() now is the result calculated before the parenthesis ()

            }
        }
        if(number != 0) result += sign * number;
        return result;
    }

    // Wrong
    // intuition: because has parentheses, so we could use a stack to help us, we traverse the str, if we meet a num or a sign, then we caculates the value of them, if we meet a (, then we push it to the stack and keep push everything we meet until meet a ), then we pop and caculate ths value of sign and num until pop out the correct (, then if stack is not empty, we repeats this again
    private int caculateByStack(String str){
        if(str == null || str.length() == 0){
            return 0;
        }

        // first remove all empty space
        str = str.replaceAll(" ", "");

        // stack help deal with parentheses
        Deque<String> strStack = new ArrayDeque<>();
        // total sum
        int sum = 0;
        for(int i = str.length() - 1; i >= 0; i--){
            char curChar = str.charAt(i);
            if(curChar <= '9' && curChar >= '0'){
                String numStr = "";
                while(i >= 0){
                    if(str.charAt(i) >= '0' && str.charAt(i) <= '9'){
                        numStr += str.charAt(i);
                    }else{
                        break;
                    }

                    i--;
                }

                int num = Integer.valueOf(numStr);

                if(i >= 0 && str.charAt(i) == '-'){
                    sum -= num;
                }else{
                    sum += num;
                }
            }else if(curChar == ')' || !strStack.isEmpty()){
                strStack.push("" + curChar);
            }else if(curChar == '('){
                boolean isPositive = true;
                int tempSum = 0;
                while(strStack.peek() != ")"){
                    String top = strStack.pop();
                    int num = 0;

                    if(top != "+" && top != "-"){
                        num = Integer.valueOf(top);
                    }else{
                        if(top == "-"){
                            isPositive = false;
                        }
                    }
                    if(isPositive){
                        tempSum += num;
                    }else{
                        tempSum -= num;
                    }
                }
                strStack.pop();

                i--;
                if(i >= 0 && str.charAt(i) == '-'){
                    sum -= tempSum;
                }else{
                    sum += tempSum;
                }
            }
        }

        return sum;
    }
}
