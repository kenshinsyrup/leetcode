package com.myleetcode.sliding_window.replace_the_substring_for_balanced_string;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int balancedString(String s) {
        return balancedStringBySlidingWindow(s);
    }

    /*

    https://leetcode.com/problems/replace-the-substring-for-balanced-string/discuss/482118/Sliding-Window-Pattern

    */
    private int balancedStringBySlidingWindow(String str) {
        if (str == null || str.length() < 4) {
            return 0;
        }

        int len = str.length();
        int need = len / 4;

        // Get the each bad char's number and count unique bad char number
        Map<Character, Integer> badCharNumMap = new HashMap<>();
        int uniqueBadCharCount = 0;
        for (char ch : str.toCharArray()) {
            badCharNumMap.put(ch, badCharNumMap.getOrDefault(ch, 0) + 1);
            // First time excess need when adding, count one unique
            if (badCharNumMap.get(ch) == need + 1) {
                uniqueBadCharCount++;
            }
        }
        if (uniqueBadCharCount == 0) {
            return 0;
        }

        int uniqueBadCharInWindowCount = 0;
        int ret = Integer.MAX_VALUE;
        int left = 0;
        int right = 0;
        while (right < len) {
            char rightCh = str.charAt(right);
            badCharNumMap.put(rightCh, badCharNumMap.get(rightCh) - 1);
            // First time reach need when reducing, count one unique in sliding window
            if (badCharNumMap.get(rightCh) == need) {
                uniqueBadCharInWindowCount++;
            }

            // Shrink
            while (uniqueBadCharInWindowCount == uniqueBadCharCount && left <= right) {
                // while(uniqueBadCharInWindowCount == uniqueBadCharCount && left <= right){
                ret = Math.min(ret, right - left + 1);

                char leftCh = str.charAt(left);
                badCharNumMap.put(leftCh, badCharNumMap.get(leftCh) + 1);
                if (badCharNumMap.get(leftCh) == need + 1) {
                    uniqueBadCharInWindowCount--;
                }
                left++;
            }

            right++;
        }

        if (ret == Integer.MAX_VALUE) {
            return 0;
        }
        return ret;

    }
}
