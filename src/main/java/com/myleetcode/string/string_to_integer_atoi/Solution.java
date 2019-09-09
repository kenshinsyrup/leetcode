package com.myleetcode.string.string_to_integer_atoi;

class Solution {
    public int myAtoi(String str) {
        return myAtoiByScan(str);
    }

    // 这里有一个很清晰的思路代码
    // https://leetcode.com/problems/string-to-integer-atoi/discuss/4643/Java-Solution-with-4-steps-explanations

    // intuition: scan str, find the start(if no invalid start, return 0), find the end(if after end there are some invalid chars, return 0), then we get an subStr = s.substring(start, end+1), then we change the subStr to integer, if overflow, return Max or Min value
    /*
    Say N is the str length
    TC: O(N)
    SC: O(1)
    */
    private int myAtoiByScan(String str){
        // special case
        if(str == null || str.length() == 0){
            return 0;
        }

        int start = 0;
        // 1. scan to find the first valid char
        while(start < str.length()){
            if(str.charAt(start) == '-' || str.charAt(start) == '+' || (str.charAt(start) >= '0' && str.charAt(start) <= '9')){
                break;
            }else if(str.charAt(start) == ' '){
                start++;
            }else{
                return 0;
            }
        }
        // check, if no valid substring
        if(start == str.length()){
            return 0;
        }

        // 2. get sign
        int sign = 1;
        if(str.charAt(start) == '-'){
            sign = -1;
            start++;
        }else if(str.charAt(start) == '+'){
            start++;
        }

        // 3. find the end
        int end = start;
        while(end < str.length()){
            if(str.charAt(end) >= '0' && str.charAt(end) <= '9'){
                end++;
            }else{
                break;
            }
        }
        end--;

        // 4. get the total number, when do this, we use the positive number, because we have the seperate sign, we only need combine the total and sign together at last
        int total = 0;
        for(int i = start; i <= end; i++){
            int num = str.charAt(i) - '0';

            // check whether we could overflow after total*10+num, use division
            if(total > (Integer.MAX_VALUE - num) / 10){
                if(sign == 1){
                    return Integer.MAX_VALUE;
                }else{
                    return Integer.MIN_VALUE;
                }
            }

            // if not overflow, get new total
            total *= 10;
            total += num;
        }

        // when return, combine the sign with total
        return total * sign;

    }
}