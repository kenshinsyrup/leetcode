package com.myleetcode.tree.path_sum_iv;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public int pathSum(int[] nums) {
        return pathSumByMapAndDFS(nums);
    }


    // TC: O(N), Recursion N, represent tree with map N, totally O(N)
    // SC: O(N), recursion N, map N, totally O(N)
    // 所以这道题，最重要的是如何想到用一个更容易用的数据结构代替建树，建树太复杂了不切实际
    // 思路: https://leetcode.com/problems/path-sum-iv/discuss/106892/Java-solution-Represent-tree-using-HashMap
    // instead of really building a tree, we could use a map to represent a tree, we use the firt two digits of num in nums as key and the third digit of num as value. the key indicates the depth and position of a node.
    // for a num xy?, we know if it's a tree node, then its left child should be (x+1)(2*y - 1)? and right child should be (x+1)(2*y -1 + 1)? ie (x+1)(2*y)?.  注意这个公式是因为给的position这个digit是1base的，如果是0base，那么应该是左子(x+1)(2*y)?, 右子(x+1)(2*y +1)?
    private int pathSumByMapAndDFS(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        // use a map to represnet a tree, key is depth and position digit, value is val digit
        Map<Integer, Integer> treeNodeMap = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            int num = nums[i];
            int depthPosition = num / 10;
            int val = num % 10;

            treeNodeMap.put(depthPosition, val);
        }

        // traverse tree to get path sum
        Result ret = new Result(0);
        int rootDepthPositon = nums[0] / 10;
        pathSumDFS(treeNodeMap, rootDepthPositon, 0, ret);

        return ret.sum;

    }

    private void pathSumDFS(Map<Integer, Integer> treeNodeMap,int depthPosition, int pathSum, Result ret){
        // base case, check null
        if(treeNodeMap.get(depthPosition) == null){
            return;
        }

        // child
        int nextDepth = 10 * (depthPosition / 10 + 1);// !!! 记得乘10，因为他要占据的是depthPosition这个二位数字的十位
        int nextPosition = 2 * (depthPosition % 10);
        int left = nextDepth + nextPosition - 1;
        int right = left + 1;

        // add curNode to path
        pathSum += treeNodeMap.get(depthPosition);

        // leaf
        if(treeNodeMap.get(left) == null && treeNodeMap.get(right) == null){
            ret.sum += pathSum;
            return;
        }

        // left subtree
        pathSumDFS(treeNodeMap, left, pathSum, ret);
        // right subtree
        pathSumDFS(treeNodeMap, right, pathSum, ret);

        // remove curNode from path
        pathSum -= treeNodeMap.get(depthPosition);
    }

    class Result{
        int sum;
        Result(int sum){
            this.sum = sum;
        }
    }

    // intuition: stupid problem
    // we know if a node's indes is i, then its left child index is 2*i, right chiel index is 2*i+1. so we could build a tree level by level with given nums array. then we could caculate all path values form root to leaf
    // but build a real tree with TreeNode is so annoying with the given nums array. give up.

}
