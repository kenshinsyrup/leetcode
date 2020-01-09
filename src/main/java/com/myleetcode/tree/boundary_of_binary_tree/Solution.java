package com.myleetcode.tree.boundary_of_binary_tree;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayList;
import java.util.List;

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
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        return boundaryOfBinaryTreeByDFS(root);
    }

    /*
    DFS:
    https://leetcode.com/problems/boundary-of-binary-tree/discuss/101280/Java(12ms)-left-boundary-left-leaves-right-leaves-right-boundary

    N is total number of nodes.
    TC: O(N)
    SC: O(N)
    */
    private List<Integer> boundaryOfBinaryTreeByDFS(TreeNode root) {
        List<Integer> ret = new ArrayList<>();

        if (root == null) {
            return ret;
        }
        if (root.left == null && root.right == null) {
            ret.add(root.val);
            return ret;
        }

        // 1. Add root.
        ret.add(root.val);
        // 2. Add left boundary path nodes. (not including root and leaf)
        leftBoundary(root.left, ret);
        // 3. Add leaves.
        leaves(root, ret);
        // 4. Add right boundary path nodes. (not including root and leaf)
        rightBoundary(root.right, ret);

        return ret;

    }

    // Preorder because of anti-clockwise.
    private void leftBoundary(TreeNode curNode, List<Integer> ret) {
        // If curNode is null or leaf node, return.
        if (curNode == null) {
            return;
        }
        if (curNode.left == null && curNode.right == null) {
            return;
        }

        ret.add(curNode.val);

        // Keep in left most path.
        if (curNode.left != null) {
            leftBoundary(curNode.left, ret);
        } else {
            leftBoundary(curNode.right, ret);
        }

    }

    // Postorder because of anti-clockwise.
    private void rightBoundary(TreeNode curNode, List<Integer> ret) {
        // If curNode is null or leaf node, return.
        if (curNode == null) {
            return;
        }
        if (curNode.left == null && curNode.right == null) {
            return;
        }

        // Keep in right most path.
        if (curNode.right != null) {
            rightBoundary(curNode.right, ret);
        } else {
            rightBoundary(curNode.left, ret);
        }

        ret.add(curNode.val);
    }

    private void leaves(TreeNode curNode, List<Integer> ret) {
        if (curNode == null) {
            return;
        }

        if (curNode.left == null && curNode.right == null) {
            ret.add(curNode.val);
            return;
        }

        leaves(curNode.left, ret);
        leaves(curNode.right, ret);
    }

}
