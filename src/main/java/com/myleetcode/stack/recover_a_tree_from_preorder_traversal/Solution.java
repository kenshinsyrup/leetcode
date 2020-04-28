package com.myleetcode.stack.recover_a_tree_from_preorder_traversal;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

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
    public TreeNode recoverFromPreorder(String S) {
        return recoverFromPreorderByStack(S);
    }

    /*
    Stack is very intuitive.
    */
    private TreeNode recoverFromPreorderByStack(String str) {
        if (str == null || str.length() == 0) {
            return new TreeNode(0);
        }

        Deque<TreeNode> nodeStack = new ArrayDeque<>();
        Deque<Integer> depthStack = new ArrayDeque<>();
        int len = str.length();
        int i = 0;
        while (i < len) {
            // Find the depth.
            int depth = 0;
            while (i < len && str.charAt(i) == '-') {
                depth++;
                i++;
            }

            // Find the val.
            int val = 0;
            while (i < len && str.charAt(i) <= '9' && str.charAt(i) >= '0') {
                val = val * 10 + (str.charAt(i) - '0');
                i++;
            }

            // Current node.
            TreeNode curNode = new TreeNode(val);

            // Process root node.
            if (nodeStack.isEmpty()) {
                depthStack.push(0);
                nodeStack.push(curNode);
                continue;
            }

            // Find parent node.
            while (!depthStack.isEmpty() && depthStack.peek() + 1 != depth) {
                nodeStack.pop();
                depthStack.pop();
            }
            TreeNode parentNode = nodeStack.peek();
            if (parentNode.left == null) {
                parentNode.left = curNode;
            } else {
                parentNode.right = curNode;
            }

            nodeStack.push(curNode);
            depthStack.push(depth);
        }

        return nodeStack.peekLast();
    }

     /*
    Recursion is a little tricky:
https://leetcode.com/problems/recover-a-tree-from-preorder-traversal/discuss/376427/Easy-to-Understand-Java-Recursive-Solution-with-Explanation
    */
}
