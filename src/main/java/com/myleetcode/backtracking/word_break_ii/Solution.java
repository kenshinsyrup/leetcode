package com.myleetcode.backtracking.word_break_ii;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        return wordBreakByBacktracking(s, wordDict);
    }

    // TC: < O(N!)
    // https://leetcode.com/problems/word-break-ii/discuss/44167/My-concise-JAVA-solution-based-on-memorized-DFS
    // intuition: seems like 139. Word Break, but need all combination, so we could use backtracking to get them
    private List<String> wordBreakByBacktracking(String str, List<String> wordDict){
        if(str == null || str.length() == 0 || wordDict == null){
            return null;
        }

        Map<String, List<String>> strWordsMap = new HashMap<>();
        return backtracking(str, wordDict, strWordsMap);

    }

    private List<String> backtracking(String str, List<String> wordDict, Map<String, List<String>> strWordsMap){
        if(strWordsMap.containsKey(str)){
            return strWordsMap.get(str);
        }

        List<String> curWordsList = new ArrayList<>();
        if(str.length() == 0){
            curWordsList.add("");//!!! important part, this means we have reached the end of str(original). with the !!! for loop, we could differentiate condition that the last part of str is not in the dict.
            return curWordsList;
        }

        for(String word: wordDict){
            if(str.startsWith(word)){
                String nextStr = str.substring(word.length(), str.length());
                List<String> nextStrWordsList = backtracking(nextStr, wordDict, strWordsMap);
                // !!!
                for(String nextStrWord: nextStrWordsList){
                    if(nextStrWord.equals("")){
                        curWordsList.add(word);
                    }else{
                        curWordsList.add(word + " " + nextStrWord);
                    }
                }
            }
        }

        strWordsMap.put(str, curWordsList);

        return curWordsList;

    }
}
