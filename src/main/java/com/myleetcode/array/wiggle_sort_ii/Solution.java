package com.myleetcode.array.wiggle_sort_ii;

import java.util.Arrays;

class Solution {
    public void wiggleSort(int[] nums) {
        wiggleSortByQuickSelect(nums);
    }

    // solution: split by median, then merge
    // O(n) time and O(n) space solution by median partition
    // TC: O(N)
    // SC: O(N)
    // https://leetcode.com/problems/wiggle-sort-ii/discuss/77684/Summary-of-the-various-solutions-to-Wiggle-Sort-for-your-reference
    // https://leetcode.com/problems/wiggle-sort-ii/discuss/77680/Clear-Java-O(n)-avg-time-and-O(n)-space-solution-using-3-way-partition
    /*
The basic idea is to first select the kth smallest element, where k is the half the size (if size is even) or half the size+1 (if size is odd). So the array is partitioned into two halves, for even size, left half size==right half size, for odd size, left half size==right half size+1.
Then iterate back from left and right half, put each value into original array.

For example, [1, 5, 1, 1, 6, 4], after select kth smallest element, it becomes [1,1,1,5,6,4] with median index of 2. For the left half is [1,1,1], right half is [5,6,4]. After merge, it becomes, 1,4,1,6,1,5.

Same for [4,5,5,6], after select kth smallest , it becomes [4,5,5,6] with left half [4,5] and right half [5,6], merge it to be [5,6,4,5].
    */
    private void wiggleSortByQuickSelect(int[] nums){
        if(nums == null || nums.length == 0){
            return;
        }

        int len = nums.length;
        // we need keep the original nums array, because we need the relative pos
        int[] copy = Arrays.copyOf(nums, len);

        int mid = (len + 1) >> 1;
        int midVal = findKthValue(nums, mid);

        // re-arrange the copy array: LessThanMedian;EqualsToMedian;LargerThanMedian
        int start = 0;
        int cur = 0;
        int end = len - 1;
        while(cur <= end){
            if (copy[cur] < midVal) {
                swap(copy, start, cur);

                start++;
                cur++;
            } else if (copy[cur] > midVal) {
                swap(copy, end, cur);

                end--;
            } else{
                cur++;
            }
        }

        // place by odd and even idx
        int j = 0;
        for(int i = mid - 1; i >= 0; i--){
            nums[j] = copy[i];

            j += 2;
        }

        j = 1;
        for(int i = len - 1; i >= mid; i--){
            nums[j] = copy[i];

            j += 2;
        }

    }

    private int findKthValue(int[] nums, int k){
        int len = nums.length;

        k--;

        int left = 0;
        int right = len - 1;
        while(left <= right){
            int median = partition(nums, left, right);

            if(median == k){
                return nums[median];
            }else if(median < k){
                left = median + 1;
            }else{
                right = median - 1;
            }
        }

        return nums[left];
    }

    private int partition(int[] nums, int left, int right){
        // for better performance, should choose random idx or use three-median method to choose the idx of pivot. for easy, use the end value.
        int pivot = nums[right];

        int cur = left;
        while(left <= right){
            if(nums[left] < pivot){
                swap(nums, left, cur);

                cur++;
            }

            left++;
        }
        swap(nums, cur, right);

        return cur;
    }

    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
