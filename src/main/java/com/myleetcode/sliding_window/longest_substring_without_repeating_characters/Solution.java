package com.myleetcode.sliding_window.longest_substring_without_repeating_characters;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        // return lenghtOfLongestSubstringBySlidingWindow(s);
        return lenghtOfLongestSubstringBySlidingWindowII(s);
    }

    // 3, 159, 340, 992
    // https://leetcode.com/problems/subarrays-with-k-different-integers/discuss/235002/One-code-template-to-solve-all-of-these-problems!
    // use the at most K distincts template
    private int lenghtOfLongestSubstringBySlidingWindowII(String str){
        if(str == null || str.length() == 0){
            return 0;
        }

        Map<Character, Integer> charNumMap = new HashMap<>();

        int len = str.length();
        int leftP = 0;
        int rightP = 0;
        int distinct = 0;
        int maxLen = 0;
        while(rightP < len){
            char curCh = str.charAt(rightP);
            // counting the # of char
            charNumMap.put(curCh, charNumMap.getOrDefault(curCh, 0) + 1);
            // meet first time
            if(charNumMap.get(curCh) == 1){
                distinct++;
            }

            // shrink window, here the condition needs a little thoughts: when we need shrink window, means there are repeated elem in the window[left:i], so must have this: distinct < i-left+1. (actually distinct only have two conditions, < i-left+1 or ==i-left+1, it's impossible >)
            while(distinct < rightP - leftP + 1){
                char leftCh = str.charAt(leftP);
                charNumMap.put(leftCh, charNumMap.get(leftCh) - 1);

                // if a distinct char is removed
                if(charNumMap.get(str.charAt(leftP)) == 0){
                    distinct--;
                }

                leftP++;
            }

            // get the window length, check if need update maxLen
            maxLen = Math.max(maxLen, rightP - leftP + 1);

            rightP++;
        }

        return maxLen;

    }


    // TC: O(N)
    // SC: O(min(M, N)), whrer M is the longest length of Set, actually the Set's length is at most 26
    // intuition: Sliding Window
    private int lenghtOfLongestSubstringBySlidingWindow(String str){
        if(str == null || str.length() == 0){
            return 0;
        }

        int maxLen = 0;

        Set<Character> charSet = new HashSet<>();

        int len = str.length();
        int left = 0;
        int right = 0;
        while(left <= right && right < len){
            char ch = str.charAt(right);
            // valid, spand
            if(!charSet.contains(ch)){
                // !!! must first caculate the length of current string, then make right++(of course, otherwise your lenght is not correct)
                maxLen = Math.max(maxLen, right - left + 1);

                charSet.add(ch);
                right++;
            }else{
                // repeated, shrink
                charSet.remove(str.charAt(left));
                left++;
            }
        }

        return maxLen;
    }
}