package com.myleetcode.tree.path_sum_ii;

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
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        return pathSumByDFS(root, sum);
    }

    // TC: O(H*N) ie O(Nlog(N)), recursion height is H, in every leaf node, we have a sum up operation cost O(N).
    // SC: (N)
    // intuition: this problem requires the all path from root to leaf, so DFS to traverse all paths seems a good solution, and this is also looks like backtracking because when we done with a node, we need remove it from path.
    private List<List<Integer>> pathSumByDFS(TreeNode root, int sum){
        List<List<Integer>> ret = new ArrayList<>();

        if(root == null){
            return ret;
        }

        List<Integer> pathValue = new ArrayList<>();

        pathDFS(root, ret, pathValue, sum);

        return ret;
    }

    private void pathDFS(TreeNode node, List<List<Integer>> ret, List<Integer> pathValue, int sum){
        // base, if node is null, return
        if(node == null){
            return;
        }

        // add current node value to path value
        pathValue.add(node.val);

        // leaf node, if current node is leaf node, caculate pathValue, if equals to sum, add the path to ret
        if(node.left == null && node.right == null){
            int v = 0;
            for(int i = 0; i < pathValue.size(); i++){
                v += pathValue.get(i);
            }
            if(v == sum){
                ret.add(new ArrayList<>(pathValue)); // deep copy
            }
        }else{
            // current node is not leaf node, do DFS in its left and right subtree, we could handle if one of them is null in base case.
            pathDFS(node.left, ret, pathValue, sum);
            pathDFS(node.right, ret, pathValue, sum);
        }

        // current node is done, remove it from path
        pathValue.remove(pathValue.size() - 1);
    }
}
