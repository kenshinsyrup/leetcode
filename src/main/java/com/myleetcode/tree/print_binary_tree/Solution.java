package com.myleetcode.tree.print_binary_tree;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
public class Solution {
    public List<List<String>> printTree(TreeNode root) {
        // return printTreeByBFS(root);
        return printTreeByDFS(root);
    }

    /*
    BFS
    https://leetcode.com/problems/print-binary-tree/discuss/106269/Java-Iterative-Level-Order-Traversal-with-Queue

    !!! Most important part is get the idea, and know for each node, it should be at the idx: left + (right-left)/2

    It could be fairly easy when we made our first observation on the problem. For the output matrix, the number of rows is height of the tree. What about the number of columns?
    row = 1 => col = 1 = 2^1 - 1
    row = 2 => col = 3 = 2^2 - 1
    row = 3 => col = 7 = 2^3 - 1
    row = 4 => col = 15 = 2^4 - 1
    ...
    row = m => col = 2^m - 1
    This can be derived from the number of leaves of a full tree (i.e 2^(height - 1)) with spaces joined (i.e 2^(height - 1) - 1).

    Then we can fill the node in level by level. Another observation is we always print a node at the center of its subtree index range. What I mean is for the left or right child of a node, the subtree rooted at the child will use half of the indices of the node.
    root is at the center of left and right, say mid
    root.left (if not null) is at the center of left and mid - 1
    root.right (if not null) is at the center of mid + 1 and right

Then we can easily have our solution as we always keep track of the left and right of the node.


    TC: O(N)
    SC: O(N)
    */
    private List<List<String>> printTreeByBFS(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        // Init.
        int rowLen = getHeight(root);
        int colLen = (int) Math.pow(2, rowLen) - 1;
        List<List<String>> res = new ArrayList<>();
        for (int i = 0; i < rowLen; i++) {
            List<String> innerList = new ArrayList<>();
            for (int j = 0; j < colLen; j++) {
                innerList.add("");
            }
            res.add(innerList);
        }

        Deque<TreeNode> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(root);
        Deque<int[]> boundQueue = new ArrayDeque<>();
        boundQueue.offer(new int[]{0, colLen - 1});

        int rowIdx = 0;
        while (!nodeQueue.isEmpty()) {
            int size = nodeQueue.size();
            for (int i = 0; i < size; i++) {
                TreeNode curNode = nodeQueue.poll();
                int[] bound = boundQueue.poll();

                // Put cur node in correct position.
                int midIdx = bound[0] + (bound[1] - bound[0]) / 2;
                res.get(rowIdx).set(midIdx, String.valueOf(curNode.val));

                if (curNode.left != null) {
                    nodeQueue.offer(curNode.left);
                    boundQueue.offer(new int[]{bound[0], midIdx - 1});
                }
                if (curNode.right != null) {
                    nodeQueue.offer(curNode.right);
                    boundQueue.offer(new int[]{midIdx + 1, bound[1]});
                }
            }

            rowIdx++;
        }

        return res;

    }

    private int getHeight(TreeNode curNode) {
        if (curNode == null) {
            return 0;
        }

        return 1 + Math.max(getHeight(curNode.left), getHeight(curNode.right));
    }

    /*
    DFS, same idea with above BFS.

    TC: O(N)
    SC: O(H) if don't count the output List.
    */
    private List<List<String>> printTreeByDFS(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        // Init.
        int rowLen = getHeight(root);
        int colLen = (int) Math.pow(2, rowLen) - 1;
        List<List<String>> res = new ArrayList<>();
        for (int i = 0; i < rowLen; i++) {
            List<String> innerList = new ArrayList<>();
            for (int j = 0; j < colLen; j++) {
                innerList.add("");
            }
            res.add(innerList);
        }

        // Populate.
        dfs(root, res, 0, colLen - 1, 0);

        return res;
    }

    private void dfs(TreeNode curNode, List<List<String>> res, int left, int right, int rowIdx) {
        if (curNode == null) {
            return;
        }

        int mid = left + (right - left) / 2;
        res.get(rowIdx).set(mid, String.valueOf(curNode.val));

        dfs(curNode.left, res, left, mid - 1, rowIdx + 1);
        dfs(curNode.right, res, mid + 1, right, rowIdx + 1);
    }
}
