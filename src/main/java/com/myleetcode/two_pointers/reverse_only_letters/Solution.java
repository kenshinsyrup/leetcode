package com.myleetcode.two_pointers.reverse_only_letters;

class Solution {
    public String reverseOnlyLetters(String S) {
        return reverseOnlyLettersByTwoPointers(S);
    }

    // Two Pointers move towards each other
    // TC: O(N)
    // SC: O(N), char array
    private String reverseOnlyLettersByTwoPointers(String str){
        if(str == null || str.length() == 0){
            return str;
        }

        char[] chArray = str.toCharArray();
        int len = str.length();
        int leftP = 0;
        int rightP = len - 1;
        while(leftP < rightP){
            // leftP find a letter
            while(leftP < rightP && !isLetter(str.charAt(leftP))){
                leftP++;
            }

            // rightP find a letter
            while(leftP < rightP && !isLetter(str.charAt(rightP))){
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

    private boolean isLetter(char ch){
        if((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
            return true;
        }

        return false;
    }
}
