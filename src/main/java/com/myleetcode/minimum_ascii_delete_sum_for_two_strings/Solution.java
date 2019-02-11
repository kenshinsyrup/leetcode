package com.myleetcode.minimum_ascii_delete_sum_for_two_strings;


class Solution {
    public int minimumDeleteSum(String s1, String s2) {
        // 这个是TripleByte的问题
        // DP问题

        if (s1 == null || s2 == null) {
            return 0;
        }

        return minimumDeleteSumByDP(s1, s2);

    }

    private int minimumDeleteSumByDP(String s1, String s2) {
        //思路来源：https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/discuss/108823/Java-O(n)-Similar-idea-of-Edit-Distance-and-LC-524
        //https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/discuss/108811/JavaDP(With-Explanation)

        int s1Len = s1.length();
        int s2Len = s2.length();

        // dp[i][j] 与前面的dp和对应的删除掉的char有关。也就是和 dp[i-1][j] + s1.charAt(i-1), dp[i][j-1] + s2.charAt(j-1), dp[i-1][j-1] + s1.charAt(i-1) + s2.charAt(j - 1)，这三个情况有关。
        // 也就是说dp的值是 前面删掉的char sum + 前面的dp值。
        // 我们要遍历完整个string，那么i要取范围[0, s1.length]而不是[0, s1.length - 1]，因为我们一直在考虑charAt(i - 1)，j同理。这时候就有一个问题，我们要考虑i和j为0的情况特殊处理一下。同时，我们的dp的存储空间要是dp[s1.length+1][s2.length+1]。
        int[][] dp = new int[s1Len + 1][s2Len + 1];

        for (int i = 0; i <= s1Len; i++) {
            for (int j = 0; j <= s2Len; j++) {
                if (i == 0 || j == 0) {// i ==0 || j ==0是base case
                    if (i == 0 && j == 0) {
                        // i和j前面不存在char，也就没有要删除的char，前面的dp也不存在，所以dp[0][0]是0
                        dp[0][0] = 0;
                    } else if (i == 0) {
                        // i为0的时候，i前面不存char，但是j不为0，那么dp和j-1有关
                        dp[0][j] = dp[0][j - 1] + s2.charAt(j - 1);
                    } else {
                        dp[i][0] = dp[i - 1][0] + s1.charAt(i - 1);
                    }
                } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {// 两个normal case是正常情况
                    // normal case 1:
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // normal case 2:
                    dp[i][j] = Math.min(dp[i][j - 1] + s2.charAt(j - 1), dp[i - 1][j] + s1.charAt(i - 1));
                }
            }
        }

        return dp[s1Len][s2Len];

    }
}

