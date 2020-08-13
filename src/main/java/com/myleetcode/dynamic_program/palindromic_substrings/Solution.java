package com.myleetcode.dynamic_program.palindromic_substrings;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public int countSubstrings(String s) {
        // return countSubstringsByBacktracking(s);
        return countSubstringsByDP(s);
    }

    // TC: O(n^2)
    // SC: O(n^2)
    // DP
    // dp[i][j] means s[i,j] is palindrome or not, then we count true numbers
    // dp[i][j] = dp[i+1][j-1], if(s[i] == s[j])
    // dp[i][j] = false, otherwise
    // base case is: single char, dp[i][j] = true, if i == j; double chars, dp[i][j] = true, if i+1 == j
    // row by row from bottom
    private int countSubstringsByDP(String s) {
        // special case
        if (s == null || s.length() == 0) {
            return 0;
        }

        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        int count = 0;

        // base case: single char.
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
            count++;
        }
        // double chars.
        for (int i = 0; i < len - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                count++;
            }
        }

        // 对于字符串来说，dp找回文就是i指针从末尾向前，j从i后面向后，如果已知ij之间的是否为回文，那么只要知道i和j对应的char是不是相同就可以判断整个ij区间是否为回文
        // 那么从二维数组来看，就是以对角线为base，找最右上角的值，而依赖关系又是以来左下角的内容，所以row by row的方式不能从上向下，要从下向上
        // row by row from bottom
        for (int i = len - 1 - 1; i >= 0; i--) {
            for (int j = i + 2; j < len; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1];

                    // !!! dp[i+1][j-1] maybe not true, we only want to record number of true
                    if (dp[i][j]) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    // MLE: 这个题，虽然可以用backtracking做，但是复杂度很高，在输入比较大时爆栈，这个题目规定了输入是1000极限
    // Intuition: it requires how many, we could use backtracking to enum all possible candidates then count number, then this problem will be similar as 131. Palindrome Partitioning
    private int countSubstringsByBacktracking(String s) {
        // special case
        if (s == null || s.length() == 0) {
            return 0;
        }

        // for backtracking, we need a ret list to store all candidates; a idx to record position
        List<String> ret = new ArrayList<>();
        boolean[][] visited = new boolean[s.length()][s.length()];


        backtracking(s, ret, visited, 0);

        return ret.size();
    }

    // TC O(2^n): dfs实现的backtracking是指数型的
    // SC O(n^2)
    private void backtracking(String s, List<String> ret, boolean[][] visited, int idx) {
        // base case
        if (idx >= s.length()) {
            return;
        }

        // all substrings start from idx
        for (int i = idx; i < s.length(); i++) {
            if (visited[idx][i]) {
                continue;
            }

            visited[idx][i] = true;
            String substr = s.substring(idx, i + 1);
            // if is palindrome, add to ret, keep going down
            if (isPalindrome(substr)) {
                idx++;
                ret.add(substr);

                backtracking(s, ret, visited, idx);

                // back
                idx--;
            }
        }
    }

    // TC O(n)
    // SC O(1)
    // need a isPalindrome to check input is palindrome or not
    private boolean isPalindrome(String str) {
        int start = 0;
        int end = str.length() - 1;
        while (start <= end) {
            if (str.charAt(start) != str.charAt(end)) {
                return false;
            }

            start++;
            end--;
        }
        return true;
    }


}