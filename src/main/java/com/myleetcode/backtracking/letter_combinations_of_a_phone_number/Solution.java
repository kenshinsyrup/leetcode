package com.myleetcode.backtracking.letter_combinations_of_a_phone_number;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    public List<String> letterCombinations(String digits) {
        // first need a map like {2:[a, b, c], 3:[d, e, f]...}
        // traverse all keys and build combination

        List<String> ans = new ArrayList<String>();

        // special case
        if(digits == null || digits.length() == 0){
            return ans;
        }

        // construct num -> letter map
        Map<String, String> numLetters = new HashMap<String, String>();
        numLetters.put("2", "abc");
        numLetters.put("3", "def");
        numLetters.put("4", "ghi");
        numLetters.put("5", "jkl");
        numLetters.put("6", "mno");
        numLetters.put("7", "pqrs");
        numLetters.put("8", "tuv");
        numLetters.put("9", "wxyz");

        letterCombinationsByBacktracking(ans, numLetters, digits, "");

        return ans;

    }

    private void letterCombinationsByBacktracking(List<String> ans, Map<String, String> numLetters, String digits, String temp){
        // 加入ans的条件
        if(digits.length() == 0){
            ans.add(temp);
        }else{
            // 用掉第一个digit
            String digit = digits.substring(0, 1);
//             找到这个digit对应的letters
            String letters = numLetters.get(digit);
            for(int i = 0; i < letters.length(); i++){
                // 遍历当前num对应的letters，每次取一个letter
                String letter = letters.substring(i, i + 1);
//                 temp加入当前letter，向下深入时减去digits已经用过的一个digit
                temp += letter;
                letterCombinationsByBacktracking(ans, numLetters, digits.substring(1), temp);
//                 temp恢复
                temp = temp.substring(0, temp.length() - 1);
            }
        }
    }
}
