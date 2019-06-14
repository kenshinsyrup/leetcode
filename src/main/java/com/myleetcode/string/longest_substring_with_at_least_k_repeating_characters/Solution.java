package com.myleetcode.string.longest_substring_with_at_least_k_repeating_characters;

class Solution {
    public int longestSubstring(String s, int k) {
        return longestSubstringBySlidingWindow(s, k);
    }

    // intuition: Sliding Window problem
    // TC: O(26*N) => O(N), N is the length of str
    // SC: O(26)
    // https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/discuss/87739/Java-Strict-O(N)-Two-Pointer-Solution/92538
    // https://leetcode.com/problems/minimum-window-substring/discuss/26808/here-is-a-10-line-template-that-can-solve-most-substring-problems
    // the hard part is, this problem dont specify the numUniqueTarget we need to contain in the substring, it just give the repeating times K we need to meet. So we should try all possibilities: 1 to 26.
    private int longestSubstringBySlidingWindow(String str, int k){
        if(str == null || str.length() < k){
            return 0;
        }
        if(k < 0){
            return 0;
        }

        int ret = 0;
        for(int i = 1; i <= 26; i++){
            int ls = longestSubstringWithNUniqueChars(str, k, i);
            ret = Math.max(ret, ls);
        }

        return ret;
    }

    private int longestSubstringWithNUniqueChars(String str, int k, int n){
        int len = str.length();
        int left = 0;
        int right = 0;

        int[] charNumMap = new int[26];
        int uniqueCharNum = 0;
        int numNoLessThanK = 0;
        int maxLen = 0;

        while(right < len){
            char curChar = str.charAt(right);
            charNumMap[curChar - 'a']++;

            // 1. first meet this char, uniqueCharNum++
            if(charNumMap[curChar - 'a'] == 1){
                uniqueCharNum++;
            }
            // if num of this char is k, record
            if(charNumMap[curChar - 'a'] == k){
                numNoLessThanK++;
            }

            // 2. if uniqueCharNum is more than we need, shrink the window
            while(uniqueCharNum > n && left <= right){
                char leftChar = str.charAt(left);
                charNumMap[leftChar - 'a']--;

                // if num of this char is k-1, means after this move of left, this char's num will less than k, so we decrease the numNoLessThanK
                if(charNumMap[leftChar - 'a'] == (k - 1)){
                    numNoLessThanK--;
                }
                // if num of this char is 0, means after this move of left, this char's num will less than 0, so uniqueCharNum--
                if(charNumMap[leftChar - 'a'] == 0){
                    uniqueCharNum--;
                }

                // move left pointer
                left++;
            }

            // 3. caculate length
            // if we found a string where the number of unique chars equals our target
            // and all those chars are repeated at least K times then update max
            if (uniqueCharNum == n && uniqueCharNum == numNoLessThanK){
                maxLen = Math.max(right - left + 1, maxLen);
            }

            // move right pointer
            right++;
        }

        return maxLen;
    }
}
