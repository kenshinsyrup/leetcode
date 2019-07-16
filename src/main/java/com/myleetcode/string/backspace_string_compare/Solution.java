package com.myleetcode.string.backspace_string_compare;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public boolean backspaceCompare(String S, String T) {
        // return backspaceCompareByStack(S, T);
        return backspaceCompareByPointer(S, T);
    }

    // follow up: TC O(N), SC O(1)
    // to reach O(1) SC, so we should be sure when we compare two char in String S and T, they should be the chars in the remained String after remove operations, so we should traverse the String from back to front. By this way, we dont need to store chars in extra space
    private boolean backspaceCompareByPointer(String S, String T){
        if(S == null && T == null){
            return true;
        }
        if(S == null || T == null){
            return false;
        }

        int pS = S.length() - 1;
        int pT = T.length() - 1;
        while(pS >= 0 && pT >= 0){
            // do remove operation on S
            int count = 0;
            while(pS >= 0){
                if(S.charAt(pS) == '#'){
                    count++;
                    pS--;
                }else{
                    if(count > 0){
                        count--;
                        pS--;
                    }else{
                        break;
                    }
                }
            }

            // do remove operation on T
            count = 0;
            while(pT >= 0){
                if(T.charAt(pT) == '#'){
                    count++;
                    pT--;
                }else{
                    if(count > 0){
                        count--;
                        pT--;
                    }else{
                        break;
                    }
                }
            }

            // !!! compare:
            // if pS and pT are both exhausted, true
            if(pS < 0 && pT < 0){
                return true;
            }
            // if only one is exhuasted, false
            if(pS < 0 || pT < 0){
                return false;
            }
            // no one exhausted, compare char, if equal, move forward to next round
            if(S.charAt(pS) != T.charAt(pT)){
                return false;
            }
            pS--;
            pT--;
        }
        if(pS >= 0){
            // do remove operation on S
            int count = 0;
            while(pS >= 0){
                if(S.charAt(pS) == '#'){
                    count++;
                    pS--;
                }else{
                    if(count > 0){
                        count--;
                        pS--;
                    }else{
                        break;
                    }
                }
            }
        }
        if(pT >= 0){
            // do remove operation on T
            int count = 0;
            while(pT >= 0){
                if(T.charAt(pT) == '#'){
                    count++;
                    pT--;
                }else{
                    if(count > 0){
                        count--;
                        pT--;
                    }else{
                        break;
                    }
                }
            }
        }

        // if at last, pS and pT exhausted the two string and not return false, then true
        if(pS < 0 && pT < 0){
            return true;
        }
        // otherwise, the pointer >= 0 still has char but another pointer has already been exhausted, false
        return false;
    }

    // intuition: Stack
    // use two Stack<Character> to store the char in String, for each String, if we meet # and stack is not empty, pop the top elem out; if we meet a letter not # then we push into stack. At last, we compare the two stack
    // TC: O(M + N)
    // SC: O(M + N)
    private boolean backspaceCompareByStack(String sourceStr, String targetStr){
        if(sourceStr == null && targetStr == null){
            return true;
        }
        if(sourceStr == null || targetStr == null){
            return false;
        }

        // source stack
        Deque<Character> sourceStack = new ArrayDeque<>();
        buildStack(sourceStr, sourceStack);
        // target stack
        Deque<Character> targetStack = new ArrayDeque<>();
        buildStack(targetStr, targetStack);

        // compare
        if(sourceStack.size() != targetStack.size()){
            return false;
        }
        while(!sourceStack.isEmpty() && !targetStack.isEmpty()){
            if(sourceStack.pop() != targetStack.pop()){
                return false;
            }
        }

        return true;
    }

    private void buildStack(String str, Deque<Character> charStack){
        for(int i = 0; i < str.length(); i++){
            char ch = str.charAt(i);

            if(ch == '#'){
                if(!charStack.isEmpty()){
                    charStack.pop();
                }
            }else{
                charStack.push(ch);
            }
        }
    }
}
