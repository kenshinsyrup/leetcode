package com.myleetcode.sliding_window.permutation_in_string;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean checkInclusion(String s1, String s2) {
        return checkInclusionBySlidingWindow(s1, s2);
    }

    /*
    出错点：
    1 审题：要求的是substring的permutation和s1相同就可以，不是substring和s1相同
    */
    // intuition: Sliding Window
    // TC: O(N + M), N is len of s1, M is len of s2
    // SC: O(M)
    // very template problem:
    // https://leetcode.com/problems/permutation-in-string/discuss/102598/Sliding-Window-in-Java-very-similar-to-Find-All-Anagrams-in-a-String
    // https://leetcode.com/problems/subarrays-with-k-different-integers/discuss/235002/One-code-template-to-solve-all-of-these-problems!
    private boolean checkInclusionBySlidingWindow(String str1, String str2){
        if(str1 == null && str2 == null){
            return true;
        }
        if(str1 == null || str2 == null){
            return false;
        }

        int len1 = str1.length();
        int len2 = str2.length();
        if(len1 > len2){
            return false;
        }

        Map<Character, Integer> chNumMap = new HashMap<>();
        for(char ch: str1.toCharArray()){
            chNumMap.put(ch, chNumMap.getOrDefault(ch, 0) + 1);
        }

        int count = chNumMap.size();
        int leftP = 0;
        int rightP = 0;
        while(rightP < len2){
            char curCh = str2.charAt(rightP);

            if(chNumMap.containsKey(curCh)){
                chNumMap.put(curCh, chNumMap.get(curCh) - 1);
                if(chNumMap.get(curCh) == 0){
                    count--;
                }
            }

            // count == 0 means all ch in str1 meet, check cur substring length and shink
            while(count == 0){
                if(rightP - leftP + 1 == len1){
                    return true;
                }

                char leftCh = str2.charAt(leftP);
                if(chNumMap.containsKey(leftCh)){
                    chNumMap.put(leftCh, chNumMap.get(leftCh) + 1);
                    if(chNumMap.get(leftCh) > 0){
                        count++;
                    }
                }

                leftP++;
            }

            rightP++;
        }

        return false;
    }

}
