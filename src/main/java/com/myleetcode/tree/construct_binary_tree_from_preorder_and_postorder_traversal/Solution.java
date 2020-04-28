package com.myleetcode.tree.construct_binary_tree_from_preorder_and_postorder_traversal;

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
    public TreeNode constructFromPrePost(int[] pre, int[] post) {
        return constructFromPrePostByRecursion(pre, post);
    }

    /*
    Most important part is to find the end index of left sub tree in pre and post array. Use this int leftSubLen = postLeftSubEnd - postStart + 1; help use instead of try to find the left sub tree's end value in pre and post and then get its index because that way could cause index out of boundary easily.

    TC: O(N^2)
    SC: O(N)
    */
    private TreeNode constructFromPrePostByRecursion(int[] pre, int[] post) {
        if (pre == null || pre.length == 0 || post == null || post.length == 0 || pre.length != post.length) {
            return null;
        }

        int lenPre = pre.length;
        int lenPost = post.length;

        return build(pre, 0, lenPre - 1, post, 0, lenPost - 1);
    }

    private TreeNode build(int[] pre, int preStart, int preEnd, int[] post, int postStart, int postEnd) {
        // Base case.
        if (preStart > preEnd) {
            return null;
        }
        if (preStart == preEnd) {
            return new TreeNode(pre[preStart]);
        }

        // Subtree root.
        TreeNode curRoot = new TreeNode(pre[preStart]);

        // Find left subtree array's end index in pre and post array.
        int preLeftSubStart = preStart + 1; // preStart is curRoot, should not use, so left sub root is at preStart + 1.
        int leftSubRootVal = pre[preLeftSubStart];
        int postLeftSubEnd = postStart;
        while (postLeftSubEnd < postEnd) { // postEnd is curRoot, should not use, so right sub root is at postEnd - 1.
            if (post[postLeftSubEnd] == leftSubRootVal) {
                break;
            }
            postLeftSubEnd++;
        }
        // !!! How to find the preLeftSubEnd easily without process too many index corner cases.
        int leftSubLen = postLeftSubEnd - postStart + 1;
        int preLeftSubEnd = preLeftSubStart + leftSubLen - 1;


        curRoot.left = build(pre, preLeftSubStart, preLeftSubEnd, post, postStart, postLeftSubEnd);
        curRoot.right = build(pre, preLeftSubEnd + 1, preEnd, post, postLeftSubEnd + 1, postEnd - 1);

        return curRoot;
    }
}
