package com.myleetcode.math.sort_transformed_array;

class Solution {
    public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
        return sortTransformedArrayByMath(nums, a, b, c);
    }

    // Math: Quadratic, Parabola
    // if a > 0, the largest two y derived from x in two ends of nums, so compare these two y and choose the larger one and put into most back available pos in ret
    // if a <= 0, the smallest two y also derived from x in two ends of nums, so compare thest two y and choose the smaller one and put into most front available pos in ret
    // https://leetcode.com/problems/sort-transformed-array/discuss/83322/Java-O(n)-incredibly-short-yet-easy-to-understand-AC-solution/87567
    private int[] sortTransformedArrayByMath(int[] nums, int a, int b, int c){
        if(nums == null || nums.length == 0){
            return new int[0];
        }

        int len = nums.length;
        int[] ret = new int[len];
        int start = 0;
        int end = len - 1;

        if(a > 0){
            for(int i = len - 1; i >= 0; i--){
                int leftR = quadraFunc(nums[start], a, b, c);
                int rightR = quadraFunc(nums[end], a, b, c);
                if(leftR > rightR){
                    ret[i] = leftR;
                    start++;
                }else{
                    ret[i] = rightR;
                    end--;
                }
            }
        }else{
            for(int i = 0; i < len; i++){
                int leftR = quadraFunc(nums[start], a, b, c);
                int rightR = quadraFunc(nums[end], a, b, c);
                if(leftR < rightR){
                    ret[i] = leftR;
                    start++;
                }else{
                    ret[i] = rightR;
                    end--;
                }
            }
        }

        return ret;

    }

    private int quadraFunc(int x, int a, int b, int c){
        return a * x * x + b * x + c;
    }
}
