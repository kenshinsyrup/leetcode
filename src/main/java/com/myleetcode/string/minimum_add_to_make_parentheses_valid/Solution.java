package com.myleetcode.string.minimum_add_to_make_parentheses_valid;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int minAddToMakeValid(String S) {
        // return minAddToMakeValidByStack(S);
        return minAddToMakeValidByTwoVariable(S);
    }

    /*
    出错点：
    1 智障: char ch == '(' 错误的写成了ch == "("
    */

    // after done with Stack, we could think, could we use non-extra space? we could use a countL to record the num of ( and countR to record the num of ). if meet a (, countL++; if meet a ): if countL is not 0, countL--, if countL is 0, countR++. at last, return countL + countR
    // TC: O(N)
    // SC: O(1)
    private int minAddToMakeValidByTwoVariable(String str){
        if(str == null || str.length() == 0){
            return 0;
        }
        if(str.length() == 1){
            return 1;
        }

        int countL = 0;
        int countR = 0;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == '('){
                countL++;
            }else{
                if(countL != 0){
                    countL--;
                }else{
                    countR++;
                }
            }
        }

        return countL + countR;
    }

    // intuition: Stack
    // traverse the String S
    // 1 if meet (, push to stack
    // 2 if meet a ): 2.1 pop up a ( if stack is not empty; 2.2 use a variable count to count this situation
    // at last, stack size + count is the ans
    // TC: O(N)
    // SC: O(N)
    private int minAddToMakeValidByStack(String str){
        if(str == null || str.length() == 0){
            return 0;
        }
        if(str.length() == 1){
            return 1;
        }

        int count = 0;
        Deque<Character> pStack = new ArrayDeque<>();
        for(int i = 0; i < str.length(); i++){
            char ch = str.charAt(i);

            if(ch == '('){
                pStack.push(ch);
            }else{
                if(!pStack.isEmpty()){
                    pStack.pop();
                }else{
                    count++;
                }
            }
        }

        return pStack.size() + count;
    }
}
