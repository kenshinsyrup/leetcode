package com.myleetcode.tree.kth_smallest_element_in_a_bst;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.Stack;

public /**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    // 2 recursive
    // for kthSmallestByRecursive to count and record ans
    // better keep these two variables in a wrapper class
    /*
    private static int number = 0;
    private static int count = 0;
    */

    // 3 recursive with helper class withour global variable.
    /*
    class Ans{
        int count;
        int number;

        Ans(int k){
            count = k;
        }
    }
    */

    public int kthSmallest(TreeNode root, int k) {
        // Intuition: traverse inorder, keep counting, when k is 0 we get the node.val and this is the answer.
        // traverse a tree inorder, we have recursive and iterative way.

        // special case
        if(root == null || k < 0){
            return -1;
        }

        // 1 iterative
        return kthSmallestByIterative(root, k);

        // 2 recursive
        /*
        count = k;
        number = 0;
        kthSmallestByRecursive(root);
        return number;
        */

        // 3 recursive with helper class withour global variable.
        /*
        Ans ans = new Ans(k);
        kthSmallestByRecursiveWithHelpClass(root, ans);
        return ans.number;
        */
    }


    private int kthSmallestByIterative(TreeNode node, int k){
        // inorder , dfs
        Stack<TreeNode> nodeS = new Stack<TreeNode>();

        // init
        // left to leaf
        while(node != null){
            nodeS.push(node);
            node = node.left; // to the most left
        }

        while(k > 0 && !nodeS.isEmpty()){
            TreeNode n = nodeS.pop(); // get 'center' root
            k--;
            if(k == 0){
                return n.val;
            }

            // here we must get and check n.right before we push to stack. Otherwise, there's a possibility that n.right is null and we must not push null to stack because if there's null node in stack, the n.val above will encounter runtime error.
            TreeNode right = n.right; // get 'right' node of 'center' node
            while(right != null){
                nodeS.push(right);
                right = right.left; // continue go to the new subtree's most left
            }
        }

        return -1;

    }


    // 2 recursive
    /*
    private void kthSmallestByRecursive(TreeNode node){
        if(node == null){
            return;
        }

        kthSmallestByRecursive(node.left); // to the left most

        // process 'center'
        count--;
        if(count == 0){
            number = node.val;
            return;
        }

        kthSmallestByRecursive(node.right); // go to the 'right' node and then continue to the left most

    }
    */

    // 3 recursive with helper class withour global variable.
    /*
    private void kthSmallestByRecursiveWithHelpClass(TreeNode node, Ans ans){
        if(node == null){
            return;
        }

        kthSmallestByRecursiveWithHelpClass(node.left, ans); // to the left most

        // process 'center'
        ans.count--;
        if(ans.count == 0){
            ans.number = node.val;
            return;
        }

        kthSmallestByRecursiveWithHelpClass(node.right, ans); // go to the 'right' node and then continue to the left most

    }
    */

}
