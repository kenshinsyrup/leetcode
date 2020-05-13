package com.myleetcode.array.di_string_match;

public class Solution {
    public int[] diStringMatch(String S) {
        return diStringMatchBySimulate(S);
    }

    private int[] diStringMatchBySimulate(String S) {
        if (S == null || S.length() == 0) {
            return new int[]{0};
        }

        int len = S.length();
        int[] res = new int[len + 1];
        int left = 0;
        int right = len;
        for (int i = 0; i < len; i++) {
            if (S.charAt(i) == 'I') {
                res[i] = left;
                left++;
            } else {
                res[i] = right;
                right--;
            }
        }
        res[len] = left;

        return res;
    }
}
