package com.myleetcode.stack.longest_valid_parentheses;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int longestValidParentheses(String s) {
        // return longestValidParenthesesByCount(s);// WRONG
        return longestValidParenthesesBysStack(s);// Prefer
        // return longestValidParenthesesBysStackII(s);
    }

    // and, there's a way to caculate the longest on the fly to optimize this stack solution, but it's hard to understand and remember. Prefer the original one.
    private int longestValidParenthesesBysStackII(String s){
        if(s == null || s.length() <= 1){
            return 0;
        }

        int maxLen = 0;
        Deque<Integer> idxStack = new ArrayDeque<>();

        idxStack.push(-1);

        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '('){
                idxStack.push(i);
            }else{
                idxStack.pop();

                if(idxStack.isEmpty()){
                    idxStack.push(i);
                }else{
                    maxLen = Math.max(maxLen, i - idxStack.peek());
                }
            }
        }

        return maxLen;
    }

    // TC: O(N)
    // SC: O(N)
    // https://leetcode.com/problems/longest-valid-parentheses/discuss/14126/My-O(n)-solution-using-a-stack
    private int longestValidParenthesesBysStack(String s){
        if(s == null || s.length() <= 1){
            return 0;
        }

        // use a stack to keep all index that NOT has valid pair
        Deque<Integer> idxStack = new ArrayDeque<>();
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '('){
                idxStack.push(i);
            }else{
                if(idxStack.isEmpty()){
                    idxStack.push(i);
                }else{
                    int topIdx = idxStack.peek();
                    if(s.charAt(topIdx) == '('){
                        idxStack.pop();
                    }else{
                        idxStack.push(i);
                    }
                }
            }
        }

        // caculate longest, since all not valid idx are in stack, then str between these idx are valid
        if(idxStack.isEmpty()){
            return s.length();
        }

        int endIdx = s.length(); // !!!here we have to assume we have a invalid idx at s.length() position, this could help us keep consistent pattern: badStartIdx+"good pairs"+badEndIdx, then badEndIdx-badStartIdx-2 is the good pairs length.
        int startIdx = s.length();
        int maxLen = 0;
        while(!idxStack.isEmpty()){
            startIdx = idxStack.pop();
            maxLen = Math.max(maxLen, endIdx - startIdx - 1);

            endIdx = startIdx;
        }
        // same reson, we should assume we have a invalid idx before index 0 ie -1
        maxLen = Math.max(maxLen, endIdx - (-1) -1);

        return maxLen;
    }

    // this fail when input is "()(()"
    // TC: O(N)
    // SC: O(1)
    // intuition: traverse the string, remember ( and ) #, if ) more than ( we reset the count of (). for convience we could use a variable called pair to count ( and ), if we meet ( we add 1 to it, if we meet ); then here is more interesting, we add -1 to it, if pair is >= 0 means our ) successfully find a pair, so we make count++ and update max, if pair < 0 we reset count. At last we should rememeber turn max*2 because the problem nedd output length not pairs.
    private int longestValidParenthesesByCount(String s){
        if(s == null || s.length() <= 1){
            return 0;
        }

        int count = 0;
        int pair = 0;
        int max = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '('){
                pair += 1;
            }else{
                pair -= 1;

                if(pair >= 0){
                    count++;
                    max = Math.max(max, count);
                }
                if(pair < 0){
                    count = 0;
                    pair = 0;
                }
            }
        }

        return max * 2;
    }
}
