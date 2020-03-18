package com.myleetcode.sliding_window.number_of_substrings_containing_all_three_characters;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int numberOfSubstrings(String s) {
        return numberOfSubstringsBySlidingWindow(s);
    }

    /*
    Sliding Window

    Important:
    how to count
    https://leetcode.com/problems/number-of-substrings-containing-all-three-characters/discuss/516973/JavaPython-3-Sliding-Window-O(n)-O(1)-code-w-explanation-and-analysis.

    TC: O(N)
    SC: O(1)
    */
    private int numberOfSubstringsBySlidingWindow(String str) {
        if (str == null || str.length() < 3) {
            return 0;
        }

        int ret = 0;
        Map<Character, Integer> charNumMap = new HashMap<>();
        int uniqueCount = 0;
        int len = str.length();
        int left = 0;
        int right = 0;
        while (right < len) {
            char ch = str.charAt(right);
            if (ch == 'a' || ch == 'b' || ch == 'c') {
                charNumMap.put(ch, charNumMap.getOrDefault(ch, 0) + 1);
                if (charNumMap.get(ch) == 1) {
                    uniqueCount++;
                }
            }

            // Shrink and Count
            while (uniqueCount == 3 && left <= right) {
                // Count.
                // Important: When we find a,b,c exists in substring [left:right], then means every substring starts from left and end with index larger than or equal to right is valid
                ret += (str.length() - 1 - right + 1);

                // Shrink.
                char leftCh = str.charAt(left);
                if (leftCh == 'a' || leftCh == 'b' || leftCh == 'c') {
                    charNumMap.put(leftCh, charNumMap.get(leftCh) - 1);
                    if (charNumMap.get(leftCh) == 0) {
                        uniqueCount--;
                    }
                }

                left++;
            }

            right++;
        }

        return ret;
    }
}
