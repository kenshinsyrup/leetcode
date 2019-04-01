package com.myleetcode.string.string_to_integer_atoi;

class Solution {
    public int myAtoi(String str) {
        return myAtoiByScan(str);
    }

    // 这个题有海量的反对, 很多人表示这个不是个好题目https://leetcode.com/problems/string-to-integer-atoi/discuss/4640/Such-a-shitty-problem

    // 这里有一个很清晰的思路代码
    // https://leetcode.com/problems/string-to-integer-atoi/discuss/4643/Java-Solution-with-4-steps-explanations

    // intuition: scan str, find the start(if no invalid start, return 0), find the end(if after end there are some invalid chars, return 0), then we get an subStr = s.substring(start, end+1), then we change the subStr to integer, if overflow, return Max or Min value
    private int myAtoiByScan(String str){
        // special case
        if(str == null || str.length() == 0){
            return 0;
        }

        int start = 0;
        // scan to find the first valid char
        while(start < str.length()){
            if(str.charAt(start) == '-' || str.charAt(start) == '+' || (str.charAt(start) >= '0' && str.charAt(start) <= '9')){
                break;
            }else if(str.charAt(start) == ' '){
                start++;
            }else{
                return 0;
            }
        }

        if(start == str.length()){
            return 0;
        }

        int sign = 1;
        if(str.charAt(start) == '-'){
            sign = -1;
            start++;
        }else if(str.charAt(start) == '+'){
            start++;
        }

        // find the end
        int end = start;
        while(end < str.length()){
            if(str.charAt(end) >= '0' && str.charAt(end) <= '9'){
                end++;
            }else{
                break;
            }
        }

        // get substring
        String subStr = str.substring(start, end);

        int total = 0;
        for(int i = 0; i < subStr.length(); i++){
            String intStr = String.valueOf(subStr.charAt(i));
            int num = Integer.valueOf(intStr);
            if(total > (Integer.MAX_VALUE - num) / 10){
                if(sign == 1){
                    return Integer.MAX_VALUE;
                }else{
                    return Integer.MIN_VALUE;
                }
            }

            total *= 10;
            total += num;
        }

        return total * sign;

    }
}
