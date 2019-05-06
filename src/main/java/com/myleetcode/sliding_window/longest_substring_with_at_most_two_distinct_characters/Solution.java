package com.myleetcode.sliding_window.longest_substring_with_at_most_two_distinct_characters;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        // return lengthOfLongestSubstringTwoDistinctBySlidingWindow(s);
        // return lengthOfLongestSubstringTwoDistinctBySlidingWindowII(s);
        return lengthOfLongestSubstringTwoDistinctBySlidingWindowIII(s, 2);
    }

    // 3, 159, 340, 992
    // https://leetcode.com/problems/subarrays-with-k-different-integers/discuss/235002/One-code-template-to-solve-all-of-these-problems!
    // use the at most K distincts template
    private int lengthOfLongestSubstringTwoDistinctBySlidingWindowIII(String str, int K){
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

    // optimize, template it
    // actually there's a solution with int[] array instead of map to count the char, that will be much faster and spend less space, but I dont like it. there will be many logics to caculates num of chars and hard to remeber.
    private int lengthOfLongestSubstringTwoDistinctBySlidingWindowII(String s){
        if(s == null){
            return 0;
        }
        if(s.length() <= 2){
            return s.length();
        }

        // map to count
        Map<Character, Integer> charNumMap = new HashMap<>();

        char[] charArray = s.toCharArray();

        // two pointers to form window
        int leftP = 0;
        // int rightP = 0;

        // result
        int maxLen = Integer.MIN_VALUE;

        // outter loop, spread window and when necessary we shrink window
        for(int i = 0; i < charArray.length; i++){ //i is rightP
            // spread window
            charNumMap.put(charArray[i], charNumMap.getOrDefault(charArray[i], 0) +1);

            // update maxLen if have less than or equal to 2 keys
            if(charNumMap.keySet().size() <= 2){
                maxLen = Math.max(maxLen, i - leftP + 1);
            }

            // if has more than two keys in map, shrink window to make it's valid
            if(charNumMap.keySet().size() > 2){
                // loop to shrink window
                while(leftP < i){
                    // get current leftP char
                    char charLeftP = charArray[leftP];
                    // move forward
                    leftP++;

                    // reduce num and if one distinct char key has 0 num, remove it and we are good then
                    charNumMap.put(charLeftP, charNumMap.get(charLeftP) - 1);
                    if(charNumMap.get(charLeftP) == 0){
                        charNumMap.remove(charLeftP);
                        break;
                    }
                }
            }
        }

        return maxLen;

    }

    // TC: O(N)
    // SC: O(N)
    // intuition: sliding window. use leftP and rightP to build a window, use a map to store what chars we find now, normally leftP points to a char A and rightP points to another different char B.
    // rightP move to end of S, if it encounter a new char which is different to current two, ie not contained in current map, we put it into map and shrink window, move leftP to the first B char, this we could do by leftP + map.get(A) this is the value's usage in map. then we start spread window again.
    private int lengthOfLongestSubstringTwoDistinctBySlidingWindow(String s){
        if(s == null){
            return 0;
        }
        if(s.length() <= 2){
            return s.length();
        }

        Map<Character, Integer> charNumMap = new HashMap<>();
        char[] charArray = s.toCharArray();
        int leftP = 0;
        // int rightP = 0;
        int maxLen = Integer.MIN_VALUE;
        for(int i = 0; i < charArray.length; i++){ //i is rightP
            // if we have less than or euqal to 2 keys in map, we need to spread window
            if(charNumMap.keySet().size() <= 2){
                charNumMap.put(charArray[i], charNumMap.getOrDefault(charArray[i], 0) +1);

                // update maxLen if have 2 keys
                if(charNumMap.keySet().size() <= 2){
                    // this costs too much, but dont get good idea
                    // int count = 0;
                    // for(char key: charNumMap.keySet()){
                    //     count += charNumMap.get(key);
                    // }
                    // actually we could use the i - leftP + 1

                    maxLen = Math.max(maxLen, i - leftP + 1);
                }
            }

            // if has more than two keys in map, shrink window
            if(charNumMap.keySet().size() > 2){
                // otherwise we shrink window
                while(leftP < i){
                    // get current leftP char
                    char charLeftP = charArray[leftP];
                    // move
                    leftP++;

                    // reduce num
                    charNumMap.put(charLeftP, charNumMap.get(charLeftP) - 1);
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
