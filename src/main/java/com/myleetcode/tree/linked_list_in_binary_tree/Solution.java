package com.myleetcode.tree.linked_list_in_binary_tree;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */

import com.myleetcode.utils.list_node.ListNode;
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
public class Solution {
    public boolean isSubPath(ListNode head, TreeNode root) {
        return isSubPathByDFSAll(root, head);
    }

    /*
    DFSAll

    Most important part is the base case of DFS.
    if(curListNode == null){ // If we exhaust LinkList, means we have matched all the LinkedList in the Tree so true.
        return true;
    }
    if(curTreeNode == null){ // If we still have LinkedList to match but current SubTree is exhausted, false.
        return false;
    }

    DO NOT WRITE LIKE THIS:
    if(curListNode == null && curTreeNode == null){
        return true;
    }
    if(curListNode == null || curTreeNode == null){
        return false;
    }
    This means only if LinkedList and SubTree both are exhausted at the same time return true, which is definitely WRONG.

    TC: O(N * min(H, M)), N is tree nodes number, M is list nodes number
    SC: O(max(H, M))
    */
    private boolean isSubPathByDFSAll(TreeNode root, ListNode head) {
        if (head == null) {
            return true;
        }
        if (root == null) {
            return false;
        }

        return dfsAll(root, head);
    }

    private boolean dfsAll(TreeNode curRoot, ListNode head) {
        if (head == null) {
            return true;
        }
        if (curRoot == null) {
            return false;
        }

        boolean curRet = dfs(curRoot, head);
        if (curRet) {
            return curRet;
        }

        return dfsAll(curRoot.left, head) || dfsAll(curRoot.right, head);
    }

    private boolean dfs(TreeNode curTreeNode, ListNode curListNode) {
        if (curListNode == null) {
            return true;
        }
        if (curTreeNode == null) {
            return false;
        }

        if (curTreeNode.val != curListNode.val) {
            return false;
        }

        return dfs(curTreeNode.left, curListNode.next) || dfs(curTreeNode.right, curListNode.next);
    }
}
