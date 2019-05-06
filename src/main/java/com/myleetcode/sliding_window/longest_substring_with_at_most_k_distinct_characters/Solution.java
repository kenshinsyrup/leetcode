package com.myleetcode.sliding_window.longest_substring_with_at_most_k_distinct_characters;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        // return lengthOfLongestSubstringKDistinctBySlidingWindow(s, k);
        return lengthOfLongestSubstringKDistinctII(s, k);
    }

    // 3, 159, 340, 992
    // https://leetcode.com/problems/subarrays-with-k-different-integers/discuss/235002/One-code-template-to-solve-all-of-these-problems!
    // use the at most K distincts template
    private int lengthOfLongestSubstringKDistinctII(String str, int K){
        if(str == null || str.length() == 0){
            return 0;
        }

        int maxLen = 0;

        Map<Character, Integer> charNumMap = new HashMap<>();

        int len = str.length();
        int left = 0;
        int distinct = 0;
        for(int i = 0; i < len; i++){
            charNumMap.put(str.charAt(i), charNumMap.getOrDefault(str.charAt(i), 0) + 1);

            if(charNumMap.get(str.charAt(i)) == 1){
                distinct++;
            }

            while(left <= i && distinct > K){
                charNumMap.put(str.charAt(left), charNumMap.get(str.charAt(left)) - 1);

                if(charNumMap.get(str.charAt(left)) == 0){
                    distinct--;
                }

                left++;
            }

            maxLen = Math.max(maxLen, i - left + 1);
        }

        return maxLen;
    }

    // intuition: this is a more generalized situation derived from problem: 159. Longest Substring with At Most Two Distinct Characters
    // but the thoughts behind is same
    private int lengthOfLongestSubstringKDistinctBySlidingWindow(String s, int k){
        // special case
        if(s == null || k <= 0){
            return 0;
        }
        if(s.length() <= k){
            return s.length();
        }

        // map to count distinct char num
        Map<Character, Integer> charNumMap = new HashMap<>();

        char[] charArray = s.toCharArray();

        // window left
        int leftP = 0;

        int maxLen = Integer.MIN_VALUE;

        for(int i = 0; i < charArray.length; i++){
            // current char add to map
            charNumMap.put(charArray[i], charNumMap.getOrDefault(charArray[i], 0) + 1);

            // if window is valid, update maxLen
            if(charNumMap.size() <= k){
                maxLen = Math.max(maxLen, i - leftP + 1); // here use index to caculate length is safe and fast
            }else{
                // shrink window
                while(leftP < i){
                    // get char at leftP
                    char charLeftP = charArray[leftP];
                    // reduce its number in map
                    charNumMap.put(charLeftP, charNumMap.get(charLeftP) - 1);
                    // move forward leftP
                    leftP++;

                    // check if window valid
                    if(charNumMap.get(charLeftP) == 0){
                        charNumMap.remove(charLeftP);
                        break;
                    }
                }
            }
        }

        return maxLen;

    }

}
