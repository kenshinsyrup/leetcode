package com.myleetcode.string.reverse_words_in_a_string;

class Solution {
    public String reverseWords(String s) {
        // return reverseWordsByTwoPointers(s);
        return reverseWordsByReverse(s);
    }

    // use this solution
    // use the same sol with 186. Reverse Words in a String II
    // TC: O(N)
    // SC: O(N)
    private String reverseWordsByReverse(String str){
        if(str == null || str.length() == 0){
            return str;
        }

        str = str.trim(); // remove leading and tailing spaces
        int len = str.length();
        char[] charArray = str.toCharArray();

        // 1 reverse the whole string
        reverse(charArray, 0, len - 1);

        // 2 reverse every word
        int i = 0;
        int j = 0;
        while(j < len){
            // find the delimiter, reverse the word str[i:j-1]
            while(j < len && charArray[j] != ' '){
                j++;
            }
            reverse(charArray, i, j - 1);

            // skip spaces
            while(j < len && charArray[j] == ' '){
                j++;
            }

            // move i
            i = j;
        }

        // 3 build ans string, remove useless spaces
        StringBuilder sb = new StringBuilder();
        for(int idx = 0; idx < len; idx++){
            // skip useless spaces
            if(sb.length() > 0 && sb.charAt(sb.length() - 1) == ' ' && charArray[idx] == ' '){
                continue;
            }

            sb.append(charArray[idx]);
        }

        return sb.toString();
    }

    private void reverse(char[] charArray, int start, int end){
        while(start < end){
            char temp = charArray[start];
            charArray[start] = charArray[end];
            charArray[end] = temp;

            start++;
            end--;
        }
    }

    // TC: O(N)
    // SC: O(N)
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
