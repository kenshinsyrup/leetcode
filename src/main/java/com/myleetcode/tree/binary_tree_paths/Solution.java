package com.myleetcode.tree.binary_tree_paths;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayList;
import java.util.List;

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
    public List<String> binaryTreePaths(TreeNode root) {

        List<String> ret = new ArrayList<String>();

        // special case
        if(root == null){
            return ret;
        }

        // easier
        binaryTreePathsByDFS(root, ret, "");

        // should consider delete in every recursion
        // StringBuilder sb = new StringBuilder();
        // binaryTreePathsByDFSAndSB(root, ret, sb);

        return ret;
    }

    // DFS
    // TC: O(n)
    // SC: O(n)
    private void binaryTreePathsByDFS(TreeNode node, List<String> ret, String temp){
        // base case, if null reached, return
        if(node == null){
            return;
        }

        // build temp
        if(temp == ""){
            temp = Integer.toString(node.val);
        }else{
            temp = temp + "->" + node.val;
        }


        // leaf, add to ret and return
        if(node.left == null && node.right == null){
            ret.add(temp);
            return;
        }

        // dfs
        binaryTreePathsByDFS(node.left, ret, temp);
        binaryTreePathsByDFS(node.right, ret, temp);
    }

    // instead of String, we could use StringBuilder. But there's a lot of differences here. StringBuilder is only one and we pass it all through the recursion. But with String, we build different "temp" object every time we use "+" (because this is hwo java works with String).
    private void binaryTreePathsByDFSAndSB(TreeNode node, List<String> ret, StringBuilder sb){
        // base case
        if(node == null){
            return;
        }

        // record sb length for delete
        int len = sb.length();

        // build string
        sb.append(node.val);

        // leaf
        if(node.left == null && node.right == null){
            // add to ret
            ret.add(sb.toString());

            // must delete anything appended in this turn, something like backtracking
            sb.delete(len, sb.length());

            return;
        }

        sb.append("->");

        // dfs
        binaryTreePathsByDFSAndSB(node.left, ret, sb);
        binaryTreePathsByDFSAndSB(node.right, ret, sb);

        // must delete anything appended in this turn, something like backtracking
        sb.delete(len, sb.length());

    }



}
