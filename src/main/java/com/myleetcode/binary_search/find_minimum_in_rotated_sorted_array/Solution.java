package com.myleetcode.binary_search.find_minimum_in_rotated_sorted_array;

class Solution {
    public int findMin(int[] nums) {
        return findMinByBS(nums);
    }

    // intuition: this is the preprocess problem of 33. Search in Rotated Sorted Array
    // we use BS to find the idx that nums[idx] > nums[idx + 1], then idx+1 is the ans
    private int findMinByBS(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int start = 0;
        int end = nums.length - 1;
        while(start <= end){
            int mid = start + (end - start) / 2;
            if(mid < nums.length - 1 && nums[mid] > nums[mid + 1]){
                return nums[mid + 1];
            }

            // !!! mid only has two conditions:
            // 1st, in first part, then nums[mid] >= nums[start], first part vals are all larger than second part so ignore second part
            // 2nd, in second part, then nums[mid] <= nums[end], second part vals are all less than first part so ignore first part
            // mid at the first part's peak is the pos we are looking for, we have checked it above
            if(nums[mid] >= nums[start]){
                start = mid + 1;
            }else if(nums[mid] <= nums[end]){
                end = mid - 1;
            }
        }

        // !!! if not find scuh peak, means no rotation at all, the smallest val is at the 0th pos
        return nums[0];
    }

}
