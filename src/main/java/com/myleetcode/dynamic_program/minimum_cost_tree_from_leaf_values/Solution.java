package com.myleetcode.dynamic_program.minimum_cost_tree_from_leaf_values;

class Solution {
    public int mctFromLeafValues(int[] arr) {
        return mctFromLeafValuesByRecursiveDP(arr);
    }

    /*
    N
    There's O(N) solution for this problem: https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/discuss/339959/One-Pass-O(N)-Time-and-Space
    */
    /*
    Recursive DP:
    https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/discuss/340033/C%2B%2B-with-comments
    For a node, when it act as a root, the MCT for it is: Min(MinLeftSubTreeMC + MinRightSubTreeMC + (MaxLeftLeaf*MaxRightLeaf)), actually it's Min(LeftSubTreeMC + RightSubTreeMC + curNodeVal). And we know the current node's val is (MaxLeftLeaf*MaxRightLeaf).

    TC: O(N^3)
    SC: O(N^2)
    */
    private int mctFromLeafValuesByRecursiveDP(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int len = arr.length;
        int[][] memo = new int[len][len];

        return recursiveDP(arr, 0, len - 1, memo);
    }

    private int recursiveDP(int[] arr, int startIdx, int endIdx, int[][] memo) {
        // Base case, leaf node has no MCT.
        if (startIdx >= endIdx) {
            return 0;
        }

        // Base case, already caculated this range.
        if (memo[startIdx][endIdx] != 0) {
            return memo[startIdx][endIdx];
        }

        // Caculate current range. Here we cut the range to two parts and try all the possibilities.
        // Let's see, if we cut at i, then left subtree is [startIdx:i] and right subtree is [i+1:end], here we must be careful to avoid overflow so i should in range [start:end-1] so that i+1 is not larger than end.
        int ret = Integer.MAX_VALUE;
        for (int i = startIdx; i < endIdx; i++) {
            // Left subtree MCT.
            int leftMCT = recursiveDP(arr, startIdx, i, memo);

            // Right subtree MCT.
            int rightMCT = recursiveDP(arr, i + 1, endIdx, memo);

            // Caculate the curNode value.
            int maxLeftLeaf = 0;
            for (int j = startIdx; j <= i; j++) {
                maxLeftLeaf = Math.max(maxLeftLeaf, arr[j]);
            }
            int maxRightLeaf = 0;
            for (int j = i + 1; j <= endIdx; j++) {
                maxRightLeaf = Math.max(maxRightLeaf, arr[j]);
            }
            int curVal = maxLeftLeaf * maxRightLeaf;

            ret = Math.min(ret, curVal + leftMCT + rightMCT);
        }

        memo[startIdx][endIdx] = ret;
        return ret;
    }
}