package com.myleetcode.divide_and_conquer.median_of_two_sorted_arrays;

class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        return findMedianSortedArraysByKth(nums1, nums2);
    }

    // this problem could be solved by the thought of: Find The Kth Smallest Number in a Sorted Array
    // actually it's easy to find the kth smallest num in sorted arry, we just return nums[k]
    // according the Median Num definition: the median is used for dividing a set into two equal length subsets, that one subset is always greater than the other
    // so the median num is
    //  average of idx (L1+L2)/2-1 num and idx (L1+L2)/2 num, if (L1+L2) is even
    // idx (L1+L2)/2 num, if (L1+L2) is odd

    // sol1: naive solution
    // two pointers, one for nums1, one for nums2, every time compare two pointers pointing num, choose the smaller one and make the corresponding increase. until we reach the median, based on the whole array lenght
    // TC: O(N + M)

    // sol2: Divide and Conquer
    // TC: O(log(M + N))
    // SC: O(log(M + N))
    // solution: https://leetcode.com/problems/median-of-two-sorted-arrays/discuss/2496/Concise-JAVA-solution-based-on-Binary-Search
    // we know how to find a the kth(1based) smallest num in a unsorted array, we use the findKth solution
    // now, we could change it a little to solve this problem. We want to find the kth(1based) elem in finalArray, the naive solution is compare the two sorted array's elem one by one, we could improve it by compare group by group
    private double findMedianSortedArraysByKth(int[] nums1, int[] nums2){
        // first check special cases:
        // special case1, nums1 and nums2 both invalid
        if(nums1 == null && nums2 == null || nums1.length == 0 && nums2.length == 0){
            return 0.0;
        }

        // special case2, nums1 is invalid
        if(nums1 == null || nums1.length == 0){
            int len2 = nums2.length;
            if(len2 %2 == 0){
                return (nums2[(len2 - 1) / 2] + nums2[len2 / 2]) / 2.0;
            }else{
                return 1.0 * nums2[len2 / 2];
            }
        }

        // special case3, nums2 is invalid
        if(nums2 == null || nums2.length == 0){
            int len1 = nums1.length;
            if(len1 %2 == 0){
                return (nums1[(len1 - 1) / 2] + nums1[len1 / 2]) / 2.0;
            }else{
                return 1.0 * nums1[len1 / 2];
            }
        }

        int len1 = nums1.length;
        int len2 = nums2.length;

        // k is the kth num in whole array we want to find, 1 based
        // so we could say we want to choose smallest k num from nums1 and nums2

        // if the whole array length is odd
        // median idx is (len1+len2)/2, so k is medianIdx+1, ie (len1+len2)/2+1
        if((len1 + len2) % 2 != 0){
            int k = (len1 + len2) / 2 + 1;
            return findKth(nums1, 0, nums2, 0, k) * 1.0;
        }

        // if whole array length is even
        // "median" left num idx is (len1+len2)/2-1, right num idx is (len1+len2)/2
        // so we try to find the leftK is leftIdx+1, ie (len1+len2)/2-1+1, rightK is rightIdx+1,ie (len1+len2)/2+1
        int leftK = (len1 + len2) / 2 - 1 + 1;
        int rightK = (len1 + len2) / 2 + 1;
        int leftNum = findKth(nums1, 0, nums2, 0, leftK);
        int rightNum = findKth(nums1, 0, nums2, 0, rightK);
        return (leftNum + rightNum) / 2.0;
    }

    // nums1 and nums2 are sorted array, kth is the kth num in the combined and sorted array, k is 1 based.
    // we try to find the kth(1based) elem in the whole array, but we have no the whole array, only has two sorted subarray, so we assume that half k is in the nums1 and half k is in the nums2
    // then, we try to find the half k num on each subarray, here we should keep in mind that half k may overflow our subarray, so when we try to find the halfKth elem in subarray, we use this startIdx+Math.min(k/2, len)-1
    // after we get the halfKth elem in each subarray, let's say they are candidateInNums1 and candidateInNums2, then we compare these two nums, by they we are actually compare two group(range nums1[start1:candidateInNums1Idx] and range nums2[start2:candidateInNums2Idx])
    // !!! during recursion, k is keep updated
    // this func find the kth(1based) elem in the whole array nums1+nums2, size is k
    private int findKth(int[] nums1, int start1, int[] nums2, int start2, int size){
        // 1. first check special cases:
        // special case1
        // If nums1 is exhausted, return kth number in nums2
        if(start1 >= nums1.length){
            return nums2[start2 + size - 1];
        }
        // special case2
        // If nums2 is exhausted, return kth number in nums1
        if(start2 >= nums2.length){
            return nums1[start1 + size - 1];
        }

        // 2. base case, size is 1
        // if nums1 and nums2 are both not exhausted, if k is already 1, then the min value of nums1[start1] and nums2[start] is the kth(1th) num in the combined array
        if(size == 1){
            if(start1 >= nums1.length){
                return nums2[start2];
            }
            if(start2 >= nums2.length){
                return nums1[start1];
            }
            return Math.min(nums1[start1], nums2[start2]);
        }

        // 3. normal case, nums1 and nums2 both not exhausted, k is more than 1, we discard the invalid half every time:

        // 3.1 try to get halfKth elem in nums1, if out of boundary, use the last elem of nums1
        // candidateNum1Idx and candidateNum1
        int candidateNum1Idx = start1 + Math.min(size/2, nums1.length) - 1;
        int candidateNum1 = nums1[candidateNum1Idx];

        // 3.2 try to get halfKth elem in nums2, if out of boundary, use the last elem of nums2
        // candidateNum2Idx and candidateNum2
        int candidateNum2Idx = start2 + Math.min(size/2, nums2.length) - 1;
        int candidateNum2 = nums2[candidateNum2Idx];

        // 3.3if candidateNum1 < candidateNum2, means the left of candidateNum1(including) is impossilbe to be the kth elem in the combined array; otherwise means the left of candidateNum2(including) is impossible.
        // !!! when update size, we use the size minus the discarded part length, means we stil has this num elems to find, this is the new kth, this is the correct way. dont use size/2 or sth like that, because nums1 or nums2 may dont have so many elems in its range[start:len-1], menas we may did not discard that many.
        if(candidateNum1 < candidateNum2){
            return findKth(nums1, candidateNum1Idx + 1, nums2, start2, size - (candidateNum1Idx - start1 + 1));
        }else{
            return findKth(nums1, start1, nums2, candidateNum2Idx + 1, size - (candidateNum2Idx - start2 + 1));
        }
    }
}