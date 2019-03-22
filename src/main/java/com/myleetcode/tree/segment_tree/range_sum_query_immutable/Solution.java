package com.myleetcode.tree.segment_tree.range_sum_query_immutable;

class NumArray {

    int[] numArray;
    int[] segmentArray;

    public NumArray(int[] nums) {
        if(nums == null){
            return;
        }

        numArray = nums;
        segmentArray = new int[numArray.length + 1];
        for(int i = 0; i < nums.length; i++){
            segmentArray[i + 1] = segmentArray[i] + nums[i];
        }
    }

    public int sumRange(int i, int j) {
        if(j < i || i < 0 || j >= numArray.length){
            return -1;
        }

        return segmentArray[j + 1] - segmentArray[i];
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(i,j);
 */
