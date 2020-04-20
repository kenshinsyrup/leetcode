package com.myleetcode.bad_problems.maximum_nesting_depth_of_two_valid_parentheses_strings;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public int[] maxDepthAfterSplit(String seq) {
        return maxDepthAfterSplitByMath(seq);
    }

    /*
    Bad Question.

    Explaination and Answer:
    https://leetcode.com/problems/maximum-nesting-depth-of-two-valid-parentheses-strings/discuss/358419/Confused-by-this-problem-I-was-too-but-here-is-how-it-became-crystal-clear...

    */
    private int[] maxDepthAfterSplitByMath(String seq) {
        if (seq == null || seq.length() == 0) {
            return new int[0];
        }

        int len = seq.length();
        List<Integer> retList = new ArrayList<>();
        int depth = 0;
        for (char ch : seq.toCharArray()) {
            if (ch == '(') {
                depth++;
            }

            // Assign to group A or B.
            retList.add(depth % 2);

            if (ch == ')') {
                depth--;
            }
        }

        int size = retList.size();
        int[] ret = new int[size];
        for (int i = 0; i < size; i++) {
            ret[i] = retList.get(i);
        }

        return ret;
    }
}