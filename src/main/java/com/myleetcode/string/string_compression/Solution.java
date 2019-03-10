package com.myleetcode.string.string_compression;

class Solution {
    public int compress(char[] chars) {
        // special case
        if(chars == null || chars.length == 0){
            return 0;
        }

        return compressByCount(chars);
    }

    // 38. Count and Say， 与38题一样，采用了同样的逻辑来在不排序不破坏原字符串的情况下，一次遍历的过程中数出并拼接字符极其个数，唯一的区别是38题需要1，该题不需要1.
    private int compressByCount(char[] chars){
        StringBuilder sb = new StringBuilder();
        int count = 1;
        char preC = chars[0];
        for(int i = 1; i < chars.length; i++){
            if(preC == chars[i]){
                count++;
            }else{
                sb.append(preC);
                if(count != 1){
                    sb.append(count);
                }

                preC = chars[i];
                count = 1;
            }
        }

        sb.append(preC);
        if(count != 1){
            sb.append(count);
        }

        // check
        if(sb.length() > chars.length){
            return chars.length;
        }

        // leetcode is stupid on this problem. The description tells us we need to return the lenght. But the output it expect sth like this: ["a","2","b","2","c","3"]

        for(int i = 0; i < sb.length(); i++){
            chars[i] = sb.charAt(i);
        }

        return sb.length();
    }
}