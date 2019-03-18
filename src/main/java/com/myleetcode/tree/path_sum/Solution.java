package com.myleetcode.tree.path_sum;

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
    public boolean hasPathSum(TreeNode root, int sum) {

        return pathSumByDFS(root, sum);
    }

    private boolean pathSumByDFS(TreeNode node, int target){
        if(node == null){
            return false;
        }

        // if(target == node.val) 这样是不对的，因为题目要求的是root-leaf，所以必须同时满足path sum与sum相等且node是leaf两个条件. 注意树为[1,2]，sum为1，leetcode认为这个是false，因为不存在一个root-leaf和为1的path,这个个树只有一个path 1-2
        /*
            1
           /
          2
        */
        // 树的逻辑结构不要认为包含null node,不存在1-null这个path
        if(target == node.val && node.left == null && node.right == null){
            return true;
        }

        return pathSumByDFS(node.left, target - node.val) ||
                pathSumByDFS(node.right, target - node.val);
    }
}
