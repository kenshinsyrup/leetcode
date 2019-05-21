package com.myleetcode.tree.sum_root_to_leaf_numbers;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayList;
import java.util.List;

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
    public int sumNumbers(TreeNode root) {
        return sumNumbersByDFS(root);
    }

    // TC: O(N), N is the total node #
    // SC: O(N)
    // intuition: Preorder Tree Traversal Problem
    // we could use a List to collect all pathNum, which is generated when traverse the tree
    private int sumNumbersByDFS(TreeNode root){
        if(root == null){
            return 0;
        }

        List<Integer> pathNumList = new ArrayList<>();
        dfs(root, 0, pathNumList);

        int sum = 0;
        for(int val: pathNumList){
            sum += val;
        }

        return sum;
    }


    private void dfs(TreeNode curNode, int parentVal, List<Integer> pathNumList){
        // wrong, this will have all pathNum double times into List
        // if(curNode == null){
        //     pathNumList.add(parentVal);
        //     return;
        // }

        // !!! base case, if null must return, because some nodes may have only one child
        if(curNode == null){
            return;
        }

        // generate curVal
        parentVal = parentVal * 10 + curNode.val;

        // if current is leaf
        // !!! we must add the val to List when we are exactly at the leaf node, if we do this in the leaf's children ie the two null node, we will get all pathNum double times into the List. Just like the comment codes above
        if(curNode.left == null && curNode.right == null){
            pathNumList.add(parentVal);
            return;
        }

        // left and right subtree explore
        dfs(curNode.left, parentVal, pathNumList);
        dfs(curNode.right, parentVal, pathNumList);
    }
}
