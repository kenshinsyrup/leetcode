package com.myleetcode.binary_search.longest_duplicate_substring;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    public String longestDupSubstring(String S) {
        return longestDupSubstringByBSAndMap(S);
    }

    /*
    Goldman Sachs OA.
    thoughts:
    https://leetcode.com/problems/longest-duplicate-substring/discuss/327643/Step-by-step-to-understand-the-binary-search-solution

    code:
    https://leetcode.com/problems/longest-duplicate-substring/discuss/695214/JAVA-or-Binary-search-O(n-log-n)-average-with-Rabin-Karp-Algorithm

    possibleSubstringStartIdxWithHashCode works
    possibleSubstringStartIdxWithSubstring MLE

    This problem has two important parts:
    1 is find it could be solved based on Binary Search.
    2 is find the substring method is resource consuming, we could use hashcode replace it. But it's not important to implement the hashcode function, it's just the thoughts. Though possibleSubstringStartIdxWithSubstring is MLE, we should know this is the base code.
    */
    private String longestDupSubstringByBSAndMap(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }

        int len = str.length();
        int left = 1;
        int right = len;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (possibleSubstringStartIdxWithHashCode(str, mid) != -1) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        int start = possibleSubstringStartIdxWithHashCode(str, left - 1);
        if (start == -1) {
            return "";
        }
        return str.substring(start, start + left - 1);

    }

    /*
    MLE
    */
    public int possibleSubstringStartIdxWithSubstring(String str, int substringLen) {
        Set<String> strSet = new HashSet<>();
        for (int i = 0; i + substringLen <= str.length(); i++) {
            String substr = str.substring(i, i + substringLen);

            if (strSet.contains(substr)) {
                return i;
            }

            strSet.add(substr);
        }

        return -1;
    }

    public int possibleSubstringStartIdxWithHashCode(String str, int substringLen) {
        final long mod = (long) Math.pow(2, 32);

        long hashCode = 0;
        int num = 26;
        int len = str.length();

        for (int i = 0; i < substringLen; i++) {
            hashCode = (hashCode * num + (str.charAt(i) - 'a')) % mod;
        }

        HashSet<Long> set = new HashSet();
        set.add(hashCode);
        long global = 1;
        for (int i = 0; i < substringLen; ++i) {
            global = (global * num) % mod;
        }

        for (int start = 1; start < len - substringLen + 1; start++) {
            hashCode = (hashCode * num - (str.charAt(start - 1) - 'a') * global % mod + mod) % mod;
            hashCode = (hashCode + (str.charAt(start + substringLen - 1) - 'a')) % mod;

            if (set.contains(hashCode)) {
                return start;
            }

            set.add(hashCode);
        }

        return -1;
    }
}
