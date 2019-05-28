package com.myleetcode.backtracking.strobogrammatic_number_iii;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public int strobogrammaticInRange(String low, String high) {
        return strobogrammaticInRangeByBacktracking(low, high);
    }

    // TC: O(5^N), N is the high length, 5 is the # of charCharMap keys
    // SC: O(N)
    // https://leetcode.com/problems/strobogrammatic-number-iii/discuss/67378/Concise-Java-Solution/215155
    // very similar with 247. Strobogrammatic Number II
    // the first difference is: we dont need to store all results down, just count them
    // second is: the low and hight maybe have different length, so we check all leng strings bewteen low length and high length
    class Result{
        int count;
        public Result(){
            this.count = 0;
        }
    }

    private int strobogrammaticInRangeByBacktracking(String low, String high) {
        if(low == null || high == null){
            return 0;
        }

        Result ret = new Result();

        Map<Character, Character> charCharMap = new HashMap<>();
        charCharMap.put('0', '0');
        charCharMap.put('1', '1');
        charCharMap.put('6', '9');
        charCharMap.put('8', '8');
        charCharMap.put('9', '6');

        for(int len = low.length(); len <= high.length(); len++){
            char[] snippet = new char[len];
            backtracking(charCharMap, low, high, snippet, 0, len - 1, ret);
        }

        return ret.count;
    }

    public void backtracking(Map<Character, Character> charCharMap, String low, String high , char[] snippet, int left, int right, Result ret) {
        // if left(first part) idx larger than right(second part) idx, means we have get a string
        if (left > right) {
            String str = String.valueOf(snippet);

            // if str has equal length with low and smaller than it, return
            // is str has equal length with high and larger than it, return
            if ((str.length() == low.length() && str.compareTo(low) < 0) || (str.length() == high.length() && str.compareTo(high) > 0)) {
                return;
            }

            // this str is valid, count
            ret.count++;

            return;
        }

        for (char ch: charCharMap.keySet()) {
            // if snippet should have more than 1 digit, no leading 0
            if(snippet.length != 1 && left == 0 && ch == '0'){
                continue;
            }

            // center, ch must be 0 or 1 or 8
            if(left == right && ch != charCharMap.get(ch)){
                continue;
            }

            snippet[left] = ch;
            snippet[right] = charCharMap.get(ch);

            backtracking(charCharMap, low, high, snippet, left + 1, right - 1, ret);
        }
    }
}
