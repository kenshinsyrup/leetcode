package com.myleetcode.tree.construct_binary_tree_from_inorder_and_postorder_traversal;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return buildTreeByDFS(inorder, postorder);
    }

    // 105. Construct Binary Tree from Preorder and Inorder Traversal
    // TC: O(N)
    // SC: O(N)
    // intuition: #1 we could convert postorder to preorder then it's same as 105. Construct Binary Tree from Preorder and Inorder Traversal
    // #2 we dont convert the postorder to preorder explicitly, just traverse the postorder from back to front.
    private TreeNode buildTreeByDFS(int[] inorder, int[] postorder){
        // special case
        if(inorder == null || inorder.length == 0 || postorder == null || postorder.length == 0 || postorder.length != inorder.length){
            return null;
        }

        Map<Integer, Integer> inorderValueIdxMap = new HashMap<>();
        for(int i = 0; i < inorder.length; i++){
            inorderValueIdxMap.put(inorder[i], i);
        }

        return buildNode(postorder, 0, postorder.length - 1, inorderValueIdxMap, 0);

    }

    private TreeNode buildNode(int[] postorder, int postStart, int postEnd, Map<Integer, Integer> inorderValueIdxMap, int inStart){
        // base case
        if(postStart > postEnd){
            return null;
        }
        if(postStart == postEnd){
            return new TreeNode(postorder[postEnd]);
        }

        // current root
        int rootVal = postorder[postEnd];
        TreeNode root = new TreeNode(rootVal);

        // idx in inorder array
        int rootIdx = inorderValueIdxMap.get(rootVal);

        // left subtree and right subtree in postorder
        // rootIdx is current root idx, so [inStart:rootIdx - 1] is the left subtree in inorder, so left subtree length is: rootIdx - 1 - inStart + 1 == rootIdx - inStart, so the left subtree relative index is [0:rootIdx-inStart - 1], so use the postStart as base then left subtree is [postStart: postStart + (rootIdx - inStart - 1)];
        // then the rest is right subtree, of course we should exclude the index of postEnd: [postStart + (rootIdx - inStart): postEnd-1]
        // left subtree and right subtree in inorder is easier: left is [inStart:rootIdx-1], right is [rootIdx+1:inEnd], but we only need inStart in our logic
        TreeNode left = buildNode(postorder, postStart, postStart + rootIdx - inStart -1, inorderValueIdxMap, inStart);
        TreeNode right = buildNode(postorder, postStart + rootIdx - inStart, postEnd - 1, inorderValueIdxMap, rootIdx + 1);

        root.left = left;
        root.right = right;
        return root;

    }
}
