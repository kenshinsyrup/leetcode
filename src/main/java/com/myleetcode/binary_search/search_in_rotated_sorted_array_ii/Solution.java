package com.myleetcode.binary_search.search_in_rotated_sorted_array_ii;

class Solution {
    public boolean search(int[] nums, int target) {
        return searchByBS(nums, target);
    }

    // At first, I thought this is a same problem with 33 and use the same solution, but there's a problem to find pivotIdx with BS because there'are duplicates in the nums array.
    // so, we could remove duplicates first, cost O(N), then use 33's solution. Or use the traverse instead of BS to find the pivotIdx, also cost O(N). This make the whole solution cost O(N).
    // but if cost O(N), why not just traverse the nums array to find the target...
    // TC: O(N)
    // SC: O(1)
    // intuition: this is a follow up of 33. Search in Rotated Sorted Array
    // looks like the same idea, find the smallest elem and that's the pivot
    // 1 if no rotated, do BS in the whole nums
    // otherwise, do BS in the part that the target is on
    private boolean searchByBS(int[] nums, int target){
        if(nums == null || nums.length == 0){
            return false;
        }
        // !!!
        if(nums.length == 1){
            return nums[0] == target;
        }

        int pivotIdx = findPivotIdx(nums);
        if(pivotIdx == 0){
            return findTarget(nums, 0, nums.length - 1, target);
        }



        // if target < nums[0], must at right half ascending line segment
        if(target < nums[0]){
            return findTarget(nums, pivotIdx, nums.length - 1, target);
        }

        return findTarget(nums, 0, pivotIdx - 1, target);

    }

    private boolean findTarget(int[] nums, int left, int right, int target){
        int start = left;
        int end = right;
        while(start <= end){
            int mid = start + (end - start) / 2;

            if(nums[mid] == target){
                return true;
            }else if(nums[mid] < target){
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }

        return false;
    }

    // TC: O(N)
    // SC: O(1)
    private int findPivotIdx(int[] nums){
        for(int i = 1; i < nums.length; i++){
            if(nums[i - 1] > nums[i]){
                return i;
            }
        }
        return 0;
    }

    // because have duplicates, so BS to find pivot is not correct, consider the input:[1,3,1,1,1] 3, the pivotIdx will be 0 because could not find the correct nums[mid] > nums[mid+1] with this BS solution
    /*private int findPivotIdx(int[] nums){
        int start = 0;
        int end = nums.length - 1;
        while(start <= end){
            int mid = start + (end - start) / 2;
            //!!! check OutOfBoundary
            if(mid < nums.length - 1 && nums[mid] > nums[mid + 1]){
                return mid + 1;
            }else{
                if(nums[start] < nums[mid]){
                    start = mid + 1;
                }else{
                    end = mid - 1;
                }
            }
        }

        // if not find the rotated node, then means no rotate, so return the 0
        return 0;
    }*/
}
