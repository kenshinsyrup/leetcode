package com.myleetcode.divide_and_conquer.kth_largest_element_in_an_array;

import java.util.Random;

class SolutionNew {
    public int findKthLargest(int[] nums, int k) {
        return findFirstKLargestByQuickSelect(nums, k);
    }

    // TC: O(N) to O(N^2)
    private int findFirstKLargestByQuickSelect(int[] nums, int k){
        // 和K-closest同样的思路
        // 区别只在于，因为要找的是严格的第K个大值，那么我们相当于找到K-1个大值，Kth是pivot，然后剩下的是小于pivot的值，那么pivot就是我们要的。注意这里的K是1based的而pivotIndex是0based，而我们要得到的是pivotIndex这个值，那么就需要K-1，也就是说找第2个大值，那么我们要找的是pivotIndex为1的情况

        // special case
        if(nums == null || nums.length == 0 || k < 1){
            return 0;
        }

        int len = nums.length;
        int begin = 0;
        int end = len - 1;

        int pivotIndex = 0;
        k -= 1;
        while (begin <= end) {
            pivotIndex = findPivotIndexByPartition(nums, begin, end);
            if(pivotIndex == k) {
                break;
            } else if (pivotIndex > k) {
                end = pivotIndex - 1;
            } else {
                begin = pivotIndex + 1;
            }
        }

        return nums[k];

    }

    private int findPivotIndexByPartition(int[] nums, int begin, int end){
        // choose pivot and make it at end
        Random random = new Random();
        int randomIdx = begin + random.nextInt(end - begin + 1);
        swap(nums, randomIdx, end);
        int pivot = nums[end];

        // pivotIdx points to a num that is the first num we dont know if it is larger than pivot. when we traverse the nums through, it will point to the first num that is larger than pivot, we swap this with the end num, thus, this is the pivot, the num before it are larger and after it are smaller
        int pivotIdx = begin;
        int curIdx = begin;
        while(curIdx <= end){
            if(nums[curIdx] > pivot){//!!! we need the kth largest elem in sorted order, so we must make the pivot the kth largest elem
                // curIdx is valid, swap it to the valid section
                swap(nums, pivotIdx, curIdx);

                // move the pivotIdx to next, points to the first not-known num
                pivotIdx++;
            }

            // move curIdx anyway
            curIdx++;
        }
        // swap the pivot to its position
        swap(nums, pivotIdx, end);

        return pivotIdx;
    }

    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
