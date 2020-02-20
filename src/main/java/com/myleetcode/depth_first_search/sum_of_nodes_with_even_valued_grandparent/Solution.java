package com.myleetcode.depth_first_search.sum_of_nodes_with_even_valued_grandparent;

import com.myleetcode.utils.tree_node.TreeNode;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int sumEvenGrandparent(TreeNode root) {
        return sumEvenGrandparentByDFSAll(root);// more general, prefer
        // return sumEvenGrandparentByDFSWithParentAndGrandparent(root);
    }

    /*
    Also there's a BFS solution here, it's just like the sumEvenGrandparentByDFSWithParentAndGrandparent solution.
    https://leetcode.com/problems/sum-of-nodes-with-even-valued-grandparent/discuss/482991/Easy-BFS-solution-in-Java
    */

    /*
    DFS
    keep parent and grandparent node
    https://leetcode.com/problems/sum-of-nodes-with-even-valued-grandparent/discuss/477095/Easy-DFS-solution

    TC: O(N)
    SC: O(H)
    */
    public int sumEvenGrandparentByDFSWithParentAndGrandparent(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Result ret = new Result();
        dfsWithParentAndGrandparent(root, null, null, ret);

        return ret.sum;
    }

    void dfsWithParentAndGrandparent(TreeNode current, TreeNode parent, TreeNode grandParent, Result ret) {
        if (current == null) {
            return;
        }

        if (grandParent != null && grandParent.val % 2 == 0) {
            ret.sum += current.val;
        }

        // ( newChild, parent, GrandParent)
        dfsWithParentAndGrandparent(current.left, current, parent, ret);
        dfsWithParentAndGrandparent(current.right, current, parent, ret);
    }

    /*
    DFSAll
    DFS all nodes, use every node as root do a k-level DFS to check whether the root could act as a grand parent, here k is 2

    TC: O(kN) => O(2N) => O(N)
    SC: O(H)
    */
    private int sumEvenGrandparentByDFSAll(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Result ret = new Result();
        preorder(root, ret);

        return ret.sum;
    }

    private void preorder(TreeNode curNode, Result ret) {
        if (curNode == null) {
            return;
        }

        if (curNode.val % 2 == 0) {
            kLevelDFS(curNode, 2, ret);
        }

        preorder(curNode.left, ret);
        preorder(curNode.right, ret);
    }

    private void kLevelDFS(TreeNode curNode, int k, Result ret) {
        if (curNode == null) {
            return;
        }

        // Have digged k levels and curNode is not null, so successful.
        if (k == 0) {
            ret.sum += curNode.val;
            return;
        }

        kLevelDFS(curNode.left, k - 1, ret);
        kLevelDFS(curNode.right, k - 1, ret);
    }

    private class Result {
        int sum;

        public Result() {
            this.sum = 0;
        }
    }

}
