package com.myleetcode.backtracking.letter_combinations_of_a_phone_number;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public List<String> letterCombinations(String digits) {

        return letterCombinationsByBacktracking(digits);

    }

    // intuition: Backtracking
    // first need a map like {2:[a, b, c], 3:[d, e, f]...}
    // traverse all keys and build combination
    /*
    if make it simple, we could say:
    TC: O(2^N)
    SC: O(2^N).

    but to be strict, should know:
    TC: O(3^N * 4^M), where N is the number of digits in the input that maps to 3 letters (e.g. 2, 3, 4, 5, 6, 8) and M is the number of digits in the input that maps to 4 letters (e.g. 7, 9), and N+M is the total number digits in the input.

    SC: O(3^N * 4^M), since one has to keep O(3^N * 4^M) solutions.
    */
    private List<String> letterCombinationsByBacktracking(String digits){
        List<String> ret = new ArrayList<String>();

        // special case
        if(digits == null || digits.length() == 0){
            return ret;
        }

        // construct num -> letter map
        Map<String, String> numLettersMap = new HashMap<String, String>();
        numLettersMap.put("2", "abc");
        numLettersMap.put("3", "def");
        numLettersMap.put("4", "ghi");
        numLettersMap.put("5", "jkl");
        numLettersMap.put("6", "mno");
        numLettersMap.put("7", "pqrs");
        numLettersMap.put("8", "tuv");
        numLettersMap.put("9", "wxyz");

        backtracking(ret, numLettersMap, digits, 0, new StringBuilder());

        return ret;
    }

    private void backtracking(List<String> ret, Map<String, String> numLettersMap, String digits, int idx, StringBuilder sb){
        // 1. base, digits exhausted
        if(idx == digits.length()){
            ret.add(sb.toString());
            return;
        }

        // 2. try the letters according to current num
        // 2.1. get current letters string
        String curNumStr = digits.substring(idx, idx + 1);
        String curLettersStr = numLettersMap.get(curNumStr);
        if(curLettersStr == null){
            return;
        }

        // 2.2 try one char in the letters string
        for(int i = 0; i < curLettersStr.length(); i++){
            // append ith letter
            sb.append(curLettersStr.substring(i, i + 1));

            // explore next idx
            backtracking(ret, numLettersMap, digits, idx + 1, sb);

            // backtrack
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
