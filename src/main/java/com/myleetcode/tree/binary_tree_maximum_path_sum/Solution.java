package com.myleetcode.tree.binary_tree_maximum_path_sum;

import com.myleetcode.utils.tree_node.TreeNode;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    class Result{
        int maxPathSum;
        public Result(int maxPathSum){
            this.maxPathSum = maxPathSum;
        }
    }

    // here's a more elegant one: https://leetcode.com/problems/binary-tree-maximum-path-sum/discuss/39875/Elegant-Java-solution

    // TC: O(N)
    // SC: O(N)
    public int maxPathSum(TreeNode root) {
        if(root == null){
            return 0;
        }

        Result ret = new Result(Integer.MIN_VALUE);// !!! must use the min as init value

        dfs(root, ret);

        return ret.maxPathSum;
    }

    // intuition: Preorder Tree Traversal
    // DFS to solve it: since path is parent-child connections, we could get left and right subtree's sum respectively, then we choose the max(cur, cur+left, cur+right, cur+left+right), this is the cur tree's max path sum, then we update the global maxSum in the Result class
    private int dfs(TreeNode curNode, Result ret){
        // base, if null then sum is 0
        if(curNode == null){
            return 0;
        }

        // get left and right subtree path sum respectively
        int leftBranchSum = dfs(curNode.left, ret);
        int rightBranchSum = dfs(curNode.right, ret);

        // !!! use current node as root, we get the max path sum, there are 4 candidates: curNode.val; curNode.val+left; curNode.val+right; curNode.val+left+right
        // !!! dont write like this: curPathSum = Math.max(curPathSum, curPathSum + leftBranchSum), path sum should be curNode.val + left/right path sum
        int curPathSum = curNode.val;
        curPathSum = Math.max(curPathSum, curNode.val + leftBranchSum);
        curPathSum = Math.max(curPathSum, curNode.val + rightBranchSum);
        curPathSum = Math.max(curPathSum, curNode.val + leftBranchSum + rightBranchSum);
        // update the global max
        ret.maxPathSum = Math.max(ret.maxPathSum, curPathSum);

        // !!! to return, we should not return the curPathSum, because that is the whole path using the curNode as root. when return, we only need the larger of left and right plus the curNode.val, ie use one branch and curNode to act as a branch of parent root
        // act as a branch
        int branchSum = curNode.val;
        branchSum = Math.max(branchSum, curNode.val + leftBranchSum);
        branchSum = Math.max(branchSum, curNode.val + rightBranchSum);
        return branchSum;
    }

}
