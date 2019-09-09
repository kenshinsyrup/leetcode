package com.myleetcode.greedy.longest_palindrome;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int longestPalindrome(String s) {
        return longestPalindromeByGreedy(s);
    }

    /*
    intuition:
    This problem actually want us to use the letters to build a stirng, then if it's a palindrome, return true. Since we could build many strings, the most natural thought is useing backtracking to build all strings, then check each string, if find one is palindrome then record its length, until we find the longest one. TC is (2^N * N), 2^N is backtracking, N is check palindrome

    BUT, that's too overkill. Since the problem only need the length of longtest palindrome, not the specific string, so we could only caculate numbers.
    1. we build a map to count all letters and their numbers
    2. traverse the map, for each letter, we know:
        2.1 if its number is even, we could always put all of it to the final longest palindrome string
        2.2 if odd, then, if more than 2, then we could put num-1 current letter to the final longest palindrome string
    3. after traverse the map, if final length is smaller than total letter number, we plus one to the final length, means we could pick any one letter from the rest letters to make the final palindrome string(length is even) to be a new longer palindrome string(now length is odd)
    */
    /*
    TC: O(N)
    SC: O(N)
    */
    private int longestPalindromeByGreedy(String str){
        if(str == null || str.length() == 0){
            return 0;
        }
        if(str.length() < 2){
            return str.length();
        }

        int len = str.length();
        // 1. map char-num
        Map<Character, Integer> charNumMap = new HashMap<>();
        for (int i = 0; i < len; i++){
            charNumMap.put(str.charAt(i), charNumMap.getOrDefault(str.charAt(i), 0) + 1);
        }

        int res = 0;
        // 2. caculate length
        for (Character ch : charNumMap.keySet()){
            // ch num is even
            if (charNumMap.get(ch) % 2 == 0){
                res += charNumMap.get(ch);
            }else{
                // ch num is odd and more than 2
                if(charNumMap.get(ch) > 2){
                    res += charNumMap.get(ch) - 1;
                }
            }
        }

        // 3. check if we exhausted all letters
        if (res < len){
            res += 1;
        }

        return res;
    }
}
