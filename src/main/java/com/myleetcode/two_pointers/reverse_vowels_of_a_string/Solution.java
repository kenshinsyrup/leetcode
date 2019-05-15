package com.myleetcode.two_pointers.reverse_vowels_of_a_string;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class Solution {
    public String reverseVowels(String s) {
        // return reverseVowelsByTwoPointers(s);
        return reverseVowelsByTwoPointersII(s);
    }

    // TC: O(N)
    // SC: O(N)
    // optimize, convert String to char[], then do the operations, then build the output String, this way reduce the Building of String to 1 time
    private String reverseVowelsByTwoPointersII(String str){
        if(str == null || str.length() == 0){
            return str;
        }

        Set<Character> vowelSet = new HashSet<>();
        Collections.addAll(vowelSet, 'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U');

        int len = str.length();
        int start = 0;
        int end = len - 1;
        char[] strCharArr = str.toCharArray();
        while(start < end){
            char startCh = strCharArr[start];
            char endCh = strCharArr[end];
            if(!vowelSet.contains(startCh)){
                start++;
                continue;
            }

            if(!vowelSet.contains(endCh)){
                end--;
                continue;
            }

            // both are vowels and not equal, swap
            if(vowelSet.contains(startCh) && vowelSet.contains(endCh) && startCh != endCh){
                strCharArr[start] = endCh;
                strCharArr[end] = startCh;
            }
            // both are vowels, after process, move
            start++;
            end--;
        }

        return new String(strCharArr);


    }
    // this cost about 250ms... because build String is costy
    // intuition: Two Pointers from head and tail move towards eachother
    private String reverseVowelsByTwoPointers(String str){
        if(str == null || str.length() == 0){
            return str;
        }

        Set<Character> vowelSet = new HashSet<>();
        Collections.addAll(vowelSet, 'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U');

        int len = str.length();
        int start = 0;
        int end = len - 1;
        while(start < end){
            char startCh = str.charAt(start);
            char endCh = str.charAt(end);

            if(!vowelSet.contains(startCh)){
                start++;
                continue;
            }

            if(!vowelSet.contains(endCh)){
                end--;
                continue;
            }

            // both are vowels and not equal, swap
            if(vowelSet.contains(startCh) && vowelSet.contains(endCh) && startCh != endCh){
                str = str.substring(0, start) + endCh + str.substring(start + 1, len);
                str = str.substring(0, end) + startCh + str.substring(end + 1, len);
            }
            // both are vowels, after process, move
            start++;
            end--;
        }

        return str;
    }
}
