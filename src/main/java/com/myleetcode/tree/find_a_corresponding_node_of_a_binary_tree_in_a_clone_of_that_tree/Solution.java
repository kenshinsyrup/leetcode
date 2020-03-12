package com.myleetcode.tree.find_a_corresponding_node_of_a_binary_tree_in_a_clone_of_that_tree;

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
    public final TreeNode getTargetCopy(final TreeNode original, final TreeNode cloned, final TreeNode target) {
        // return getTargetCopyByDFSPath(original, cloned, target);

        return getTargetCopyByDFSSimultaneously(original, cloned, target);
    }

    /*
    DFS
    do DFS the same way on both original and cloned tree simultaneously, find the node whichi equals to the target on the original tree, then the node on cloned tree is what we want.

    TC: O(N)
    SC: O(H)
    */
    private final TreeNode getTargetCopyByDFSSimultaneously(final TreeNode original, final TreeNode cloned, final TreeNode target) {
        if (original == null || cloned == null || target == null) {
            return null;
        }

        return dfs(original, cloned, target);
    }

    private TreeNode dfs(TreeNode originalCur, TreeNode clonedCur, TreeNode target) {
        if (originalCur == null) {
            return null;
        }

        if (originalCur == target) {
            return clonedCur;
        }

        TreeNode leftAns = dfs(originalCur.left, clonedCur.left, target);
        if (leftAns != null) {
            return leftAns;
        }

        TreeNode rightAns = dfs(originalCur.right, clonedCur.right, target);
        if (rightAns != null) {
            return rightAns;
        }

        return null;
    }


    /*
    DFS
    record path and search path

    TC: O(N)
    SC: O(N)
    */
    private final TreeNode getTargetCopyByDFSPath(final TreeNode original, final TreeNode cloned, final TreeNode target) {
        if (original == null || cloned == null || target == null) {
            return null;
        }

        String pathTarget = preorderPath(original, new StringBuilder(), target);
        return preorderDFS(cloned, new StringBuilder(), pathTarget);
    }

    private String preorderPath(TreeNode curNode, StringBuilder sb, TreeNode target) {
        if (curNode == null) {
            return null;
        }

        sb.append(curNode.val);

        if (curNode == target) {
            return sb.toString();
        }

        String leftAns = preorderPath(curNode.left, sb, target);
        if (leftAns != null) {
            return leftAns;
        }

        String rightAns = preorderPath(curNode.right, sb, target);
        if (rightAns != null) {
            return rightAns;
        }

        return null;
    }

    private TreeNode preorderDFS(TreeNode curNode, StringBuilder sb, String pathTarget) {
        if (curNode == null) {
            return null;
        }

        sb.append(curNode.val);

        // 4 possible answers:
        if (sb.toString().equals(pathTarget)) {
            return curNode;
        }

        TreeNode leftAns = preorderDFS(curNode.left, sb, pathTarget);
        if (leftAns != null) {
            return leftAns;
        }

        TreeNode rightAns = preorderDFS(curNode.right, sb, pathTarget);
        if (rightAns != null) {
            return rightAns;
        }

        return null;
    }
}