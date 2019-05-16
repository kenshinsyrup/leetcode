package com.myleetcode.binary_search.count_of_smaller_numbers_after_self;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> countSmaller(int[] nums) {
        return countSmallerByBS(nums);
    }

    // TC: O(N^2)
    // SC: O(N)
    // thought: https://leetcode.com/problems/count-of-smaller-numbers-after-self/discuss/76576/My-simple-AC-Java-Binary-Search-code
    // since we only care about the num smaller than curNum after it, so we could traverse the nums from back to head
    // we keep a List to store the Sorted Nums that we have already met, then, when we meet a curNum in nums, we could do a BS in the List to find the idx that it belongs to, ie the first num in List that larger than it, this idx-1-0+1 is the number of num that less than curNum after it. Put this into a ret List.
    // keep doint this until we exhaust all nums
    private List<Integer> countSmallerByBS(int[] nums){
        List<Integer> ret = new ArrayList<>();

        if(nums == null || nums.length == 0){
            return ret;
        }

        int len = nums.length;
        List<Integer> snippet = new ArrayList<>();
        for(int i = len - 1; i >= 0; i--){
            int pos = findPosByBS(snippet, nums[i]);
            ret.add(0, pos);// pos - 1 - 0 + 1
        }

        return ret;
    }

    // TC: BS O(logN), insert O(N), total is O(N)
    private int findPosByBS(List<Integer> numList, int target){
        int size = numList.size();
        int start = 0;
        int end = size - 1;
        while(start <= end){
            int mid = start + (end - start) / 2;

            if(numList.get(mid) < target){
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }

        // !!! start at last is the first pos that the num is larger or equals target
        // insert, should be careful, if start equals to size, means we need add target to the last
        if(start == size){
            numList.add(target);
        }else{
            numList.add(start, target);
        }

        return start;
    }
}
