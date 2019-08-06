package com.myleetcode.tree.binary_search_tree.convert_bst_to_greater_tree;

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
    public TreeNode convertBST(TreeNode root) {
        // return convertBSTByReversedInOrder(root);
        return convertBSTByStack(root);
    }

    // reversed inorder traversal with stack
    // TC: O(N)
    // SC: O(N)
    private TreeNode convertBSTByStack(TreeNode root){
        if(root == null){
            return root;
        }

        int presum = 0;
        Deque<TreeNode> nodeStack = new ArrayDeque<>();

        // reversed inorder by stack
        TreeNode curNode = root;
        while(!nodeStack.isEmpty() || curNode != null){
            // go find the right most node
            while(curNode != null){
                nodeStack.push(curNode);
                curNode = curNode.right;
            }

            // pop out the right most node
            curNode = nodeStack.pop();

            // process
            curNode.val += presum;
            presum = curNode.val;

            // go process left subtree
            curNode = curNode.left;
        }

        return root;

    }

    // intuition: looks like a reversedInOrder tree traverse, we need first traverse right subtree, for each node in bst, we need get val from right node and plus it to ourself, and then traverse left subtree
    // TC: O(N)
    // SC: O(N)
    private TreeNode convertBSTByReversedInOrder(TreeNode root){
        if(root == null){
            return root;
        }

        reversedInOrder(root, 0); // init, root has no presum 0

        return root;
    }

    // draw a picture
    // first, the thought is to do a reversed inorder traversal
    // then, we should keep the presum val of cur node. in BST, since we are dont the accumulating plus, then all we should do is plus the right child val to cur node
    // then, if we meet a null node, ie a leaft node, we should return presum val to parent to let it plus with it
    // in cur node, we add the right val to us, then pass ourself val to left node, and the whole subtree rooted with curNode has the sum in curNode.left(if null, we said above, it's curNode.val, anyway, it's the value of reversedInOrder(curNode.left, curNode.val); ), so we return it to parent
    private int reversedInOrder(TreeNode curNode, int presum){
        // base
        if(curNode == null){
            return presum;
        }

        int right = reversedInOrder(curNode.right, presum);

        curNode.val += right;

        return reversedInOrder(curNode.left, curNode.val);
    }
}
