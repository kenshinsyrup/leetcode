package com.myleetcode.array.find_all_numbers_disappeared_in_an_array;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        return findDisappearedNumbersBySwapSort(nums);
    }

    // TC: O(N)
    // SC: O(1)
    // 思路请参考41题
    // intuition: 整体思路同41. First Missing Positive，做一点调整,找出所有的缺少的数字.这个题目比41好的点在于，如果输入为[]那么缺少的数字是[], 如果输入是[1]或者形如[1,2,3]这样的完美的nums，输出是[]
    private List<Integer> findDisappearedNumbersBySwapSort(int[] nums){
        List<Integer> ret = new ArrayList<>();

        if(nums == null){
            return ret;
        }

        int n = nums.length;
        // A[i] rang is 1:n, and i range is 0:n-1, so a[i] should be in i+1 position
        for(int i = 0; i < n; i++){
            while(nums[i] >= 1 && nums[i] <= n && (nums[i] != nums[nums[i] - 1])){
                swap(nums, i, nums[i] - 1);
            }
        }

        for(int i = 0; i < n; i++){
            if(nums[i] != i + 1){
                ret.add(i + 1);
            }
        }

        return ret;

    }

    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
