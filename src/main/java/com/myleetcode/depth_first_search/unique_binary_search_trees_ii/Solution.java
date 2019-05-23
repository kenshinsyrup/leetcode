package com.myleetcode.depth_first_search.unique_binary_search_trees_ii;

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
    public List<TreeNode> generateTrees(int n) {
        return generateTreesByBuild(n);
    }

    // intuition:
    // TC: Catalan number
    // SC: O(N)
    private List<TreeNode> generateTreesByBuild(int n){
        List<TreeNode> ret = new ArrayList<>();

        if(n <= 0){
            return ret;
        }

        return buildTree(1, n);
    }

    // this is a tranformation of build balanced BST where we only use the mid of start and end as the curRoot
    private List<TreeNode> buildTree(int start, int end){
        List<TreeNode> curRootList = new ArrayList<>();
        if(end < start){
            curRootList.add(null); // should add null in
            return curRootList;
        }

        // leaf
        if(start == end){
            curRootList.add(new TreeNode(start));
            return curRootList;
        }

        // tree
        // current subtree could use any num in [start:end] as root
        for(int i = start; i <= end; i++){

            // subtree's roots
            List<TreeNode> leftRootList = buildTree(start, i - 1);
            List<TreeNode> rightRootList = buildTree(i + 1, end);

            // 4 conditions
            if(leftRootList.size() == 0 && rightRootList.size() == 0){
                // no subtree
                curRootList.add(new TreeNode(i));
            }else if(rightRootList.size() == 0){
                // only left subtree
                for(TreeNode leftRoot: leftRootList){
                    TreeNode curRoot = new TreeNode(i);
                    curRoot.left = leftRoot;

                    curRootList.add(curRoot);
                }
            }else if(leftRootList.size() == 0){
                // only right subtree
                for(TreeNode rightRoot: rightRootList){
                    TreeNode curRoot = new TreeNode(i);
                    curRoot.right = rightRoot;

                    curRootList.add(curRoot);
                }
            }else{
                // two subtrees
                // append subtree to curRoot
                for(TreeNode leftRoot: leftRootList){
                    for(TreeNode rightRoot: rightRootList){
                        // !!! must new a curRoot Treenode every time here, if we new it in the for loop of i, then we are reusing only one Treenode for all leftRootList and rightRootList and will only geht the last right and left root append the curRoot with many time because curRoot is reference.
                        TreeNode curRoot = new TreeNode(i);
                        curRoot.left = leftRoot;
                        curRoot.right = rightRoot;

                        curRootList.add(curRoot);
                    }
                }
            }
        }

        return curRootList;
    }
}
