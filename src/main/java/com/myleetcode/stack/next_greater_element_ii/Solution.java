package com.myleetcode.stack.next_greater_element_ii;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

class Solution {
    public int[] nextGreaterElements(int[] nums) {
        // return nextGreaterElementsByStack(nums);
        return nextGreaterElementsByStackII(nums);
    }

    // https://leetcode.com/problems/next-greater-element-ii/discuss/98273/Java-10-lines-and-C%2B%2B-12-lines-linear-time-complexity-O(n)-with-explanation

    // TC: O(N)
    // SC: O(N)
    // 这个解法是可以优化的，我们并不需要真的去操作一个二倍的doubleNum和一个二倍的ret，因为我们需要的doubleNum中的值，可以通过对i取%len来从nums中取到
    // 所以同样的逻辑，可以改写一下省去doubleNum数组并且只使用长度与nums相同的ret
    private int[] nextGreaterElementsByStackII(int[] nums){
        if(nums == null || nums.length == 0){
            return new int[0];
        }

        int len = nums.length;
        int[] ret = new int[len];
        Arrays.fill(ret, -1);

        // 这次我们用stack暂时存储num的index，因为index是唯一的
        Deque<Integer> numIdxStack = new ArrayDeque<>();
        // 我们扩展一个二倍数组出来，然后直接求取这个数组的next greater，那么思路就和496相同了,但是由于doubleNum是对于nums的二倍复制，所以没必要真的分配一个数组，只需要对i取%len来从nums中取值即可
        for(int i = 0; i < len * 2; i++){
            int idxInNums = i % len;
            int num = nums[idxInNums];

            while(!numIdxStack.isEmpty() && num > nums[numIdxStack.peek()]){
                ret[numIdxStack.pop()] = num;
            }

            numIdxStack.push(idxInNums);
        }

        return ret;
    }


    // TC: O(N)
    // SC: O(N)
    // intuition: use stack like 496. Next Greater Element I.
    // the first difference: BUT this proble may have duplicates, so we could use the stack to keep the index of the ascending subsequence values.
    // the second difference: this is a circle array problem, for this style problem, the tipical solution is to expand it to two times of it. if we make a doubleNums array by concatenating two copies of nums, this proble will be same as the 496
    private int[] nextGreaterElementsByStack(int[] nums){
        if(nums == null || nums.length == 0){
            return new int[0];
        }

        int len = nums.length;
        // 我们扩展一个二倍数组出来，然后直接求取这个数组的next greater，那么思路就和496相同了
        int[] doubleNum = new int[len*2];
        for(int i = 0; i < len; i++){
            doubleNum[i] = nums[i];
            doubleNum[len + i] = nums[i];
        }

        int[] ret = new int[len*2];
        Arrays.fill(ret, -1);

        // 这次我们用stack暂时存储num的index，因为index是唯一的
        Deque<Integer> numIdxStack = new ArrayDeque<>();
        for(int i = 0; i < len * 2; i++){
            int num = doubleNum[i];

            while(!numIdxStack.isEmpty() && num > doubleNum[numIdxStack.peek()]){
                ret[numIdxStack.pop()] = num;
            }

            numIdxStack.push(i);
        }

        // 返回一半的ret
        return Arrays.copyOfRange(ret, 0, len);
    }

}
