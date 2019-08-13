package com.myleetcode.array.next_permutation;

class Solution {
    public void nextPermutation(int[] nums) {
        nextPermutationByAnalysis(nums);
    }

    /*
    出错点：毫无思路
    */

    // 思路：
    // 源于：First, we observe that for any given sequence that is in descending order, no next larger permutation is possible. For example, no next permutation is possible for the following array: [9, 5, 4, 3, 1]
    /* 推理：
    We need to find the first pair of two successive numbers a[i]a[i] and a[i-1]a[i−1], from the right, which satisfy a[i] > a[i-1]a[i]>a[i−1].
    Now, no rearrangements to the right of a[i-1] can create a larger permutation since that subarray consists of numbers in descending order.
    Thus, we need to rearrange the number a[i-1] to the right of a[i-1]. Now, what kind of rearrangement will produce the next larger number? We want to create the permutation just larger than the current one. Therefore, we need to replace the number a[i−1] with the number which is just larger than itself among the numbers lying to its right section, say a[j].
    We swap the numbers a[i-1]a[i−1] and a[j]a[j]. We now have the correct number at index i-1i−1. But still the current permutation isn't the permutation that we are looking for. We need the smallest permutation that can be formed by using the numbers only to the right of a[i-1]a[i−1]. Therefore, we need to place those numbers in ascending order to get their smallest permutation.
    */
    // TC: O(N)
    // SC: O(1)
    private void nextPermutationByAnalysis(int[] nums){
        if(nums == null || nums.length == 0){
            return;
        }

        int len = nums.length;
        int leftP = len - 1;
        // 1 from end, find the first ptr that nums[leftP] > nums[leftP-1]
        while(leftP > 0 && nums[leftP] <= nums[leftP - 1]){
            leftP--;
        }
        // the ptr we need is leftP-1
        leftP--;

        // 1.1 check: if no such successive elems, what we need is the ascending sorted one, just reverse all
        if(leftP < 0){
            reverse(nums, 0, len - 1);
            return;
        }

        // 2 from end, find the first elem smaller than nums[leftP]
        int rightP = len - 1;
        while(rightP >= 0 && nums[leftP] >= nums[rightP]){
            rightP--;
        }

        // 3, swap and reverse the right part
        swap(nums, leftP, rightP);
        reverse(nums, leftP + 1, len - 1);
    }

    private void reverse(int[] nums, int leftIdx, int rightIdx){
        while(leftIdx < rightIdx){
            swap(nums, leftIdx, rightIdx);

            leftIdx++;
            rightIdx--;
        }
    }

    private void swap(int[] nums, int leftIdx, int rightIdx){
        int temp = nums[leftIdx];
        nums[leftIdx] = nums[rightIdx];
        nums[rightIdx] = temp;
    }

}
