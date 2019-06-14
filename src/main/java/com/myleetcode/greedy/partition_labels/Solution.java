package com.myleetcode.greedy.partition_labels;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> partitionLabels(String S) {
        return partitionLabelsByGreedy(S);
    }

    // intuition:
    /*
    1. traverse the string record the last index of each char.
    2. using pointer to record end of the current sub string.
    */
    // TC: O(N)
    // SC: O(1)
    // solution: https://leetcode.com/problems/partition-labels/discuss/113259/Java-2-pass-O(n)-time-O(1)-space-extending-end-pointer-solution
    private List<Integer> partitionLabelsByGreedy(String str){
        List<Integer> ret = new ArrayList<>();

        if(str == null || str.length() == 0){
            return ret;
        }

        // record the last idx a char occurs in the str
        int[] charLastIdxMap = new int[26];
        for(int i = 0; i < str.length(); i++){
            charLastIdxMap[str.charAt(i) - 'a'] = i;
        }

        // start is the substring's startpoint, at first it's 0
        int start = 0;
        // end is the substring's endpoint, at first it's 0
        int end = -1;
        for(int i = 0; i < str.length(); i++){
            // so we keep the farest idx of all chars' last idx that we have encountered
            end = Math.max(end, charLastIdxMap[str.charAt(i) - 'a']);

            // if end equals to current i, means we got a substring that all chars in it has the farest idx at i(ie at end), so this is a valid substing we need
            if(end == i){
                // add this substring's length to ret
                ret.add(end - start + 1);

                // next substring's start point should be cur end plus 1
                start = end + 1;
            }
        }

        return ret;
    }
}
