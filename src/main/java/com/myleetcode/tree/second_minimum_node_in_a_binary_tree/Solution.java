package com.myleetcode.tree.second_minimum_node_in_a_binary_tree;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

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
    public int findSecondMinimumValue(TreeNode root) {
        return findSecondMinimumValueByIterativeDFS(root);
    }

    // intuition: find all leaves, keep two value, one is the min and one is the second min, when find:1 node.val samller than min, we should make second min = min and min = node.val;2 node.val smaller than second min but not min, then second min = node.val; 3 node.val bigger than secondMin, nothing happen
    // hwo to konw we get the secondMin? AKA, how do we know we have not less than 2 leaves? we could check the min and secondMin values, because we always change min first, so if at last secondMin is not changed, then must has no more one leaf.
    // 这个要有两个值，所以用iterative的dfs写一下试试
    // TC: O(N)
    // SC: O(N)
    private int findSecondMinimumValueByIterativeDFS(TreeNode root){
        if(root == null){
            return -1;
        }

        // min and second min
        int min = Integer.MAX_VALUE;
        int secondMin = Integer.MAX_VALUE;

        // stack for dfs
        Deque<TreeNode> nodeStack = new ArrayDeque<>();

        nodeStack.push(root);
        while(!nodeStack.isEmpty()){
            TreeNode curNode = nodeStack.pop();

            // leaf
            if(curNode.left == null && curNode.right == null){
                if(curNode.val < min){
                    int temp = min;
                    min = curNode.val;
                    secondMin = temp;
                }else if(curNode.val != min && curNode.val < secondMin){
                    // !!! 这个地方是个坑，我们要注意检查curNode.val ！= min才能在curNode.val < secondMin时去更新secondMin为curNode.val，否则，如果只检查curNode.val < secondMin,那么当curNode.val与min相等时肯定满足条件，而这样是不对的，我们需要secondMin<min，二者不能相等，因为有要求如果没有合法secondMin需要输出-1
                    secondMin = curNode.val;
                }
            }

            if(curNode.left != null){
                nodeStack.push(curNode.left);
            }
            if(curNode.right != null){
                nodeStack.push(curNode.right);
            }
        }

        return secondMin == Integer.MAX_VALUE? -1: secondMin;

    }
}
