package com.myleetcode.stack.mini_parser;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    /**
     * // This is the interface that allows for creating nested lists.
     * // You should not implement it, or speculate about its implementation
     * public interface NestedInteger {
     *     // Constructor initializes an empty nested list.
     *     public NestedInteger();
     *
     *     // Constructor initializes a single integer.
     *     public NestedInteger(int value);
     *
     *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
     *     public boolean isInteger();
     *
     *     // @return the single integer that this NestedInteger holds, if it holds a single integer
     *     // Return null if this NestedInteger holds a nested list
     *     public Integer getInteger();
     *
     *     // Set this NestedInteger to hold a single integer.
     *     public void setInteger(int value);
     *
     *     // Set this NestedInteger to hold a nested list and adds a nested integer to it.
     *     public void add(NestedInteger ni);
     *
     *     // @return the nested list that this NestedInteger holds, if it holds a nested list
     *     // Return null if this NestedInteger holds a single integer
     *     public List<NestedInteger> getList();
     * }
     */

// intuition: more like: Basic Caculator, Valid Num which's purpuse is to check solid code ability, keep mind clear
/*
The idea is very straightforward:

if it's '[', we just construct a new nested integer and push it onto the stack

if it's a number, we parse the whole number and add to the previous nested integer object

if it's ',', we'll just continue;

if it's ']', we'll just pop one nested integer from the working stack and assign it to the result

Also, we'll pay attention to this corner case or understand the input: the input could be "324", "[324]", they are different: the former should return a nested integer with one single integer, the latter should return a nested integer with a list
*/
// https://leetcode.com/problems/mini-parser/discuss/86204/Java-Solution-using-Stack.-logic-same-same-as-basic-calculator-question
// the thought is:
// if meet "[", we push a new empty NI into Stack
// !!! if mee "]", we pop the top NI out, then add it to the new top NI
// if meet "-", change sign
// !!! if meet digit, find continus digit char to construct a int, then 1 if stack is empty, then use this num to build a NI to push into stack; or 2 add it to the top NI
    public NestedInteger deserialize(String s) {
        return deserializeByStack(s);
    }

    // TC: O(N)
    // SC: O(N)
    private NestedInteger deserializeByStack(String str){
        if(str == null || str.length() == 0){
            return new NestedInteger(); // observe, NI has two constructor, here we build empty NI
        }

        Deque<NestedInteger> niStack = new ArrayDeque<>();

        int len = str.length();
        int sign = 1;
        for(int i = 0; i < len; i++){
            char curChar = str.charAt(i);

            if(curChar == '['){
                niStack.push(new NestedInteger());// push empty NI
            }else if(curChar == ']' && niStack.size() > 1){ // !!! must size > 1, if curChar is ] and size is 1, means we are at the last ] and the stack only has one NI in the form of [...]. (BUT we should know valid NI also have the form of ... without outter[], so we could not just return here if the size is 1 and return empty NI at the last of the program)
                NestedInteger curNI = niStack.pop();
                niStack.peek().add(curNI);
            }else if(curChar == '-'){
                sign *= -1;
            }else if(isDigit(curChar)){
                // get the int by continuous digit char
                int num = curChar - '0';
                while(i + 1 < len && isDigit(str.charAt(i + 1))){
                    num = num * 10 + (str.charAt(i + 1) - '0');

                    i++; // move i
                }
                num *= sign; // sign

                // with the num, we get a NI, then if stack is emtpy, push in; if not, peek the top NI and add current NI as its elem
                if(niStack.isEmpty()){
                    niStack.push(new NestedInteger(num));// here use another constructor
                }else{
                    niStack.peek().add(new NestedInteger(num));
                }

                // since the input is valid, so reset the sign after use
                sign = 1;
            }
            // the last else we omit it, because it's ',', we just continue
        }

        // since the input is valid, so at last we should only have one NI in stack or empty
        return niStack.isEmpty() ? new NestedInteger() : niStack.pop() ;
    }

    private boolean isDigit(char ch){
        return ch <= '9' && ch >= '0';
    }
}
