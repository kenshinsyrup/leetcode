package com.myleetcode.math.strobogrammatic_number;

class Solution {
    public boolean isStrobogrammatic(String num) {
        return isStrobogrammaticByTwoPointers(num);
    }

    // TC: O(N)
    // SC: O(1)
    // intuition: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
    // if length is odd, then the middle num must be 0 or 1, the rest are the same with even length
    // if even length string num, use start and end two pointers to traverse, and we check the chars that if they are strobogrammatic or not.
    // 0:0, 1:1, 6:9, 8:8, 9:6
    private boolean isStrobogrammaticByTwoPointers(String numStr){
        if(numStr == null || numStr.length() == 0){
            return false;
        }

        int len = numStr.length();
        int start = 0;
        int end = len - 1;
        while(start < end){
            if(!isSymmetric(numStr.charAt(start), numStr.charAt(end))){
                return false;
            }

            start++;
            end--;
        }

        // odd length, check the middle cha
        if(len % 2 != 0){
            if(!(numStr.charAt(start) == '0' || numStr.charAt(start) == '1' || numStr.charAt(start) == '8')){
                return false;
            }
        }

        return true;
    }

    private boolean isSymmetric(char c1, char c2){
        return c1 == '0' && c2 == '0' || c1 == '1' && c2 == '1' || c1 == '6' && c2 == '9' || c1 == '8' && c2 == '8' || c1 == '9' && c2 == '6';
    }
}
