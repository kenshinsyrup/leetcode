package com.myleetcode.sliding_window.minimum_size_subarray_sum;

class Solution {
    public int minSubArrayLen(int s, int[] nums) {
        // return minSubArrayLenByPresum(s, nums); // Naive
        // return minSubArrayLenByPresumAndBS(s, nums); // follow up

        return minSubArrayLenBySlidingWindow(s, nums); // TC O(N)
    }

    // intuition:
    // first get the presum array of nums, then we could get any sum in range of [i,j] with it in O(1). Second try all combination of i, j to get the minimum subarray len.
    // TC O(N^2)
    // SC O(N)
    private int minSubArrayLenByPresum(int target, int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        // get the presum array of nums
        int len = nums.length;
        int[] presums = new int[len + 1];
        for(int i = 1; i < presums.length; i++){
            presums[i] = nums[i - 1] + presums[i - 1];
        }

        // find the minimum len of range
        int minLen = Integer.MAX_VALUE;
        for(int i = 0; i < presums.length; i++){
            // for(int j = 1; j < i; j++){
            //     if(presums[i] - presums[j] >= target){
            //         minLen = Math.min(minLen, i - j);
            //     }
            // }
            // the tricky part, we make j in range (i, 1], then when find the first j make the condition correct, we break.
            // let the j reduce from i-1 to 1, by this way we could stop when we find the first j make the condition correct, because we are looking for the min len of subarray, since every search the i is fixed, so the largest j is the ans for this i
            for(int j = i - 1; j >= 0; j--){
                if(presums[i] - presums[j] >= target){
                    minLen = Math.min(minLen, i - j);
                    break;
                }
            }
        }

        return minLen == Integer.MAX_VALUE? 0: minLen;
    }

    // follow up: within TC O(N * logN)
    // we find this condition if(presums[i] - presums[j] >= target), and we know presums is a sorted array, so we could rewrite this condition to presums[i] >= presums[j] + target, so we want to search the first i that make this condition correct when j is in range [1, i) and i in range [1, presums.len-1]
    private int minSubArrayLenByPresumAndBS(int target, int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        // get the presum array of nums
        int len = nums.length;
        int[] presums = new int[len + 1];
        for(int i = 1; i < presums.length; i++){
            presums[i] = nums[i - 1] + presums[i - 1];
        }

        // find the minimum len of range
        int minLen = Integer.MAX_VALUE;
        for(int j = 0; j < presums.length; j++){
            int curTarget = presums[j] + target;
            int i = binarysearch(presums, j, presums.length - 1, curTarget);
            if(j < i){//!!!
                minLen = Math.min(minLen, i - j);
            }
        }

        return minLen == Integer.MAX_VALUE? 0: minLen;
    }

    // to find the first i that presums[i] >= presums[j] + target
    private int binarysearch(int[] presums, int start, int end, int target){
        while(start <= end){
            int mid = start + (end - start) / 2;

            if(presums[mid] < target){
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }
        // no such i
        if(start == presums.length){
            return -1;
        }

        return start;
    }

    // https://leetcode.com/problems/minimum-size-subarray-sum/discuss/59078/Accepted-clean-Java-O(n)-solution-(two-pointers)/60387
    // https://leetcode.com/problems/minimum-window-substring/discuss/26808/Here-is-a-10-line-template-that-can-solve-most-'substring'-problems
    // TC: O(N)
    // SC: O(1)
    private int minSubArrayLenBySlidingWindow(int target, int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int left = 0;
        int minLen = Integer.MAX_VALUE;
        int sum = 0;
        for(int i = 0; i < nums.length; i++){
            sum += nums[i];

            while(sum >= target){
                minLen = Math.min(minLen, i - left + 1);

                // shrind window
                sum -= nums[left];
                left += 1;
            }
        }

        return minLen == Integer.MAX_VALUE? 0: minLen;

    }

}
