package com.myleetcode.string.reverse_words_in_a_string_ii;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public void reverseWords(char[] str) {
        // reverseWordsWithStack(str);
        reverseWordsByReverse(str);
    }

    // TC: O(N)
    // SC: O(1)
    // reverse two times, first time reversen the whole sentence, second time reverse every word
    private void reverseWordsByReverse(char[] str){
        if(str == null || str.length <= 1){
            return;
        }

        // reverse the whole sentence, so the whole sentence is ok but word is not
        reverse(str, 0, str.length - 1);

        int i = 0;
        int j = 0;
        while(i < str.length){
            while(j < str.length && str[j] != ' '){
                j++;
            }
            //reverse word
            reverse(str, i, j - 1);

            // skip spaces
            while(j < str.length && str[j] == ' '){
                j++;
            }

            // move i
            i = j;
        }
    }

    private void reverse(char[] str, int start, int end){
        while(start < end){
            char temp = str[start];
            str[start] = str[end];
            str[end] = temp;

            start++;
            end--;
        }
    }

    // TC: O(N^2), N is the length of str
    // SC: O(N)
    // intuition: we could traverse the str from end with two pointers, during this we push chars into a stack, when encounters a space, we pop the chars in stack to a new str with a space.
    private void reverseWordsWithStack(char[] str){
        if(str == null || str.length <= 1){
            return;
        }

        Deque<Character> charStack = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();

        int i = str.length - 1;
        int j = i;
        while(i >= 0){
            while(j >= 0 && str[j] != ' '){
                charStack.push(str[j]);
                j--;
            }
            while(!charStack.isEmpty()){
                sb.append(charStack.pop());
            }
            // append all the spaces
            while(j >= 0 && str[j] == ' '){
                sb.append(' ');
                j--;
            }

            i = j;
        }

        char[] newStr = sb.toString().toCharArray();
        for(int k = 0; k < str.length; k++){
            str[k] = newStr[k];
        }
    }
}
