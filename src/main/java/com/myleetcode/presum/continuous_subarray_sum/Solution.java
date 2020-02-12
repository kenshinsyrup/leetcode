package com.myleetcode.presum.continuous_subarray_sum;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        // return checkSubarraySumByPresum(nums, k);

        // return checkSubarraySumWithMath(nums, k);

        return checkSubarraySumWithPresumAndMap(nums, k);
    }

    private boolean checkSubarraySumWithPresumAndMap(int[] nums, int k) {
        // corner case 1: according to the question, the result's length has to be >=2
        if (nums == null || nums.length <= 1) {
            return false;
        }
        // corner case 2: nums.length == 2
        if (nums.length == 2) {
            if (nums[0] + nums[1] == 0 || (k != 0 && (nums[0] + nums[1]) % k == 0)) {
                return true;
            }
        }
        // corner case 3: k is 0
        if (k == 0) {
            for (int i = 0; i < nums.length - 1; i++) {
                if (nums[i] == 0 && nums[i + 1] == 0) {
                    return true;
                }
            }
            return false;
        }

        /*
        i-remainderIdxMap.get(remainder)>=2 needs the nums length at least 3, this is the reason why we check length 2 seperately above
        */
        int len = nums.length;
        int[] presum = new int[len + 1];
        for (int i = 0; i < len; i++) {
            presum[i + 1] = presum[i] + nums[i];
        }

        Map<Integer, Integer> remainderIdxMap = new HashMap<>();
        for (int i = 0; i < len; i++) {
            int remainder = presum[i + 1] % k;

            if (remainder == 0) { // [0:i] sum is divisible by k
                if (i - 0 + 1 >= 2) {
                    return true;
                }
            }

            if (remainderIdxMap.containsKey(remainder)) {
                if (i - remainderIdxMap.get(remainder) >= 2) { // at least two elems
                    return true;
                }
            } else {
                // only when remainderIdxMap not ontains remainder we update the i. otherwise, if remainderIdxMap contains remainder but i-remainderIdxMap.get(remainder)<=1, we dont update idx, so we keep the left most idx of such key
                remainderIdxMap.put(remainder, i);
            }
        }

        return false;
    }

    // optimize: Math
    // https://leetcode.com/problems/continuous-subarray-sum/discuss/99499/Java-O(n)-time-O(k)-space
    // TC: O(N)
    // SC: O(N)
    /*
a+(n*x))%x is same as (a%x)

For e.g. in case of the array [23,2,4,6,7], k is 6, the running sum is [23,25,29,35,42] and the remainders are [5,1,5,5,0]. We got remainder 5 at index 0, index 2 and at index 3. That means, in between index 0 and 2 we must have added a number which is multiple of the k. between index 0 and 3 is the same. between index 2 and 3 is the same too but 3-2==1 means a this subarray sum is a elem in nums, so we know this not satisfy the restriction "at least 2 elems in nums"
    */
    // since the result need at least 2 elems to sum, so we check the nums.length == 2 especially in this solution
    private boolean checkSubarraySumWithMath(int[] nums, int k) {
        // corner case 1: according to the question, the result's length has to be >=2
        if (nums == null || nums.length <= 1) {
            return false;
        }
        // corner case 2: nums.length == 2
        if (nums.length == 2) {
            if (nums[0] + nums[1] == 0 || (k != 0 && (nums[0] + nums[1]) % k == 0)) {
                return true;
            }
        }
        // corner case 3: k is 0
        if (k == 0) {
            for (int i = 0; i < nums.length - 1; i++) {
                if (nums[i] == 0 && nums[i + 1] == 0) {
                    return true;
                }
            }
            return false;
        }

        /*
        i-remainderIdxMap.get(remainder)>=2 needs the nums length at least 3, this is the reason why we check length 2 seperately above
        */
        int len = nums.length;
        Map<Integer, Integer> remainderIdxMap = new HashMap<>();
        remainderIdxMap.put(0, 0);
        int sum = 0;
        int remainder = 0;
        for (int i = 0; i < len; i++) {
            sum += nums[i];
            remainder = sum % k;

            if (remainderIdxMap.containsKey(remainder)) {
                if (i - remainderIdxMap.get(remainder) >= 2) { // at least two elems
                    return true;
                }
            } else {
                // only when remainderIdxMap not ontains remainder we update the i. otherwise, if remainderIdxMap contains remainder but i-remainderIdxMap.get(remainder)<=1, we dont update idx, so we keep the left most idx of such key
                remainderIdxMap.put(remainder, i);
            }
        }

        return false;
    }

    // intuition:
    // use presums array to get subarray sum in O(1), if a sum%k is 0 and the sum is a subarray which's length is at least then true. O(N^2)
    // TC: O(N^2)
    // SC: O(N)
    private boolean checkSubarraySumByPresum(int[] nums, int k) {
        // corner case 1: according to the question, the result's length has to be >=2
        if (nums == null || nums.length <= 1) {
            return false;
        }
        // corner case 2: nums.length == 2
        if (nums.length == 2) {
            if (nums[0] + nums[1] == 0 || (k != 0 && (nums[0] + nums[1]) % k == 0)) {
                return true;
            }
        }
        // corner case 3: k is 0
        if (k == 0) {
            for (int i = 0; i < nums.length - 1; i++) {
                if (nums[i] == 0 && nums[i + 1] == 0) {
                    return true;
                }
            }
            return false;
        }

        // presums array
        int len = nums.length;
        int[] presums = new int[len + 1];
        for (int i = 1; i < presums.length; i++) {
            presums[i] = presums[i - 1] + nums[i - 1];
        }

        // try all continuous subarray sum
        for (int i = 0; i < presums.length; i++) {
            for (int j = 0; j < i - 1; j++) {
                if ((presums[i] - presums[j]) % k == 0) {
                    return true;
                }
            }
        }

        return false;

    }
}