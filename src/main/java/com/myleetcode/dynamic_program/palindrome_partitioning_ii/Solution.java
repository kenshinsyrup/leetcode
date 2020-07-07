package com.myleetcode.dynamic_program.palindrome_partitioning_ii;

public class Solution {
    public int minCut(String s) {
        return minCutByDP(s);
    }

    // intuition: CS5800, DP, same problem as "Minimum Length of Palindromes Substring List".
    /*
    Palindrome List Problem.

    Thought:
        dp[i] means given first i chars in string s, if we make every substring of s be a palindrome, the min length of the palindrome list.

    Function:
        dp[i] = min(
            dp[i], self, get in base case.
            dp[i-1]+1, if s[i-1:i-1] is palindrome
            dp[i-2]+1, if s[i-2:i-1] is palindrome
            dp[i-3]+1, if s[i-3:i-1] is palindrome
            ...,
            dp[0]+1, if s[0:i-1] is palindrome
            )

    Base case:
        dp[i] = i, means given i chars in string s, palindrome list length is at most i, because every char itself is a palindrome.
        dp[0] = 0, means given 0 char in string s, palindrome list length is 0.

    At last, we want to know the number of cuts, that the length of Palindrome List minus 1.

    TC: O(N^2) with isPalindromeDP check; O(N^3) with Two Pointers check.
    SC: O(N^2) with isPalindromeDP check; O(N) with Two Pointers check.

    The getIsPalindromeDPArray is the main part of solution for 5. Longest Palindromic Substring.
    */
    private int minCutByDP(String s) {
        // Special case.
        if (s == null || s.length() == 0) {
            return 0;
        }

        boolean[][] isPalindromeDP = getIsPalindromeDPArray(s);

        int len = s.length();
        int[] dp = new int[len + 1];

        // Base case.
        dp[0] = 0;
        for (int i = 1; i <= len; i++) {
            dp[i] = i;
        }

        // DP explore.
        for (int i = 1; i <= len; i++) {
            for (int j = 0; j < i; j++) {
                // if(isPalindromeByTwoPointers(j, i - 1, s)){
                //     dp[i] = Math.min(dp[i], dp[j] + 1);
                // }
                if (isPalindromeByDPArray(isPalindromeDP, j, i - 1)) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }

        return dp[len] - 1; // 我们要的是dp被分配了整个S的时候的值即取dp[len], 我们用的算法是minmimum number of palindrom list, 所以cut number = list number - 1.
    }

    // TC: O(N)
    private boolean isPalindromeByTwoPointers(int leftIdx, int rightIdx, String s) {

        while (leftIdx <= rightIdx) {
            if (s.charAt(leftIdx) != s.charAt(rightIdx)) {
                return false;
            }

            leftIdx++;
            rightIdx--;
        }

        return true;
    }

    private boolean isPalindromeByDPArray(boolean[][] dp, int leftStrIdx, int rightStrIdx) {
        return dp[leftStrIdx + 1][rightStrIdx + 1];
    }

    private boolean[][] getIsPalindromeDPArray(String str) {
        int len = str.length();
        boolean[][] dp = new boolean[len + 1][len + 1];

        // Base case.
        // Given 0 char.
        dp[0][0] = true;
        // Given 1 char.
        for (int i = 1; i <= len; i++) {
            dp[i][i] = true;
        }
        // Given 2 consecutive chars.
        for (int i = 1; i <= len - 1; i++) {
            if (str.charAt(i - 1) == str.charAt(i)) {
                dp[i][i + 1] = true;
            }
        }

        // DP explore.
        for (int i = len; i >= 1; i--) {
            for (int j = i + 2; j <= len; j++) {
                // If the head and tail char is the same
                if (str.charAt(i - 1) == str.charAt(j - 1)) {
                    dp[i][j] = dp[i + 1][j - 1];
                }
            }
        }

        return dp;
    }
}