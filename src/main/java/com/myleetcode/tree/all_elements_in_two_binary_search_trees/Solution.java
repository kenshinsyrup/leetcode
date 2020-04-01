package com.myleetcode.tree.all_elements_in_two_binary_search_trees;

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
 * TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        // return getAllElementsByInorderRecursive(root1, root2); // Wrong
        return getAllElementsByInorderIterative(root1, root2);
    }

    /*
    Inorder iterative transformation problem.

    N is the larger tree nodes number
    TC: O(N)
    SC: O(H)
    */
    private List<Integer> getAllElementsByInorderIterative(TreeNode root1, TreeNode root2) {
        List<Integer> ret = new ArrayList<>();
        if (root1 == null && root2 == null) {
            return ret;
        }

        Deque<TreeNode> nodeStack1 = new ArrayDeque<>();
        Deque<TreeNode> nodeStack2 = new ArrayDeque<>();
        while (!nodeStack1.isEmpty() || !nodeStack2.isEmpty() || root1 != null || root2 != null) {
            if (root1 != null || root2 != null) {
                if (root1 != null) {
                    nodeStack1.push(root1);
                    root1 = root1.left;
                }

                if (root2 != null) {
                    nodeStack2.push(root2);
                    root2 = root2.left;
                }
            } else {
                if ((!nodeStack1.isEmpty() && !nodeStack2.isEmpty() && nodeStack1.peek().val < nodeStack2.peek().val) || (!nodeStack1.isEmpty() && nodeStack2.isEmpty())) {
                    root1 = nodeStack1.pop();

                    ret.add(root1.val);

                    root1 = root1.right;
                } else {
                    root2 = nodeStack2.pop();

                    ret.add(root2.val);

                    root2 = root2.right;
                }
            }
        }

        return ret;
    }

    /*
    WRONG.
    [0,-10,10]
    [5,1,7,0,2]

    Inorder recursive transformation problem. BUT this is wrong. During recursion on both trees, it's very difficult to determine whether to add the node value to ret when we meet a null node and a non-null node.

    If want to use recursion, there's a method. We could do inorder recursion on each tree seperately and keep nodes in two lists. Then do a merge to this two sorted lists to the ret. This will also cost TC: O(N) but SC will be O(N + M)

    N is the larger tree nodes number
    TC: O(N)
    SC: O(H)
    */
    private List<Integer> getAllElementsByInorderRecursive(TreeNode root1, TreeNode root2) {
        List<Integer> ret = new ArrayList<>();
        if (root1 == null && root2 == null) {
            return ret;
        }

        inorderOnTrees(root1, root2, ret);

        return ret;
    }

    private void inorderOnTrees(TreeNode curNode1, TreeNode curNode2, List<Integer> ret) {
        if (curNode1 == null && curNode2 == null) {
            return;
        }

        if (curNode1 == null) {
            inorderOnTrees(curNode1, curNode2.left, ret);

            ret.add(curNode2.val);

            curNode2.left = null;
            inorderOnTrees(curNode1, curNode2.right, ret);
        } else if (curNode2 == null) {
            inorderOnTrees(curNode1.left, curNode2, ret);

            ret.add(curNode1.val);

            curNode1.left = null;
            inorderOnTrees(curNode1.right, curNode2, ret);
        } else {
            inorderOnTrees(curNode1.left, curNode2.left, ret);

            curNode1.left = null;
            curNode2.left = null;
            if (curNode1.val < curNode2.val) {
                ret.add(curNode1.val);

                inorderOnTrees(curNode1.right, curNode2, ret);
            } else {
                ret.add(curNode2.val);

                inorderOnTrees(curNode1, curNode2.right, ret);
            }
        }
    }
}
