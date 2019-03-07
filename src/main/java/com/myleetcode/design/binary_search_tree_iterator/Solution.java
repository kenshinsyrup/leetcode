package com.myleetcode.design.binary_search_tree_iterator;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.Stack;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class BSTIterator {

    private Stack<TreeNode> nodeStack = new Stack<TreeNode>();

    public BSTIterator(TreeNode root) {
        // inorder traversal is the foundermental.
        // since we only could use O(h) memory, we could not just inorder traverse BST to store nodes to an array.
        // https://leetcode.com/problems/binary-search-tree-iterator/discuss/52526/Ideal-Solution-using-Stack-(Java)
        // 整体还是一个inorder，只不过iterative的方式，分成了三部分，在不同的地方
        // 第一部分，左子
        TreeNode cur = root;
        while(cur != null){
            nodeStack.push(cur);
            cur = cur.left;
        }

    }

    // some times O(1), sometimes more than O(1), think it as average O(1) as the problem says.
    /** @return the next smallest number */
    public int next() {
        // 第二部分，当前的处理
        TreeNode top = nodeStack.pop();

        // 第三部分，右子
        TreeNode cur = top.right;
        while(cur != null){
            nodeStack.push(cur);
            cur = cur.left;
        }

        return top.val;

    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return !nodeStack.isEmpty();
    }
}

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */
