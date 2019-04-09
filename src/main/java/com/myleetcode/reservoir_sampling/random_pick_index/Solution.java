package com.myleetcode.reservoir_sampling.random_pick_index;

import java.util.Random;

class Solution {

    int[] nums;
    Random random;

    // https://leetcode.com/problems/random-pick-index/discuss/88072/simple-reservoir-sampling-solution
    public Solution(int[] nums) {
        if(nums == null || nums.length == 0){
            return;
        }

        this.nums = nums;
        this.random = new Random();
    }

    public int pick(int target) {
        // return pickNum(this.nums, 0, this.nums.length - 1, target);// wrong

        return pickRandomIdx(target);
    }

    // TC: O(N)
    // SC: O(1)
    // the thought behind this is every time we find a target, we count++, then we get a random int in the range of 0 to count. here we make a choice, if we get 0 we update the idx, if not, we dont do anything. in this way, we do the choice everytime we encounter a target.
    /*
    Actually, that is a probability problem.
    If there are k target values in the array. For the 1st one, the probability of (idx == i) is 1.0; for the 2nd one, the probability of changing to (idx == i) is 1/2, then the value keeps the previous idx is 1/2; ..... for the kth one, the probability of changing to the new idx is 1/k, for the each of others, the prob of not changing the value is 1/(k-1) * (k-1)/k = 1/k.
    */
    private int pickRandomIdx(int target){
        int count = 0;
        int idx = 0;
        for(int i = 0; i < this.nums.length; i++){
            if(this.nums[i] == target){
                count++;
                int randomNum = random.nextInt(count);
                if(randomNum == 0){
                    idx = i;
                }
            }
        }
        return idx;
    }


    // BUT this is wrong, the problem is actually has the point at "RANDOM".
    // intuition: divide and conquer with early break? every time we brak the array into two parts, and recursive to find the num. this cost O(N)
    private int pickNum(int[] numArray, int startIdx, int endIdx, int target){
        // base case 1
        if(startIdx > endIdx){
            return -1;
        }
        // base case 2
        if(startIdx == endIdx){
            if(numArray[startIdx] == target){
                return startIdx;
            }
            return -1;
        }

        int midIdx = startIdx + (endIdx - startIdx) / 2;
        int left = pickNum(numArray, startIdx, midIdx, target);
        if(left != -1){
            return left;
        }

        int right = pickNum(numArray, midIdx + 1, endIdx, target);
        if(right != -1){
            return right;
        }

        return -1;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int param_1 = obj.pick(target);
 */
