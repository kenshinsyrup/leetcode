package com.myleetcode.tree.find_duplicate_subtrees;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.*;

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
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        return findDuplicateSubtreesByDFSAndMap(root);
    }

    /*
    We could improve a little from the original solution.
    We actually do not need to record all subtree roots of a path, we just need to count the path number, at first time we count a path as 1, if we meet it again and its value is 1, we add 1 to its value and add the subtree root to answer list.
    */

    /*
    DFS to get path of every node which act as its subtree root. If path is duplicate, then we could extract the answer from the map.

    TC: O(N)
    SC: O(N)
    */
    private List<TreeNode> findDuplicateSubtreesByDFSAndMap(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        Map<String, Set<TreeNode>> pathRootsMap = new HashMap<>();
        dfs(root, pathRootsMap);

        List<TreeNode> res = new ArrayList<>();
        for (String path : pathRootsMap.keySet()) {
            if (pathRootsMap.get(path).size() >= 2) {
                res.add(pathRootsMap.get(path).iterator().next()); // Only need one root for the duplicates subtrees.
            }
        }

        return res;
    }

    private String dfs(TreeNode curNode, Map<String, Set<TreeNode>> pathRootsMap) {
        if (curNode == null) {
            return "null";
        }

        String leftPath = dfs(curNode.left, pathRootsMap);
        String rightPath = dfs(curNode.right, pathRootsMap);

        String curPath = curNode.val + " " + leftPath + " " + rightPath;

        Set<TreeNode> roots = pathRootsMap.getOrDefault(curPath, new HashSet<>());
        roots.add(curNode);
        pathRootsMap.put(curPath, roots);

        return curPath;
    }
}
