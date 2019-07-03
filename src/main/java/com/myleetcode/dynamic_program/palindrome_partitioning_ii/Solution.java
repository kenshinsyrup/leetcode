package com.myleetcode.dynamic_program.palindrome_partitioning_ii;

class Solution {
    public int minCut(String s) {
        // return minCutByDP(s);
        return minCutByDPII(s);
    }

    // according to CS5800, we could optimize the TC by reduce the time spending of check isPalindrome, we could preprocess the sting s, then use O(1) time to check if s[j-1:i-1] is palindrome
    // preprocess cost O(n^2), but check is O(1), cuts cost O(n^2), totally is O(n^2), better than O(n^3)
    private int minCutByDPII(String s){
        // 3 special case
        if(s == null || s.length() == 0){
            return 0;
        }
        if(s.length() == 1){
            return 0;
        }
        if(s.length() == 2){
            if(s.charAt(0) == s.charAt(1)){
                return 0;
            }
            return 1;
        }

        int len = s.length();

        // this is actually the 5. Longest Palindromic Substring problem
        // use dp to mark all substrings of str if it's a palindrome or not, then we could check if a substirng is palindrome with O(1) time
        boolean[][] isPalindrome = new boolean[len][len];

        // base case, single char is palindrome, two adjacent chars is the same, then is palindrome
        for(int i = 0; i < len; i++){
            // single char
            isPalindrome[i][i] = true;

            // two char
            if(i < len - 1 && s.charAt(i) == s.charAt(i + 1)){
                isPalindrome[i][i+1] = true;
            }
        }

        // normal case, our base is the diagnol line, so
        for(int i = len - 1; i >= 0; i--){
            for(int j = i + 2; j <= len - 1; j++){// we have processed j == i and j == i+1, so j from i+2
                if(s.charAt(i) == s.charAt(j)){
                    // dont out of boundary
                    if(i < len - 1 && j > 0){
                        isPalindrome[i][j] = isPalindrome[i + 1][j - 1];
                    }
                }
            }
        }

        int[] dp = new int[len + 1];

        // base case
        for(int i = 0; i <= len; i++){
            dp[i] = i;
        }

        // normal case
        for(int i = 1; i <= len; i++){ // 对于给定了长度为i的substring的情况，substring为S[0:i-1]
            for(int j = 1; j <= i; j++){// 尝试该substring的所有分割方法，取出最优方法即最小者.
                if(isPalindrome[j-1][i-1]){
                    dp[i] = Math.min(dp[i], dp[j - 1] + 1);
                }
            }
        }

        return dp[len] - 1; // 我们要的是dp被分配了整个S的时候的值即取dp[len], 我们用的算法是minmimum number of palindrom list, 所以cut number == list number - 1.
    }

    // intuition: CS5800, DP, Same as "Minimum Length of Palindromes List ", we could do this as the same, only difference is "the Minimum Cuts Number is the Minimum Length of Palindromes List - 1

    // dp[i]: !!!i means given i chars of S!!!, dp[i] means for the i, the list number of palindromes after the optimal break, so optimal break is dp[i]-1
    // dp[i] = min(dp[i], dp[j -1] +1), if S[j-1:i-1] is palindrome(the +1 means one palindrome S[j:i]), for i[1:n] j[1,i]

    // base case: dp[i] = i, for i[0:n], ie, every char itself being a palindrome

    // TC: O(n^3), cut costs O(n^2) but check cost O(n), so totally O(n^3)
    private int minCutByDP(String s){
        // special case
        if(s == null || s.length() == 0){
            return 0;
        }

        int len = s.length();
        int[] dp = new int[len + 1];

        // base case
        for(int i = 0; i <= len; i++){
            dp[i] = i;
        }

        // normal case
        for(int i = 1; i <= len; i++){
            for(int j = 1; j <= i; j++){
                if(isPalindromeByTwoPointers(j-1, i-1, s)){
                    dp[i] = Math.min(dp[i], dp[j - 1] + 1);
                }
            }
        }

        return dp[len] - 1; // 我们要的是dp被分配了整个S的时候的值即取dp[len], 我们用的算法是minmimum number of palindrom list, 所以cut number = list number - 1.
    }

    // TC: O(N)
    private boolean isPalindromeByTwoPointers(int leftIdx, int rightIdx, String s){

        while(leftIdx <= rightIdx){
            if(s.charAt(leftIdx) != s.charAt(rightIdx)){
                return false;
            }

            leftIdx++;
            rightIdx--;
        }

        return true;
    }
}
