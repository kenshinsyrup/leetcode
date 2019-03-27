package com.myleetcode.divide_and_conquer.longest_common_prefix;

class Solution {
    public String longestCommonPrefix(String[] strs) {
        // return longestCommonPrefixByHorizontalScanning(strs);
        return longestCommonPrefixByDivideAndConquer(strs);
    }

    // another approach: Divide and Conquer
    private String longestCommonPrefixByDivideAndConquer(String[] strs){
        // special case
        if(strs == null || strs.length == 0){
            return "";
        }
        if(strs.length == 1){
            return strs[0];
        }

        int len = strs.length;
        return findPrefixByDivideAndConquer(strs, 0, len - 1);

    }

    private String findPrefixByDivideAndConquer(String[] strs, int leftIdx, int rightIdx){
        // base case
        if(leftIdx == rightIdx){
            return strs[leftIdx];
        }

        // normal case
        // find mid idx, 左半部分[leftIdx:mid], 右半部分[mid+1:rightIdx],这个右半部分切记是从mid+1开始，否则肯定会在特殊情况下死循环导致stackoverflow，比如strs长度为3，idx为0，1，2，那么leftIdx为0，rightIdx为2，则mid为1，这样如果右半部分也取mid:rightIdx,则递归进入右半部分后，base case不满足，重新计算mid，得到新的mid是1，rightIdx为2，开始死循环。
        // 最常见的错误是认为右半部分如果从mid+1开始，那么会在leftIdx==rightIdx这个情况下算出来的mid==leftIdx==rightIdx，这样mid+1会大于rightIdx会越界，但这个地方前提就是错的，由于base case在最前面检查，所以在计算mid时，不可能出现leftIdx==rightIdx的情况，所以不用考虑mid+1会大于rightIdx的问题
        int mid = leftIdx + (rightIdx - leftIdx) / 2;

        // get left part prefix
        String leftPrefix = findPrefixByDivideAndConquer(strs, leftIdx, mid);
        // get right part prefix
        String rightPrefix = findPrefixByDivideAndConquer(strs, mid + 1, rightIdx);

        // get the whole prefix
        return getPrefix(leftPrefix, rightPrefix);
    }

    // TC: O(S), S is the sum of length of all string in strs
    // intuition: compare first two to get a prefix, compare prefix with 3th get a new prefix, until end.
    private String longestCommonPrefixByHorizontalScanning(String[] strs){
        // special case
        if(strs == null || strs.length == 0){
            return "";
        }
        if(strs.length == 1){
            return strs[0];
        }

        int len = strs.length;
        String prefix = strs[0];
        for(int i = 1; i < len; i++){
            prefix = getPrefix(prefix, strs[i]);

            // for optimization: break early
            if(prefix == ""){
                return "";
            }
        }

        return prefix;

    }

    private String getPrefix(String firstStr, String secondStr){
        if(firstStr == null || firstStr.length() == 0 || secondStr == null || secondStr.length() == 0){
            return "";
        }

        int i = 0;
        while(i < firstStr.length() && i < secondStr.length()){
            if(firstStr.charAt(i) != secondStr.charAt(i)){
                return firstStr.substring(0, i);
            }

            i++;
        }

        return firstStr.substring(0, i);
    }

}
