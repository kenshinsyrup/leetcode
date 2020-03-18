package com.myleetcode.sliding_window.binary_subarrays_with_sum;

public class Solution {
    public int numSubarraysWithSum(int[] A, int S) {
        // return numSubarraysWithSumBySlidingWindow(A, S);
        return numSubarraysWithSumBySlidingWindowII(A, S);
    }

    /*
    Sliding Window
    https://leetcode.com/problems/binary-subarrays-with-sum/discuss/186683/C%2B%2BJavaPython-Sliding-Window-O(1)-Space

    A tricky form of the At Most Problem.

    !!!Important, careful of the left<=right when shrink window, we should always check this in this problem to handle special cases like nums is [0,0,0,0] and target is 0.
    Although other problems may no such a need. We could do this check to all Sliding Window problems to make it's easier to hanld corner case.

    By the way, in the numSubarraysWithSumBySlidingWindow solution, the problem is similar. We should check left<=right when shrink window AND when count when the windowSum equals to target.

    TC: O(N)
    SC: O(1)
    */
    private int numSubarraysWithSumBySlidingWindowII(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (target < 0) {
            return 0;
        }

        return atMostTargetSubarrayNum(nums, target) - atMostTargetSubarrayNum(nums, target - 1);
    }

    private int atMostTargetSubarrayNum(int[] nums, int target) {
        int len = nums.length;

        int ret = 0;
        int windowSum = 0;
        int left = 0;
        int right = 0;
        while (right < len) {
            windowSum += nums[right];

            // left <= right to process situation like nums is [0,0,0,0,0] and target is 0.
            while (windowSum > target && left <= right) {
                windowSum -= nums[left];
                left++;
            }

            if (windowSum <= target) {
                ret += right - left + 1;
            }

            right++;
        }

        return ret;
    }

    /*
    Sliding Window with third pointer to count.
    https://leetcode.com/problems/binary-subarrays-with-sum/discuss/186999/java-solution-with-2-pointers-slide-windows.-O(1)-space.-short-version

    Important:
    how to count valid subarray number
    when shrink, make sure left<right

    Similar:
    930. Binary Subarrays With Sum
    1248. Count Number of Nice Subarrays

    TC: O(N)
    SC: O(1)
    */
    private int numSubarraysWithSumBySlidingWindow(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (target < 0) {
            return 0;
        }

        int len = nums.length;

        int count = 0;
        int windowSum = 0;
        int left = 0;
        int right = 0;
        while (right < len) {
            // Expand window p1.
            windowSum += nums[right];

            // Shrink window.
            while (windowSum > target && left <= right) {
                windowSum -= nums[left];

                left++;
            }

            // !!! Important how to count number of valid subarray.
            // If reach target, count.
            if (windowSum == target && left <= right) {
                // 1. current subarray [left:right] is valid, count 1.
                count++;

                // 2. for situations idx(init with left) is current points 0, then [idx+1:right] also valid. This is the third pointer for help.
                // for eg. [1,0,1,0,1], if left is 1 and right is 4, then current subarray [1:4] is valid, and [2:4] is also valid. Cound not make left move because that may cause losing answer.
                int i = left;
                while (i < right && nums[i] == 0) {
                    i++;

                    count++;
                }
            }

            // Expand window p2.
            right++;
        }

        return count;
    }
}
