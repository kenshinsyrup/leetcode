package com.myleetcode.tree.lowest_common_ancestor_of_a_binary_search_tree;

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
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // special case
        if(root == null || p == null || q == null){
            return root;
        }

        return lowestCommonAncestorByRecursion(root, p, q);
    }

    // TC: O(H), H is the height of BST, recursion height is H, every recursion's operation is O(1), in worst case, H is N, N is the total nodes number of BST
    // SC: O(H), recursion height is H
    // intuition: try to figure it in non-recursive way, dont figure out the way; then think about recursive, we may need a recurse func to check the ancestor based on the BST attributes: leftSub < root < rightSub and both leftSub and rightSub are BST
    // 首先这个题目给定p，q一定存在于root node为根的树上，对于BST上的LCA,特点就是p和q一定在其两边子树上，不会在同一边，如果在同一边，肯定不是LCA
    private TreeNode lowestCommonAncestorByRecursion(TreeNode node, TreeNode p, TreeNode q){

        int curVal = node.val;
        int pVal = p.val;
        int qVal = q.val;

        // p,q都在node左边子树
        if(pVal < curVal && qVal < curVal){
            return lowestCommonAncestorByRecursion(node.left, p, q);
        }else if(pVal > curVal && qVal > curVal){
            // p,q都在node右边子树
            return lowestCommonAncestorByRecursion(node.right, p, q);
        }else{
            // p,q在node左右两边(任意分布，p左q右或者p右q左)
            return node;
        }
    }
}
