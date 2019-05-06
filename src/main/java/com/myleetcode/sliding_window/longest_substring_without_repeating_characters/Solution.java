package com.myleetcode.sliding_window.longest_substring_without_repeating_characters;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        return lenghtOfLongestSubstringBySlidingWindow(s);
    }


    // TC: O(N)
    // SC: O(min(M, N)), where M is the longest length of Set, actually the Set's length is at most 26
    // intuition: Sliding Window
    private int lenghtOfLongestSubstringBySlidingWindow(String str){
        if(str == null || str.length() == 0){
            return 0;
        }

        int maxLen = 0;

        Set<Character> charSet = new HashSet<>();

        int len = str.length();
        int left = 0;
        int right = 0;
        while(left <= right && right < len){
            char ch = str.charAt(right);
            // valid, expand
            if(!charSet.contains(ch)){
                // !!! must first caculate the length of current string, then make right++(of course, otherwise your lenght is not correct)
                maxLen = Math.max(maxLen, right - left + 1);

                charSet.add(ch);
                right++;
            }else{
                // repeated, shrink
                charSet.remove(str.charAt(left));
                left++;
            }
        }

        return maxLen;
    }

    public int lengthOfLongestSubstringOriginal(String s) {
        char[] charArray = s.toCharArray();
        int max = 0;
        for (int i = 0; i < charArray.length; i++) {
            Map<Character, Boolean> checker = new HashMap<>();
            checker.put(charArray[i], true);
            int count = 1;
            for (int j = i + 1; j < charArray.length; j++) {
                if (checker.containsKey(charArray[j])) {
                    break;
                }
                count++;
                checker.put(charArray[j], true);
            }
            if (count > max) {
                max = count;
            }
        }
        return max;
    }

    // 以下来自Leetcode: https://leetcode.com/problems/longest-substring-without-repeating-characters/
    // Approach 1: Brute Force
    // Time complexity : O(n^3)

    public int lengthOfLongestSubstringBruteForce(String s) {
        int n = s.length();
        int ans = 0;
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j <= n; j++)
                if (allUnique(s, i, j))
                    ans = Math.max(ans, j - i);
        return ans;
    }

    public boolean allUnique(String s, int start, int end) {
        Set<Character> set = new HashSet<>();
        for (int i = start; i < end; i++) {
            Character ch = s.charAt(i);
            if (set.contains(ch))
                return false;
            set.add(ch);
        }
        return true;
    }

    // Approach 2: Sliding Window
    // O(n)
    public int lengthOfLongestSubstringSlidingWindow(String s) {
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            // try to extend the range [i, j]
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            } else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;
    }

    // Approach 3: Sliding Window Optimized
    // O(n)
    public int lengthOfLongestSubstringSlidingWindowOptimized(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }

    // 更新下Approach 3的写法,让map直接保存index更容易懂
    public int lengthOfLongestSubstringSlidingWindowOptimized2(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        int i = 0;
        for (int j = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                // 在j右移的过程中出现重复的character时，将重复的character所对应的index视作prev-j，那么有两种情况：
                // 1、i < prev-j, 那么将i移动到prev-j + 1处即可
                // 2、i >= prev-j, 那么i不需要移动
                // 注意，是获取map中重复字符对应的index而不是重复字符本身
                i = Math.max(map.get(s.charAt(j)) + 1, i);
            }

            // 无论是否j处character是重复的，都需要更新子串长度信息。长度是由j和i来确定的，只要j移动，长度就要更新。
            // i的位置会根据重复与否进行更新。如果仅在j右移且非重复的时候更新长度，"tmmzuxt"这样的字符串最后一个t和第一个t重复了，
            // 但实际i在第二个m处，子串Sij中t是仅有一个的。
            // map中存储的是character及对应的index，那么长度就是j-i+1
            ans = Math.max(ans, j - i + 1);

            // 无论是否j处的character是重复的，都需要更新map
            map.put(s.charAt(j), j);
        }
        return ans;
    }

}