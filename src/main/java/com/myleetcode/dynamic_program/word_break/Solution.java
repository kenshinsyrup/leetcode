package com.myleetcode.dynamic_program.word_break;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        // return wordBreakByBacktracking(s, wordDict); // TLE
        // return wordBreakByBacktrackingWithMemo(s, wordDict); // TLE

        // return wordBreakByRecursion(s, wordDict); // TLE
        return wordBreakByRecursionWithMemo(s, wordDict); // Top Down

//        return wordBreakByDP(s, wordDict); // DP
    }

    /*
    intuition:
    Recursion with memo: we use a memo map to remember the intermediate result

    Backtracking on wordDict is bad here(TLE actually) because wordDict is far more larger than given String s.
    Recursion on String s is TLE but we could optimize it with memo to avoid redundant calculation, this is a top-down dp solution.

    TC: O(N^3 + N*M)
    SC: O(N + M)
    */
    private boolean wordBreakByRecursionWithMemo(String str, List<String> wordDict){
        if(str == null || str.length() == 0){
            return true;
        }
        if(wordDict == null || wordDict.size() == 0){
            return false;
        }

        // O(N * M)
        Set<String> dict = new HashSet<>();
        for(String subStr: wordDict){
            if(str.indexOf(subStr) != -1){
                dict.add(subStr);
            }
        }

        Map<Integer, Boolean> memo = new HashMap<>();

        return recursionWithMemo(str, 0, dict, memo);
    }

    private boolean recursionWithMemo(String str, int curIdx, Set<String> dict, Map<Integer, Boolean> memo){
        if(memo.containsKey(curIdx)){
            return memo.get(curIdx);
        }

        if(curIdx >= str.length()){
            return true;
        }

        for(int i = curIdx; i < str.length(); i++){
            if(dict.contains(str.substring(curIdx, i + 1))){
                if(recursionWithMemo(str, i + 1, dict, memo)){
                    memo.put(i + 1, true);
                    return true;
                }else{
                    memo.put(i + 1, false);
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
    private boolean wordBreakByRecursion(String str, List<String> wordDict){
        if(str == null || str.length() == 0){
            return true;
        }
        if(wordDict == null || wordDict.size() == 0){
            return false;
        }

        // O(N * M)
        Set<String> dict = new HashSet<>();
        for(String subStr: wordDict){
            if(str.indexOf(subStr) != -1){
                dict.add(subStr);
            }
        }

        return recursion(str, 0, dict);
    }

    private boolean recursion(String str, int curIdx, Set<String> dict){
        // base case
        if(curIdx >= str.length()){
            return true;
        }

        // if [curIdx:i] is in dict, we do recursion on [i+1:len-1]
        int len = str.length();
        for(int i = curIdx; i < len; i++){
            if(dict.contains(str.substring(curIdx, i + 1))){ // !!! i+1
                if(recursion(str, i + 1, dict)){
                    return true;
                }
            }
        }

        return false;
    }

    /*
    intuition:
    Backtracking with Memo, top-down dp

    Say M is the size of wordDict.
    TC: O(N * M^2), TLE
    SC: (N + M)
    */
    private boolean wordBreakByBacktrackingWithMemo(String str, List<String> wordDict){
        if(str == null || str.length() == 0){
            return true;
        }
        if(wordDict == null || wordDict.size() == 0){
            return false;
        }

        // O(N * M)
        Set<String> dict = new HashSet<>();
        for(String subStr: wordDict){
            if(str.indexOf(subStr) != -1){
                dict.add(subStr);
            }
        }

        Map<String, Boolean> memo = new HashMap<>();
        return recursionWithMemo(dict, new StringBuilder(), memo, str);
    }

    // O(N * M^2), consider the indexOf as O(N)
    private boolean recursionWithMemo(Set<String> dict, StringBuilder sb, Map<String, Boolean> memo, String str){
        String sbStr = sb.toString();
        if(memo.containsKey(sbStr)){
            return memo.get(sbStr);
        }

        if(sbStr.length() >= str.length()){
            if(sbStr.equals(str)){
                memo.put(sbStr, true);
                return true;
            }
            return false;
        }

        int size = dict.size();
        for(String curStr: dict){
            int start = sb.length();
            sb.append(curStr);

            if(recursionWithMemo(dict, sb, memo, str)){
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
    private boolean wordBreakByBacktracking(String str, List<String> wordDict){
        if(str == null || str.length() == 0){
            return true;
        }
        if(wordDict == null || wordDict.size() == 0){
            return false;
        }

        return backtracking(wordDict, new StringBuilder(), str);

    }

    private boolean backtracking(List<String> wordDict, StringBuilder sb, String str){
        if(sb.length() >= str.length()){
            if(sb.toString().equals(str)){
                return true;
            }
            return false;
        }

        int size = wordDict.size();
        for(int i = 0; i < size; i++){
            String curStr = wordDict.get(i);
            if(str.indexOf(curStr) == -1){
                continue;
            }

            int start = sb.length();
            sb.append(curStr);

            if(backtracking(wordDict, sb, str)){
                return true;
            }

            sb.delete(start, sb.length());
        }

        return false;
    }

    private boolean wordBreakByDP(String s, List<String> wordDict){
        if(s == null || s.length() == 0){
            return true;
        }
        if(wordDict == null || wordDict.size() == 0){
            return false;
        }

        int sLen = s.length();

        boolean[] dp = new boolean[sLen + 1];

        // base case
        dp[0] = true;

        // i是dp用来存储给 string中第i个char的时候 的结果。由于要使用substring，其是0based的，所以找[0,i]的不包含子串时，可以直接用j从[0,i)来找。这样，subStr就正好是substring(j,i)。然后dp[i]就依赖于 substr是否存在于dict 以及 dp[j]是否breakable。
        // 外层循环和dp的i保持一致，用来指代1based的情况下，第1到第i个(全包含)字符串对应的dp解。内层循环中，遍历外层循环的字符串的子串，也就是substring(j,i)。dp[0]作为base我们已经设定为了true。
        for(int i = 1; i <=sLen; i++){
            for(int j = 0; j < i; j++){
                String subStr = s.substring(j, i);
                if(dp[j] && wordDict.contains(subStr)){
                    dp[i] = true;
                }
            }
        }

        return dp[sLen];

    }
}