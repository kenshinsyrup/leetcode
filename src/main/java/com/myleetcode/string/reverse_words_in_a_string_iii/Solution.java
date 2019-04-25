package com.myleetcode.string.reverse_words_in_a_string_iii;

class Solution {
    public String reverseWords(String s) {
        return reverseWordsByReverse(s);
    }

    // TC: O(N)
    // SC: O(N)
    // intuition: this is the simple one of the 186. Reverse Words in a String II
    // we just need to reverse every word
    private String reverseWordsByReverse(String str){
        if(str == null || str.length() <= 1){
            return str;
        }

        char[] chars = str.toCharArray();

        int i = 0;
        int j = 0;
        while(i < chars.length){
            while(j < chars.length && chars[j] != ' '){
                j++;
            }
            reverse(chars, i, j - 1);

            // skip spaces
            while(j < chars.length && chars[j] == ' '){
                j++;
            }

            i = j;
        }

        return new String(chars);
    }

    private void reverse(char[] chars, int start, int end){
        while(start < end){
            char temp = chars[start];
            chars[start] = chars[end];
            chars[end] = temp;

            start++;
            end--;
        }
    }
}
