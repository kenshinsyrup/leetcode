package com.myleetcode.backtracking.palindrome_permutation_ii;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public List<String> generatePalindromes(String s) {
        // special case
        if(s == null || s.length() == 0){
            return new ArrayList<String>();
        }

        return generatePalindromesByBacktracking(s);
    }

    private List<String> generatePalindromesByBacktracking(String s){
        // first check, if could, get a fullfilled map
        Map<Character, Integer> strMap = new HashMap<>();
        if(!canPalindrome(s, strMap)){
            return new ArrayList<String>();
        }

        // then, to avoid LME, should only use half chars to generate half str of palindrome and then copy reverse to get another half.

        // get half num chars
        List<Character> charList = new ArrayList<Character>();
        // remeber one of the odd char, this one must in the middle of palindrome.(remains are also even so could store to the charList.)
        String midStr = "";
        for(char k: strMap.keySet()){
            if(strMap.get(k) % 2 == 1){
                midStr += k; // remember one of the odd char, eg, "aaa", we remember one "a", and store 2 "a" to charList, ie strMap.get(k) / 2
            }

            for(int i = 0; i < strMap.get(k) / 2; i++){
                charList.add(k);
            }
        }

        // now we have source list, we could start backtracking to get all permutation
        List<String> ret = new ArrayList<>();
        StringBuilder sb = new StringBuilder(); // like temp, store mid-result.
        boolean[] visiting = new boolean[charList.size()]; // mark status in the traversing

        getPermutation(charList, midStr, visiting, ret, sb);

        return ret;

    }

    private void getPermutation(List<Character> charList, String midStr, boolean[] visiting, List<String> ret, StringBuilder sb){
        // we only have even nums of char in charList, so when get a permutation, sb.size() equals to charList.size()
        if(sb.length() == charList.size()){
            String firstHalf = sb.toString();
            String secondHalf = sb.reverse().toString();
            // must reverse back sb!!!
            sb.reverse();
            ret.add(firstHalf + midStr + secondHalf);
            return;
        }

        for(int i = 0; i < charList.size(); i++){
            // check to avoid duplicates
            // 如果当前char在被访问，那么不要再添加
            if(visiting[i]){
                continue;
            }
            // 如果当前char么有在被访问，但是他和前面的相同，而前面的在被访问，那么不要添加
            if(i > 0 && (charList.get(i) == charList.get(i - 1)) && visiting[i - 1]){
                continue;
            }
            // 注意这个判断，很多为了追求性能的写法会写作i > 0 && (charList.get(i) == charList.get(i - 1)) && !visiting[i - 1],检查的依据是如果当前的和前面的一样，且前面的没有被使用，那么当前的也不应该被使用，这样在多个连续重复(重复的一般是连续的在backtrack代码里，因为很多时候预处理包含了sort)的时候可以更多的continue从而减少下面的backtrack部分。但是这样的写法稍微有点难理解，不够intuitive，暂时可以按照现在的写法写。
            // 按照现在的写法在leetcode大概是 RC击败5% Java, RC是约9000ms; SC击败6%, SC是42.
            // 改成!visiting[i - 1]的写法则为 RC击败90% Java, RC是约2ms; SC击败60%, SC是37.

            // try go forward
            // status
            visiting[i] = true;
            // add
            sb.append(charList.get(i));
            // forward
            getPermutation(charList, midStr, visiting, ret, sb);

            // back track
            // delete
            sb.deleteCharAt(sb.length() - 1);
            // status
            visiting[i] = false;
        }

    }


    // first check if this string could become palindrome permutation, ie, the 266. Palindrome Permutation problem
    private boolean canPalindrome(String s, Map<Character, Integer> strMap){
        int num = 0;
        for(int i = 0; i < s.length(); i++){
            num = strMap.getOrDefault(s.charAt(i), 0);
            strMap.put(s.charAt(i), num + 1);
        }

        int oddCount = 0;
        for(int k: strMap.values()){
            if(k % 2 == 1){
                oddCount++;
                if(oddCount > 1){
                    return false;
                }
            }
        }

        return true;
    }
}