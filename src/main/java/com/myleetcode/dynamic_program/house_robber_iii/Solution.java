package com.myleetcode.dynamic_program.house_robber_iii;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.HashMap;
import java.util.Map;

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
    public int rob(TreeNode root) {
        return robByTopDown(root);
    }

    // good explaination: https://leetcode.com/problems/house-robber-iii/discuss/79330/Step-by-step-tackling-of-the-problem

    // this is a DP problem, but this is better to solve with Top Down approach, above link contains a elegant Bottom Up approach

    // TC: O(N)
    // SC: O(N)
    private int robByTopDown(TreeNode root){
        if(root == null){
            return 0;
        }

        Map<TreeNode, Integer> nodeMoneyMap = new HashMap<>();

        return robHouse(root, nodeMoneyMap);
    }

    private int robHouse(TreeNode curNode, Map<TreeNode, Integer> nodeMoneyMap){
        // base case, if null, 0 robbed
        if(curNode == null){
            return 0;
        }

        // memorize, if solved, directly return
        if(nodeMoneyMap.containsKey(curNode)){
            return nodeMoneyMap.get(curNode);
        }

        // rob: there are two scenerios:
        // 1 is we dont rob current root node, then our money is leftSubtree + rightleftSubtree
        // 2 is we rob current root node, then our money is left.leftSubtree + left.rightSubtree + right.leftSubtree + right.rightSubtree

        // 1 if dont rob current node
        int leftMoney = robHouse(curNode.left, nodeMoneyMap);
        int rightMoney = robHouse(curNode.right, nodeMoneyMap);

        // 2 if rob current node
        int grandLeftMoney = 0;
        if(curNode.left != null){
            int leftLeftMoney = robHouse(curNode.left.left, nodeMoneyMap);
            int leftRightMoney = robHouse(curNode.left.right, nodeMoneyMap);

            grandLeftMoney = leftLeftMoney + leftRightMoney;
        }
        int grandRightMoney = 0;
        if(curNode.right != null){
            int rightLeftMoney = robHouse(curNode.right.left, nodeMoneyMap);
            int rightRightMoney = robHouse(curNode.right.right, nodeMoneyMap);

            grandRightMoney = rightLeftMoney + rightRightMoney;
        }

        // current money robbed
        int curMoney = Math.max(leftMoney + rightMoney, curNode.val + grandLeftMoney + grandRightMoney);

        // put into map
        nodeMoneyMap.put(curNode, curMoney);

        return curMoney;

    }

}
