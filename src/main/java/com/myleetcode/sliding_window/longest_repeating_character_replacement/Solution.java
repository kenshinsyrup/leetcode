package com.myleetcode.sliding_window.longest_repeating_character_replacement;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int characterReplacement(String s, int k) {
        // return characterReplacementBySlidingWindow(s, k);
        return characterReplacementBySlidingWindowII(s, k);
    }

    /*
    Sliding Window:
    Although we coulsolve this problem with the ordinary template of Sliding Window, but there do exist a better solution for this question.
    https://leetcode.com/problems/longest-repeating-character-replacement/discuss/181382/Java-Sliding-Window-with-Explanation

left: left index thats in window
right: right index thats in window
count: character count thats in window
uniqueMostCount: count of most unique characters in window
replaceCount: number of replacement needed for all characters in window to be the same

Each time we expand right, we include a new character in window.
If replaceCount is bigger than k, we got an invalid window, we should skip it until window is valid again, but only expands window size, never shrink (because even if we got a smaller window thats valid, it doesn't matter because we already found a window thats bigger and valid)

The most important part is to get the idea that the replaceCount is window length minus uniqueMostCount, and we do not need shrink window when replaceCount is bigger than k because a valid window always have better answer than its subwindow.

    TC: O(N)
    SC: O(N)
    */
    private int characterReplacementBySlidingWindowII(String s, int k) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (k < 0) {
            return 0;
        }

        int len = s.length();
        int left = 0;
        int right = 0;
        Map<Character, Integer> charNumMap = new HashMap<>();
        int uniqueMostCount = 0;
        int replaceCount = 0;
        int ret = 0;
        while (right < len) {
            char ch = s.charAt(right);
            charNumMap.put(ch, charNumMap.getOrDefault(ch, 0) + 1);
            uniqueMostCount = Math.max(uniqueMostCount, charNumMap.get(ch));
            replaceCount = right - left + 1 - uniqueMostCount;

            if (replaceCount > k) {
                char leftCh = s.charAt(left);
                charNumMap.put(leftCh, charNumMap.get(leftCh) - 1);

                left++;
            } else {
                ret = Math.max(ret, right - left + 1);
            }

            right++;
        }

        return ret;
    }

    // Ordinary Silding Window solution.
    private int characterReplacementBySlidingWindow(String str, int k) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        if (k < 0) {
            return 0;
        }

        int ret = 0;
        Map<Character, Integer> charNumMap = new HashMap<>();
        int len = str.length();
        int left = 0;
        int right = 0;
        while (right < len) {
            char rightCh = str.charAt(right);
            charNumMap.put(rightCh, charNumMap.getOrDefault(rightCh, 0) + 1);

            int operations = 0;
            int maxCharNum = Integer.MIN_VALUE;
            for (int num : charNumMap.values()) {
                operations += num;
                maxCharNum = Math.max(maxCharNum, num);
            }
            operations -= maxCharNum;

            while (charNumMap.keySet().size() > 1 && operations > k && left <= right) {
                char leftCh = str.charAt(left);
                charNumMap.put(leftCh, charNumMap.get(leftCh) - 1);
                if (charNumMap.get(leftCh) == 0) {
                    charNumMap.remove(leftCh);
                }

                operations = 0;
                maxCharNum = Integer.MIN_VALUE;
                for (int num : charNumMap.values()) {
                    maxCharNum = Math.max(maxCharNum, num);
                }
                operations -= maxCharNum;


                left++;
            }

            ret = Math.max(ret, right - left + 1);

            right++;
        }

        return ret;
    }
}

