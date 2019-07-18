package com.myleetcode.sliding_window.find_all_anagrams_in_a_string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        return findAnagramsBySlidingWindowAndMap(s, p);
    }

    // intuition: Sliding Window with Map
    // template: https://leetcode.com/problems/permutation-in-string/discuss/102598/Sliding-Window-in-Java-very-similar-to-Find-All-Anagrams-in-a-String
    private List<Integer> findAnagramsBySlidingWindowAndMap(String sStr, String pStr){
        List<Integer> ret = new ArrayList<>();
        if(sStr == null || pStr == null){
            return ret;
        }

        int sLen = sStr.length();
        int pLen = pStr.length();
        if(sLen < pLen){
            return ret;
        }

        // get the ch->num map of sStr
        Map<Character, Integer> chNumMap = new HashMap<>();
        for(char ch: pStr.toCharArray()){
            chNumMap.put(ch, chNumMap.getOrDefault(ch, 0) + 1);
        }
        // get the size of the map
        int count = chNumMap.size();

        // sliding window
        int leftP = 0;
        int rightP = 0;
        while(rightP < sLen){
            // expand window and record char we met
            char curCh = sStr.charAt(rightP);
            if(chNumMap.containsKey(curCh)){
                chNumMap.put(curCh, chNumMap.get(curCh) - 1);
                // if num is 0, means we have enough current char in sStr in cur range
                if(chNumMap.get(curCh) == 0){
                    count--;
                }
            }

            // if all ch in pStr is met in cur range, caculate
            while(count == 0){
                // check first, if len equals, find one
                if(rightP - leftP + 1 == pLen){
                    ret.add(leftP);
                }

                // then shrink
                char leftCh = sStr.charAt(leftP);
                if(chNumMap.containsKey(leftCh)){
                    chNumMap.put(leftCh, chNumMap.get(leftCh) + 1);
                    // if num is larger than 0, count++, means cur range dont have enough char
                    if(chNumMap.get(leftCh) > 0){
                        count++;
                    }
                }

                // move forward leftP
                leftP++;
            }

            // move forward rightP
            rightP++;
        }

        return ret;

    }
}
