package com.myleetcode.string.reverse_words_in_a_string;

class Solution {
    public String reverseWords(String s) {
        return reverseWordsByTwoPointers(s);
    }

    // TC: O(N)
    // SC: O(1)
    // intuition: String Reverse
    // for the requirment: "Your reversed string should not contain leading or trailing spaces." and "You need to reduce multiple spaces between two words to a single space in the reversed string." , we could accomplish this automatically if we extracts words in String and concatnate them reversely
    // there's are a lot of approaches, the most classic two: 1 convert String to words List and concatnate them in reverse order, this cost additional O(N) SC. 2 use Two Pointers, startP and endP, from the String's tail to traverse the String, when startP find a non space char, endP moves to startP, this is the end of current word, then startP moves to find the head of current word, then we get a word, repeat this
    private String reverseWordsByTwoPointers(String str){
        if(str == null || str.length() == 0){
            return str;
        }

        int strLen = str.length();
        int start = strLen - 1;
        int end = strLen - 1;
        StringBuilder sb = new StringBuilder();
        while(start >= 0){
            // if space, keep move to head of str
            while(start >= 0){
                if(str.charAt(start) == ' '){
                    start--;
                }else{
                    break;
                }
            }
            if(start < 0){ // if from start to head of str are all ' ', break the while, otherwise leading ' ' will be added to the sb
                break;
            }

            // find the tail of word
            end = start;

            // find the head of word
            while(start >= 0){
                if(str.charAt(start) != ' '){
                    start--;
                }else{
                    break;
                }
            }

            // when loop end, start is ' ' or just because we have reach the 0th pos of str
            if(start < 0){
                sb.append(str.substring(0, end + 1));// if start < 0, word is str[0:end]
            }else{
                sb.append(str.substring(start + 1, end + 1)); // if start is ' ', word is str[start+1: end]
            }
            // append a space
            sb.append(" ");
        }

        // we shoulde delete the last char ' ', so we should first check if sb.length() is 0
        if(sb.length() == 0){
            return "";
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

}
