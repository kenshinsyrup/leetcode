package com.myleetcode.stack.evaluate_reverse_polish_notation;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int evalRPN(String[] tokens) {
        return evalRPNByStack(tokens);
    }

    // TC: O(N)
    // SC: O(N)
    // intuition: Stack
    // we could push num into a Stack, if we meet a operand, we pop out two num to caculate with this operand then push back the ret, repeat
    private int evalRPNByStack(String[] tokens){
        if(tokens == null || tokens.length == 0){
            return 0;
        }

        int len = tokens.length;
        Deque<Integer> numStack = new ArrayDeque<>();

        for(int i = 0; i < len; i++){
            if(!isOperand(tokens[i])){
                // if not operand, convert to int and push to stack
                numStack.push(Integer.valueOf(tokens[i]));
            }else{
                // since input always valid, could not check if the stack the empty
                // !!! remember the sequence, first num(more left) is second popped
                int secNum = numStack.pop();
                int firNum = numStack.pop();

                // otherwise, popout two, exhaust the operand, push back ret
                if(tokens[i].equals("+")){
                    numStack.push(firNum + secNum);
                }else if(tokens[i].equals("-")){
                    numStack.push(firNum - secNum);
                }else if(tokens[i].equals("*")){
                    numStack.push(firNum * secNum);
                }else if(tokens[i].equals("/")){
                    numStack.push(firNum / secNum);
                }
            }
        }

        return numStack.pop();
    }

    private boolean isOperand(String str){
        if(str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")){
            return true;
        }
        return false;
    }

}
