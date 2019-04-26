package com.myleetcode.string.length_of_last_word;

class Solution {
    public int lengthOfLastWord(String s) {
        return lengthOfLastWordByCount(s);
    }

    private int lengthOfLastWordByCount(String str){
        if(str == null || str.length() == 0){
            return 0;
        }

        int len = str.length();
        int i = len - 1;
        // find the first last word
        while(i >= 0 && str.charAt(i) == ' '){
            i--;
        }

        // count the word
        int count = 0;
        while(i >= 0 && str.charAt(i) != ' '){
            count++;
            i--;
        }

        return count;
    }
}
