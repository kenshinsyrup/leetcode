package com.myleetcode.two_pointers.two_sum_less_than_k;

import java.util.Arrays;

class Solution {

  public int twoSumLessThanK(int[] A, int K) {
    return twoSumLessThanKByTwoPointers(A, K);
  }

  /*
  https://leetcode.com/problems/two-sum-less-than-k/discuss/322931/Java-Sort-then-push-from-two-ends.
  Intuition:
  sort the nums array
  use two pointers to move towards each other, to find the max ret that is less than K

  here there's a trap that problem needs i<j, but actually it does not need the i and j, so even we sort the nums, and if we guarantee the leftP not equals to rightP, then we meet this requirement.

  TC: O(N*logN)
  SC: O(1)
  */
  private int twoSumLessThanKByTwoPointers(int[] nums, int K) {
    if (nums == null || nums.length <= 1) {
      return -1;
    }

    // 1. sort nums
    Arrays.sort(nums);

    // 2. two pointers window
    int len = nums.length;
    int leftP = 0;
    int rightP = len - 1;
    int ret = Integer.MIN_VALUE;
    while (leftP < rightP) {
      int curSum = nums[leftP] + nums[rightP];

      if (curSum < K) {
        ret = Math.max(ret, curSum);
        leftP++;
      } else {
        rightP--;
      }
    }

    return ret == Integer.MIN_VALUE ? -1 : ret;

  }
}
