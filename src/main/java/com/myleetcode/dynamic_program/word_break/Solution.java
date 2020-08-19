package com.myleetcode.dynamic_program.word_break;

import java.util.*;

public class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        // return wordBreakByBacktracking(s, wordDict);
        // return wordBreakByBacktrackingWithMemo(s, wordDict);

        // return wordBreakByRecursion(s, wordDict);
        return wordBreakByRecursionWithMemo(s, wordDict);
    }

    /*
    intuition:
    Recursion with memo: we use a memo map to remember the intermediate result

    Backtracking on wordDict is bad here(TLE actually) because wordDict is far more larger than given String s.
    Recursion on String s is TLE but we could optimize it with memo to avoid redundant calculation, this is a top-down dp solution.

    TC: O(N^3 + N*M)
    SC: O(N + M)
    */
    private boolean wordBreakByRecursionWithMemo(String str, List<String> wordDict) {
        if (str == null || str.length() == 0) {
            return true;
        }
        if (wordDict == null || wordDict.size() == 0) {
            return false;
        }

        // O(N * M), for early break.
        Set<String> dict = new HashSet<>();
        for (String subStr : wordDict) {
            if (str.indexOf(subStr) != -1) {
                dict.add(subStr);
            }
        }

        Map<Integer, Boolean> memo = new HashMap<>();

        return recursionWithMemo(str, 0, dict, memo);
    }

    private boolean recursionWithMemo(String str, int curIdx, Set<String> dict, Map<Integer, Boolean> memo) {
        if (curIdx >= str.length()) {
            return true;
        }

        if (memo.containsKey(curIdx)) {
            return memo.get(curIdx);
        }

        for (int i = curIdx; i < str.length(); i++) {
            if (dict.contains(str.substring(curIdx, i + 1))) {
                if (recursionWithMemo(str, i + 1, dict, memo)) {
                    return true;
                }
            }
        }

        memo.put(curIdx, false);

        return false;
    }

    /*
    intuition:
    Naive Recursion: on given String str, at idx, we break the str to two parts, [start:idx] and [idx+1:len-1], then if substr1 is in wordDict and substr2 we do recursion wordBreak on it and if it returns true, means we find a combination.

    TC: O(2^N + N*M)
    SC: O(N + M)
    */
    private boolean wordBreakByRecursion(String str, List<String> wordDict) {
        if (str == null || str.length() == 0) {
            return true;
        }
        if (wordDict == null || wordDict.size() == 0) {
            return false;
        }

        // O(N * M)
        Set<String> dict = new HashSet<>();
        for (String subStr : wordDict) {
            if (str.indexOf(subStr) != -1) {
                dict.add(subStr);
            }
        }

        return recursion(str, 0, dict);
    }

    private boolean recursion(String str, int curIdx, Set<String> dict) {
        // base case
        if (curIdx >= str.length()) {
            return true;
        }

        // if [curIdx:i] is in dict, we do recursion on [i+1:len-1]
        int len = str.length();
        for (int i = curIdx; i < len; i++) {
            if (dict.contains(str.substring(curIdx, i + 1))) { // !!! i+1
                if (recursion(str, i + 1, dict)) {
                    return true;
                }
            }
        }

        return false;
    }

    /*
    下面两种做法是使用dict中的词去穷举组合，看有没有和str相同的，不好。
    */
    /*
    intuition:
    Backtracking with Memo, top-down dp

    Say M is the size of wordDict.
    TC: O(N * M^2), TLE
    SC: (N + M)
    */
    private boolean wordBreakByBacktrackingWithMemo(String str, List<String> wordDict) {
        if (str == null || str.length() == 0) {
            return true;
        }
        if (wordDict == null || wordDict.size() == 0) {
            return false;
        }

        // O(N * M)
        Set<String> dict = new HashSet<>();
        for (String subStr : wordDict) {
            if (str.indexOf(subStr) != -1) {
                dict.add(subStr);
            }
        }

        Map<String, Boolean> memo = new HashMap<>();
        return recursionWithMemo(dict, new StringBuilder(), memo, str);
    }

    // O(N * M^2), consider the indexOf as O(N)
    private boolean recursionWithMemo(Set<String> dict, StringBuilder sb, Map<String, Boolean> memo, String str) {
        String sbStr = sb.toString();
        if (memo.containsKey(sbStr)) {
            return memo.get(sbStr);
        }

        if (sbStr.length() >= str.length()) {
            if (sbStr.equals(str)) {
                memo.put(sbStr, true);
                return true;
            }
            return false;
        }

        int size = dict.size();
        for (String curStr : dict) {
            int start = sb.length();
            sb.append(curStr);

            if (recursionWithMemo(dict, sb, memo, str)) {
                return true;
            }

            memo.put(sb.toString(), false);
            sb.delete(start, sb.length());
        }

        return false;

    }

    /*
    intuition:
    Naive Solution: backtracking to try all combinations.

    TC: O(2^M), TLE
    SC: O(N + M)
    */
    private boolean wordBreakByBacktracking(String str, List<String> wordDict) {
        if (str == null || str.length() == 0) {
            return true;
        }
        if (wordDict == null || wordDict.size() == 0) {
            return false;
        }

        return backtracking(wordDict, new StringBuilder(), str);

    }

    private boolean backtracking(List<String> wordDict, StringBuilder sb, String str) {
        if (sb.length() >= str.length()) {
            if (sb.toString().equals(str)) {
                return true;
            }
            return false;
        }

        int size = wordDict.size();
        for (int i = 0; i < size; i++) {
            String curStr = wordDict.get(i);
            if (str.indexOf(curStr) == -1) {
                continue;
            }

            int start = sb.length();
            sb.append(curStr);

            if (backtracking(wordDict, sb, str)) {
                return true;
            }

            sb.delete(start, sb.length());
        }

        return false;
    }
}