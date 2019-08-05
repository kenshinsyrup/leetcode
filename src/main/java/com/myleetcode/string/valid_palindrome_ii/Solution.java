package com.myleetcode.string.valid_palindrome_ii;

class Solution {
    public boolean validPalindrome(String s) {
        return validPalindromeByTwoPointer(s);
    }

    // intuition: O(N) to check a String is palindrome or not. O(N) to try to remove a char in String we need try all position. Totally is O(N^2)

    // leftP from start, rightP from end
    // if leftP and rightP equals, move to each other;
    // if not equal: check if leftP+1:rightP is palindrome (means delete cur leftP char) or leftP:rightP-1 is palindorme(menas delete cur rightP char), if both not, false
    // TC: O(N)
    // SC: O(1)
    private boolean validPalindromeByTwoPointer(String str){
        if(str == null || str.length() <= 1){
            return true;
        }

        int leftP = 0;
        int rightP = str.length() - 1;
        while(leftP <= rightP){
            // equal
            if(str.charAt(leftP) == str.charAt(rightP)){
                leftP++;
                rightP--;
            }else{
                // not equal

                return isPalindrome(str, leftP + 1, rightP) || isPalindrome(str, leftP, rightP - 1);
            }
        }

        return true;
    }

    private boolean isPalindrome(String str, int start, int end){
        while(start <= end){
            if(str.charAt(start) == str.charAt(end)){
                start++;
                end--;
            }else{
                return false;
            }
        }

        return true;
    }
}
