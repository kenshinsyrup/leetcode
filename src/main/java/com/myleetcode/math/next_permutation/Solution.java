package com.myleetcode.math.next_permutation;

class Solution {
    public void nextPermutation(int[] nums) {
        // TRICKY PROBLEM: CHECK THE APPROACH2 IN SOLUTION

        // nextPermutationByTraverse(nums);// WRONG
        // nextPermutationByTraverseII(nums);
        nextPermutationByTraverseIII(nums);
    }

    // TC: O(N)
    // SC: O(1)
    // just to decouple the nested for loop to reduce TC from O(N^2) to O(N)
    private void nextPermutationByTraverseIII(int[] nums){
        if(nums == null || nums.length == 0 || nums.length == 1){
            return;
        }

        int len = nums.length;

        int idx = 0;
        boolean find = false;
        // traverse backward to find the first pair that nums[i] > nums[i - 1]; then traverse backward to find the first value bigger than nums[i-1] during nums[i:len-1], swap these two, then reverse nums[i:len-1].
        // if no such pair, reverse nums
        for(int i = len - 1; i >= 1; i--){
            if(nums[i] > nums[i - 1]){
                idx = i;
                find = true;
                break;
            }
        }

        if(find){
            for(int j = len - 1; j >= idx; j--){
                // find first value greater than nms[idx], swap
                if(nums[j] > nums[idx - 1]){
                    int temp = nums[idx - 1];
                    nums[idx - 1] = nums[j];
                    nums[j] = temp;

                    // reverse
                    reverse(nums, idx, len - 1);
                    return;
                }
            }
        }

        reverse(nums, 0, len - 1);
    }

    // TC: O(N^2), N is the length of nums, because we traverse the nums once
    // SC: O(1)
    private void nextPermutationByTraverseII(int[] nums){
        if(nums == null || nums.length == 0 || nums.length == 1){
            return;
        }

        int len = nums.length;

        int idx = 0;
        // traverse backward to find the first pair that nums[i] > nums[i - 1]; then traverse backward to find the first value bigger than nums[i-1] during nums[i:len-1], swap these two, then reverse nums[i:len-1].
        // if no such pair, reverse nums
        for(int i = len - 1; i >= 1; i--){
            if(nums[i] > nums[i - 1]){
                idx = i - 1;

                for(int j = len - 1; j >= i; j--){
                    // find first value greater than nms[idx], swap
                    if(nums[j] > nums[idx]){
                        int temp = nums[idx];
                        nums[idx] = nums[j];
                        nums[j] = temp;

                        // reverse
                        reverse(nums, i, len - 1);
                        return;
                    }
                }
            }
        }

        reverse(nums, 0, len - 1);

    }

    private void reverse(int[] nums, int start, int end){
        int i = start;
        int j = end;
        while(i < j){
            swap(nums, i, j);

            i++;
            j--;
        }
    }
    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


    // intuition: traverse backward, swap the first pair that nums[i-1] < nums[i], if reach the head but no such pairs found, return ascending order nums
    private void nextPermutationByTraverse(int[] nums){
        if(nums == null || nums.length == 0 || nums.length == 1){
            return;
        }

        int len = nums.length;
        for(int i = len - 1; i >= 1; i--){
            if(nums[i] > nums[i - 1]){
                int temp = nums[i];
                nums[i] = nums[i - 1];
                nums[i - 1] = temp;

                return;
            }
        }

        // not find, retrun ascending ordered nums, because if not find we know the array is descending order, so we only need to reverse it
        for(int i = 0; i < len / 2; i++){
            int temp = nums[i];
            nums[i] = nums[len - 1 - i];
            nums[len - 1 - i] = temp;
        }
    }
}
