package com.myleetcode.array.rotate_array;

class Solution {
    public void rotate(int[] nums, int k) {
        // rotateByTraverse(nums, k);
        rotateByReverse(nums, k);
    }


    // TC: O(N), N is the length of array
    // SC: O(1)
    // reverse 3 times
//This approach is based on the fact that when we rotate the array k times, k%nk elements from the back end of the array come to the front and the rest of the elements from the front shift backwards.
    /*
Let n=7n=7 and k=3
Original List                   : 1 2 3 4 5 6 7
After reversing all numbers     : 7 6 5 4 3 2 1
After reversing first k numbers : 5 6 7 4 3 2 1
After revering last n-k numbers : 5 6 7 1 2 3 4 --> Result
    */
    // https://leetcode.com/problems/rotate-array/discuss/54250/Easy-to-read-Java-solution
    private void rotateByReverse(int[] nums, int k){
        // special case
        if(nums == null || nums.length == 0 || nums.length == 1){
            return;
        }

        // must get the right number to rotate
        k = k % nums.length;

        // reverse all
        reverse(nums, 0, nums.length - 1);
        // reverse first k number
        reverse(nums, 0, k - 1);
        // reverse rest number
        reverse(nums, k, nums.length - 1);
    }

    private void reverse(int[] nums, int start, int end){
        while(start <= end){
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;

            start++;
            end--;
        }
    }

    // TC: O(K*N), K is the rotate times, N is the array length
    // SC: O(1)
    private void rotateByTraverse(int[] nums, int k){
        // special case
        if(nums == null || nums.length == 0 || nums.length == 1){
            return;
        }

        int len = nums.length;
        for(int i = 0; i < k; i++){
            // shift value in nums by one index
            int temp = nums[len - 1];
            for(int j = len - 1; j >= 1; j--){
                nums[j] = nums[j - 1];
            }
            nums[0] = temp;
        }
    }
}
