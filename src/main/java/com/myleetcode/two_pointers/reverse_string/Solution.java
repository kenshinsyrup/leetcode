package com.myleetcode.two_pointers.reverse_string;

class Solution {
    public void reverseString(char[] s) {
        reverseStringByTwoPointers(s);
    }

    // Two Pointers move towards each othe
    // TC: O(N)
    // SC: O(1)
    private void reverseStringByTwoPointers(char[] charArray){
        if(charArray == null || charArray.length == 0){
            return;
        }

        int len = charArray.length;
        int leftP = 0;
        int rightP = len - 1;
        while(leftP < rightP){
            char ch = charArray[leftP];
            charArray[leftP] = charArray[rightP];
            charArray[rightP] = ch;

            leftP++;
            rightP--;
        }
    }
}

