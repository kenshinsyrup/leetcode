package com.myleetcode.binary_search.find_first_and_last_position_of_element_in_sorted_array;

class Solution {
    public int[] searchRange(int[] nums, int target) {
        return searchRangeByBinarySearch(nums, target);
    }

    // TC: O(logN)
    // SC: O(1)
    // intuition: array is sorted and time complexity is O(logN) so must be a binary search problem.
    private int[] searchRangeByBinarySearch(int[] nums, int target){
        int[] ret = new int[]{-1, -1};

        if(nums == null || nums.length == 0){
            return ret;
        }

        // find the first num less than target. the start at last will points to the first num that greater or equals to target
        int start = 0;
        int end = nums.length - 1;
        while(start <= end){
            int mid = start + (end - start) / 2;
            if(nums[mid] < target){// here is <
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }
        if(start < nums.length && nums[start] == target){
            ret[0] = start;
        }

        // find the first num greater than target. the start at last will points to the first num that greater than target. so start - 1 is the right range.
        start = 0;
        end = nums.length - 1;
        while(start <= end){
            int mid = start + (end - start) / 2;
            if(nums[mid] <= target){// here is <=
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }
        if(start > 0 && nums[start - 1] == target){
            ret[1] = start - 1;
        }

        return ret;

    }
}
