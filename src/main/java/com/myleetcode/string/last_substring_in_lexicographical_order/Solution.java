package com.myleetcode.string.last_substring_in_lexicographical_order;

public class Solution {
    public String lastSubstring(String s) {
        // return lastSubstringByTry(s); // TLE
        return lastSubstringByTryII(s);
        // return lastSubstringByTwoPointers(s); // Optimal but Hard.
    }

    /*
    Optimal Sol, hard:
    https://leetcode.com/problems/last-substring-in-lexicographical-order/discuss/363662/Short-python-code-O(n)-time-and-O(1)-space-with-proof-and-visualization

    */
    private String lastSubstringByTwoPointers(String str) {
        int len = str.length();
        int i = 0;
        int j = i + 1;
        int k = 0;
        while (j + k < len) {
            if (str.charAt(i + k) == str.charAt(j + k)) {
                k++;
                continue;
            }

            if (str.charAt(i + k) > str.charAt(j + k)) {
                j++;
            } else {
                i = j;
                j = i + 1;
            }
            k = 0;
        }

        return str.substring(i);
    }

    /*
    TC: O(N^2)
    SC:O(1)
    */
    private String lastSubstringByTryII(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }

        String ret = "";
        char maxChar = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= maxChar && ret.compareTo(str.substring(i)) < 0) {
                ret = str.substring(i);
                maxChar = str.charAt(i);
            }

            // Skip the continuous same chars. Could avoid TLE.
            while (i < str.length() - 1 && str.charAt(i) == str.charAt(i + 1)) {
                i++;
            }
        }
        return ret;
    }

    /*
    TLE
    TC: O(N^2)
    SC:O(1)
    */
    private String lastSubstringByTry(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }

        String ret = "";
        char maxChar = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= maxChar && ret.compareTo(str.substring(i)) < 0) {
                ret = str.substring(i);
                maxChar = str.charAt(i);
            }
        }
        return ret;
    }
}
