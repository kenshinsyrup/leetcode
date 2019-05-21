package com.myleetcode.tree.inorder_successor_in_bst;

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
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        return inorderSuccessorByDFS(root, p);
    }

    // here is the predecessor and successor: https://leetcode.com/problems/inorder-successor-in-bst/discuss/72653/Share-my-Java-recursive-solution
    // here is a explaination with findSmallest approach: https://leetcode.com/problems/inorder-successor-in-bst/discuss/72700/Iterative-and-Recursive-Java-Solution-with-Detailed-Explanation

    // TC: O(H)
    // SC: O(H)
    private TreeNode inorderSuccessorByDFS(TreeNode root, TreeNode p){
        if(root == null || p == null){
            return null;
        }

        return findSuccesor(root, p);
    }

    private TreeNode findSuccesor(TreeNode curNode, TreeNode p){
        if(curNode == null){
            return null;
        }

        // on curNode's right
        if(curNode.val <= p.val){
            return inorderSuccessorByDFS(curNode.right, p);
        }else{
            // p is samller than curNode, means ans is curNode or on curNode's left
            // if not find, means curNode is ans
            TreeNode successor = inorderSuccessorByDFS(curNode.left, p);
            if(successor == null){
                return curNode;
            }
            return successor;
        }
    }

}
