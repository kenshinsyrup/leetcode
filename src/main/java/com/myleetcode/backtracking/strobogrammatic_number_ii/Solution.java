package com.myleetcode.backtracking.strobogrammatic_number_ii;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public List<String> findStrobogrammatic(int n) {
        // return findStrobogrammaticByBacktracking(n);
        return findStrobogrammaticByBacktrackingAndArray(n);
    }

    // use char[] instead of StringBuilder to store the snippet during the process, could inprove the performance
    // use charCharMap.keySet, so we dont need the charArr
    // https://leetcode.com/problems/strobogrammatic-number-ii/discuss/67273/My-Concise-JAVA-Solution-using-DFS
    private List<String> findStrobogrammaticByBacktrackingAndArray(int n){
        List<String> ret = new ArrayList<>();

        if(n <= 0){
            return ret;
        }

        // 180 degree
        Map<Character, Character> charCharMap = new HashMap<>();
        charCharMap.put('0', '0');
        charCharMap.put('1', '1');
        charCharMap.put('6', '9');
        charCharMap.put('8', '8');
        charCharMap.put('9', '6');

        // if n is odd, make it a even and remember
        boolean isOdd = false;
        if(n % 2 != 0){
            isOdd = true;
        }

        // center chars
        char[] charCenter = {'0', '1', '8'};

        char[] snippetCharArr = new char[n];

        backtrackingWithArray(charCenter, charCharMap, ret, snippetCharArr, n, 0, isOdd);

        return ret;
    }

    private void backtrackingWithArray(char[] charCenter, Map<Character, Character> charCharMap, List<String> ret, char[] snippetCharArr, int num, int idx, boolean isOdd){
        // if snippesnippetCharArr is a valid result
        if(idx == num / 2){
            if(isOdd){
                for(char ch: charCenter){
                    snippetCharArr[idx] = ch;
                    ret.add(String.valueOf(snippetCharArr));
                }
            }else{
                ret.add(String.valueOf(snippetCharArr));
            }

            return;
        }

        // process, combine
        // every time we try to pick a char from charArr
        for(char ch: charCharMap.keySet()){
            // if idx is 0, we dont put '0' in because we dont need leading 0
            if(ch == '0' && idx == 0){
                continue;
            }

            // try
            // put char to its pos
            snippetCharArr[idx] = ch;
            snippetCharArr[num - 1 - idx] = charCharMap.get(ch);

            backtrackingWithArray(charCenter, charCharMap, ret, snippetCharArr, num, idx + 1, isOdd);
        }
    }


    // TC: O(5^N)), 5 is the # of Strobogrammatic Char
    // SC: O(N)
    private List<String> findStrobogrammaticByBacktracking(int n){
        List<String> ret = new ArrayList<>();

        if(n <= 0){
            return ret;
        }

        // 180 degree
        Map<Character, Character> charCharMap = new HashMap<>();
        charCharMap.put('0', '0');
        charCharMap.put('1', '1');
        charCharMap.put('6', '9');
        charCharMap.put('8', '8');
        charCharMap.put('9', '6');

        // if n is odd, make it a even and remember
        boolean isOdd = false;
        if(n % 2 != 0){
            isOdd = true;
        }

        // chars
        char[] charArr = new char[]{'0', '1', '6', '8', '9'};
        // center chars
        char[] charCenter = {'0', '1', '8'};

        StringBuilder sbHead = new StringBuilder();// first part of final string
        StringBuilder sbTail = new StringBuilder();// second part of final string

        backtracking(charArr, charCenter, charCharMap, ret, sbHead, sbTail, n/2, isOdd);

        return ret;
    }

    private void backtracking(char[] charArr, char[] charCenter, Map<Character, Character> charCharMap, List<String> ret, StringBuilder sbHead, StringBuilder sbTail, int num, boolean isOdd){
        // if valid
        if(num == 0){
            // !!!because we put char to tail of sbHead and char to tail of sbTail, to make the final string correct, we should reverse the sbTail
            sbTail = sbTail.reverse();

            // !!!if is Odd, we should try to insert char in charCenter to every string
            if(isOdd){
                for(char ch: charCenter){
                    ret.add(sbHead.toString() + ch + sbTail.toString());
                }
            }else{
                ret.add(sbHead.toString() + sbTail.toString());
            }

            // !!!must re-reverse the sbTail to make sure the consistent. otherwise, after we reverse the sbTail, it's next try will use the reversed sbTail which is incorrect
            sbTail = sbTail.reverse();

            return;
        }

        // process, combine
        // every time we try to pick a char from charArr
        for(int i = 0; i < charArr.length; i++){
            // if sb lenght is 0, we dont put '0' in because we dont need leading 0
            if(charArr[i] == '0' && sbHead.length() == 0){
                continue;
            }

            // try
            // put char to sbHead, and put the corresponding char to sbTail
            sbHead.append(charArr[i]);
            sbTail.append(charCharMap.get(charArr[i]));
            num--;

            backtracking(charArr, charCenter, charCharMap, ret, sbHead, sbTail, num, isOdd);

            // back
            sbHead.deleteCharAt(sbHead.length() - 1);
            sbTail.deleteCharAt(sbTail.length() - 1);
            num++;
        }
    }
}
