package com.myleetcode.tree.all_possible_full_binary_trees;

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

public class Solution {
    public List<TreeNode> allPossibleFBT(int N) {
        return allPossibleFBTByRecursion(N);
    }

    /*
    https://leetcode.com/problems/all-possible-full-binary-trees/discuss/216853/Java%3A-Easy-with-Examples

    TC: O(2^N)
    SC: O(2^N)
    */
    private List<TreeNode> allPossibleFBTByRecursion(int N) {
        if (N < 1) {
            return new ArrayList<>();
        }

        return buildFBTByRecursion(N);
    }

    private List<TreeNode> buildFBTByRecursion(int N) {
        List<TreeNode> fbtList = new ArrayList<>();

        // Base case 1.
        if (N % 2 == 0) {
            return fbtList;
        }
        // Base case 2.
        if (N == 1) {
            TreeNode root = new TreeNode(0);
            fbtList.add(root);
            return fbtList;
        }

        for (int leftNodes = 1; leftNodes < N; leftNodes++) {
            int rightNodes = (N - 1) - leftNodes; // left subtree consume leftNodes nodes, root consume 1 node, so right subtree consume N-1-leftNodes nodes.

            List<TreeNode> leftFbtList = buildFBTByRecursion(leftNodes);
            List<TreeNode> rightFbtList = buildFBTByRecursion(rightNodes);

            // Try all combinations with current root, left fbt list and right fbt list.
            for (TreeNode leftFBTRoot : leftFbtList) {
                for (TreeNode rightFBTRoot : rightFbtList) {
                    TreeNode root = new TreeNode(0);
                    root.left = leftFBTRoot;
                    root.right = rightFBTRoot;

                    fbtList.add(root);
                }
            }
        }

        return fbtList;

    }
}
