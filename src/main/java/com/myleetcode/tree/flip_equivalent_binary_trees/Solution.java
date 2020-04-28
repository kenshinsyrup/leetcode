package com.myleetcode.tree.flip_equivalent_binary_trees;

import com.myleetcode.utils.tree_node.TreeNode;

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
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        return flipEquivByRecursion(root1, root2);
    }

    private boolean flipEquivByRecursion(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return true;
        }
        if (root1 == null || root2 == null) {
            return false;
        }

        return flipAndCheck(root1, root2);

    }

    private boolean flipAndCheck(TreeNode curNode1, TreeNode curNode2) {
        if (curNode1 == null && curNode2 == null) {
            return true;
        }
        if (curNode1 == null || curNode2 == null) {
            return false;
        }

        if (curNode1.val != curNode2.val) {
            return false;
        }

        // Check subtrees.
        if (flipAndCheck(curNode1.left, curNode2.left) && flipAndCheck(curNode1.right, curNode2.right)) {
            return true;
        }

        // Try flip.
        if (flipAndCheck(curNode1.left, curNode2.right) && flipAndCheck(curNode1.right, curNode2.left)) {
            TreeNode tmp = curNode2.left;
            curNode2.left = curNode2.right;
            curNode2.right = tmp;

            return true;
        }

        return false;
    }
}
