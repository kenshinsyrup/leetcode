package com.myleetcode.tree.binary_search_tree.largest_bst_subtree;

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
    public int largestBSTSubtree(TreeNode root) {
        return largestBSTSubtreeByDFS(root);
    }

    // intuition:
    // https://leetcode.com/problems/largest-bst-subtree/discuss/78891/Share-my-O(n)-Java-code-with-brief-explanation-and-comments
    class Result{
        boolean isValid; // is a valid BST
        TreeNode leftBoundary; // low boundary node
        TreeNode rightBoundary; // up boundary node
        int size; // cur node as root, max size BST in subtree

        Result(boolean isValid, TreeNode leftBoundary, TreeNode rightBoundary, int size){
            this.isValid = isValid;
            this.leftBoundary = leftBoundary;
            this.rightBoundary = rightBoundary;
            this.size = size;
        }
    }

    // TC: O(N)
    // SC: O(H)
    private int largestBSTSubtreeByDFS(TreeNode root){
        if(root == null){
            return 0;
        }

        Result ret = verifyAndCount(root);

        return ret.size;
    }

    private Result verifyAndCount(TreeNode curNode){
        // !!! base, curNode null, isValid, boundary is null
        if(curNode == null){
            return new Result(true, null, null, 0);
        }

        Result leftRet = verifyAndCount(curNode.left);
        Result rightRet = verifyAndCount(curNode.right);

        // !!! curNode as root, valid BST. no duplicates in BST so must use < and >
        if(leftRet.isValid && rightRet.isValid){
            if((leftRet.rightBoundary == null || leftRet.rightBoundary.val < curNode.val) && (rightRet.leftBoundary == null || rightRet.leftBoundary.val > curNode.val)){
                TreeNode newLeftBoundary = curNode;
                if(leftRet.leftBoundary != null){
                    newLeftBoundary = leftRet.leftBoundary;
                }

                TreeNode newRightBoundary = curNode;
                if(rightRet.rightBoundary != null){
                    newRightBoundary = rightRet.rightBoundary;
                }

                return new Result(true, newLeftBoundary, newRightBoundary, leftRet.size + rightRet.size + 1);
            }
        }

        // curNode as root is not valid BST
        return new Result(false, null, null, Math.max(leftRet.size, rightRet.size));
    }

}
