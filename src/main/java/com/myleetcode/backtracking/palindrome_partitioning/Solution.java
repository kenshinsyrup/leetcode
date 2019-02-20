package com.myleetcode.backtracking.palindrome_partitioning;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<List<String>> partition(String s) {
//        https://leetcode.com/problems/subsets/discuss/27281/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)

        List<List<String>> ans = new ArrayList<List<String>>();

        if (s == null) {
            return ans;
        }

        List<String> temp = new ArrayList<String>();

        partitionByBacktracking(s, ans, temp, 0);

        return ans;
    }

    private void partitionByBacktracking(String s, List<List<String>> ans, List<String> temp, int start) {
        if (start >= s.length()) {
            ans.add(new ArrayList(temp));
        } else {
            for (int i = start; i < s.length(); i++) {
                // add palindrome
                String substr = s.substring(start, i + 1);
                if (isPalindrome(substr)) {
                    temp.add(substr);

                    partitionByBacktracking(s, ans, temp, i + 1);

                    temp.remove(temp.size() - 1);
                }
            }
        }
    }

    private boolean isPalindrome(String s) {
        int start = 0;
        int end = s.length() - 1;

        while (start < end) {
            if (s.charAt(start) != s.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }

        return true;
    }
}
