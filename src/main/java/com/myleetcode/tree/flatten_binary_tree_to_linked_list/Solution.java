package com.myleetcode.tree.flatten_binary_tree_to_linked_list;

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
    public void flatten(TreeNode root) {
        // flattenByDFS(root);
        flattenByDFSII(root);
    }

    // we could optimize the last one, actually, we have reached the tail of left subtree and right subtree, so if we could return the tail node, then we dont neet to look up it again when we want to concatnate flatten trees
    // https://leetcode.com/problems/flatten-binary-tree-to-linked-list/discuss/268085/Divide-and-conquer-postorder-solution
    private void flattenByDFSII(TreeNode root){
        if(root == null){
            return;
        }

        flattenAndGetTail(root);
    }

    private TreeNode flattenAndGetTail(TreeNode curNode){
        // base
        if(curNode == null){
            return null;
        }

        // flatten left and right subtrees, and get their tail
        TreeNode leftTail = flattenAndGetTail(curNode.left);
        TreeNode rightTail = flattenAndGetTail(curNode.right);

        // if leftTail exists, so we need do concatnate
        if(leftTail != null){
            // assign curNode's right to leftTail.right
            leftTail.right = curNode.right;
            // assign curNode's left to right
            curNode.right = curNode.left;
            // set the left to null
            curNode.left = null;
        }

        // !!! if rightTail not null, then no matter we did the concatnate or not, the rightTail is the final tail, so we should first check and return this
        if(rightTail != null){
            return rightTail;
        }

        // then if rightTail not exist, and if leftTail exist, return it
        if(leftTail != null){
            return leftTail;
        }

        // if no rightTail or leftTail, then curNode is a tail
        return curNode;
    }

    // TC: O(N^2), not good, especially the while loop to find tail, not efficient
    // SC: O(H)
    // intuition: DFS
    // https://leetcode.com/problems/flatten-binary-tree-to-linked-list/discuss/37223/Share-my-accepted-recursive-solution-with-comments-Java
    // https://leetcode.com/problems/flatten-binary-tree-to-linked-list/discuss/36987/Straightforward-Java-Solution
    private void flattenByDFS(TreeNode root){
        if(root == null){
            return;
        }

        dfs(root);
    }

    private void dfs(TreeNode curNode){
        if(curNode == null){
            return;
        }

        // flatten left and right subtree
        dfs(curNode.left);
        dfs(curNode.right);

        // save current right for concatination
        TreeNode right = curNode.right;

        // if left subtree exists, concatnate
        if(curNode.left != null) {

            // step 1: concatinate root with left flatten subtree
            curNode.right = curNode.left;
            curNode.left = null; // set left to null to cut down the left subtree

            // step 2: move to the end of new added flatten subtree
            while(curNode.right != null){
                curNode = curNode.right;
            }

            // step 3: contatinate left flatten subtree with flatten right subtree
            curNode.right = right;
        }
    }
}
