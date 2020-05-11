package com.myleetcode.string.special_binary_string;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
    public String makeLargestSpecial(String S) {
        return makeLargestSpecialByRecursion(S);
    }

    /*
    https://leetcode.com/problems/special-binary-string/discuss/113211/JavaC%2B%2BPython-Easy-and-Concise-Recursion
    Explanation:
        Split S into several special strings (as many as possible).
        Special string starts with 1 and ends with 0. Recursion on the middle part.
        Sort all special strings in lexicographically largest order.
        Join and output all strings.

    The middle part of a special string may not be another special string. But in my recursion it is.
For example, 1M0 is a splitted special string. M is its middle part and it must be another special string.
    Because:
    The number of 0's is equal to the number of 1's in M
    If there is a prefix P of Mwhich has one less 1's than 0's, 1P will make up a special string. 1P will be found as special string before 1M0 in my solution.
    It means that every prefix of M has at least as many 1's as 0's.
    Based on 2 points above, M is a special string.
    */
    private String makeLargestSpecialByRecursion(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        return process(str);
    }

    private String process(String str) {
        int len = str.length();
        int leftIdx = 0;
        int rightIdx = 0;

        List<String> specialList = new ArrayList<>();
        int count = 0;
        while (rightIdx < len) {
            char ch = str.charAt(rightIdx);
            if (ch == '1') {
                count++;
            } else {
                count--;
            }

            // One special string.
            if (count == 0) {
                String subAns = process(str.substring(leftIdx + 1, rightIdx));
                specialList.add("1" + subAns + "0");

                leftIdx = rightIdx + 1;
            }

            rightIdx++;
        }

        // Sort by lexicographically descending order.
        Collections.sort(specialList, (String a, String b) -> {
            return b.compareTo(a);
        });

        // Current ans string.
        StringBuilder sb = new StringBuilder();
        for (String s : specialList) {
            sb.append(s);
        }
        return sb.toString();
    }
}
