package com.myleetcode.two_pointers.reverse_string_ii;

class Solution {
    public String reverseStr(String s, int k) {
        return reverseStrByTwoPointers(s, k);
    }

    // intuition:
    // 1. if has more then 2k chars, for every 2*k, reverse the first k
    //      2 if has less than 2k chars, but more than k chars, reverse the first k
    //      3 if has less than k chars, reverse them all
    // 2 and 3 would not both exist
    // TC: O(N)
    // SC: O(N)
    private String reverseStrByTwoPointers(String str, int k){
        if(str == null || str.length() == 0){
            return str;
        }
        if(k < 0){
            return str;
        }

        int len = str.length();
        char[] charArr = str.toCharArray();

        // 1. has more than 2k
        int leftP = 0;
        int rightP = leftP + 2 * k - 1;
        while(rightP < len){
            // reverse first k
            reverse(charArr, leftP, leftP + k - 1);

            leftP  = rightP + 1;
            rightP = leftP +  2 * k - 1;
        }

        // 2. has more than k, less than 2k
        if(leftP + k - 1 < len){
            rightP = leftP + k - 1;
            while(rightP < len){
                // reverse first k
                reverse(charArr, leftP, leftP + k - 1);

                leftP = rightP + 1;
                rightP = leftP + k - 1;
            }
        }else{
            // 3. has less than k
            if(leftP < len){
                reverse(charArr, leftP, len - 1);
            }
        }

        return new String(charArr);

    }

    private void reverse(char[] charArr, int start, int end){
        while(start < end){
            char temp = charArr[start];
            charArr[start] = charArr[end];
            charArr[end] = temp;

            start++;
            end--;
        }
    }
}
