package com.myleetcode.two_pointers.reverse_vowels_of_a_string;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class Solution {
    public String reverseVowels(String s) {
        return reverseVowelsByTwoPointers(s);
    }

    // TC: O(N)
    // SC: O(N)
    private String reverseVowelsByTwoPointers(String str){
        if(str == null || str.length() == 0){
            return str;
        }

        Set<Character> vowelSet = new HashSet<>();
        Collections.addAll(vowelSet, 'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U');

        char[] chArray = str.toCharArray();

        int len = str.length();
        int leftP = 0;
        int rightP = len - 1;
        while(leftP < rightP){
            // leftP find a vowel
            while(leftP < rightP && !vowelSet.contains(chArray[leftP])){
                leftP++;
            }

            // rightP find a vowel
            while(leftP < rightP && !vowelSet.contains(chArray[rightP])){
                rightP--;
            }

            // check
            if(leftP >= rightP){
                break;
            }

            // swap
            swap(chArray, leftP, rightP);

            // move towards each other
            leftP++;
            rightP--;
        }

        return new String(chArray);

    }

    private void swap(char[] chArray, int leftIdx, int rightIdx){
        char ch = chArray[leftIdx];
        chArray[leftIdx] = chArray[rightIdx];
        chArray[rightIdx] = ch;
    }
}
