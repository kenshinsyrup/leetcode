package com.myleetcode.array.product_of_array_except_self;

class Solution {
    public int[] productExceptSelf(int[] nums) {
        // special case
        if(nums == null || nums.length == 0){
            return new int[0];
        }

        // return productExceptSelfByMath(nums);
        return productExceptSelfByMathAndConstantSpace(nums);
    }

    // intuition: nested for loop, O(n^2). but the Note says must without division and in O(n).
    // prefix and suffix multiply?
    // then three for loop, firt use segment multiplication get a new array A. second use reverse segment multiplication get a new array B, then third A*B?
    // Caveat!!!: for head and tail element, its pre or suf fix multiply is 1, not 0 because this is multiply not sum

    // for the follow up
    // https://leetcode.com/problems/product-of-array-except-self/discuss/65622/Simple-Java-solution-in-O(n)-without-extra-space
    private int[] productExceptSelfByMathAndConstantSpace(int[] nums){
        int len = nums.length;
        int[] ret = new int[len]; // not count as extra space

        // prefix
        ret[0] = 1;
        for(int i = 1; i < len; i++){
            ret[i] = ret[i - 1] * nums[i - 1];
        }

        // right to keep the suffix product of i, and ret[i](product) = ret[i](prefix) * right(suffix); and then update right.
        int right = 1; // keep right value of ret[i]
        for(int i = len - 1; i >= 0; i--){
            ret[i] = right * ret[i]; // prefix prdocut of i(ret[i] for current i) * suffix product of i(right, actaully is 'i + 1' for current i)
            right = right * nums[i]; // suffix product of i(actually suffix product of "i+1" when right is used.)
        }

        return ret;
    }

    private int[] productExceptSelfByMath(int[] nums){
        int len = nums.length;
        int[] ret = new int[len]; // not count as extra space

        int[] preMul = new int[len];
        int[] sufMul = new int[len];

        // head and tail
        preMul[0] = 1;
        sufMul[len - 1] = 1;
        // prefix multiply
        for(int i = 1; i < len; i++){
            preMul[i] = preMul[i - 1] * nums[i - 1];
        }
        // suffix multiply
        for(int i = len -2; i >= 0; i--){
            sufMul[i] = sufMul[i + 1] * nums[i + 1];
        }
        // cross
        for(int i = 0; i < len; i++){
            ret[i] = preMul[i] * sufMul[i];
        }

        return ret;

    }
}