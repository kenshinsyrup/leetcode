package com.myleetcode.string.valid_palindrome;

class Solution {
    public boolean isPalindrome(String s) {
        return isPalindromeByTwoPointers(s);
    }

    // intuition: two pointers traverse from two ends of string
    private boolean isPalindromeByTwoPointers(String s){
        if(s == null || s.length() == 0){
            return true;
        }

        int len = s.length();
        int leftP = 0;
        int rightP = len - 1;
        while(leftP <= rightP){
            // check alphanumeric
            if(!isAlphanumeric(s.charAt(leftP))){
                leftP++;
                continue; // !!! 第一次没有写这个continue出错，这里如果检查到非alphaNumber到内容，需要继续向后移动，而不能只移动一次
            }
            if(!isAlphanumeric(s.charAt(rightP))){
                rightP--;
                continue;
            }

            // compare
            if(!isEqual(s.charAt(leftP), s.charAt(rightP))){
                return false;
            }

            leftP++;
            rightP--;
        }

        return true;

    }

    private boolean isAlphanumeric(char c){
        if(isAlpha(c) || isNumber(c)){
            return true;
        }
        return false;
    }

    private boolean isAlpha(char c){
        if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')){
            return true;
        }
        return false;
    }

    private boolean isNumber(char c){
        if((c >= '0' && c <= '9')){
            return true;
        }
        return false;
    }

    private boolean isEqual(char firstChar, char secondChar){
        // same
        if(firstChar == secondChar){
            return true;
        }
        // not same, but is uppercase and lowercase char of same character
        if(isAlpha(firstChar) && isAlpha(secondChar)){
            if(firstChar == (secondChar + 'A' - 'a') || secondChar == (firstChar + 'A' - 'a')){
                return true;
            }
        }

        return false;
    }
}
