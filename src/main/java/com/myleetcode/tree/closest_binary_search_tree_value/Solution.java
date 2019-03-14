package com.myleetcode.tree.closest_binary_search_tree_value;

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
class Solution {
    public int closestValue(TreeNode root, double target) {
        // special case
        if(root == null){
            return -1;
        }

        return closestValueByTraverse(root, target);
    }

    // https://leetcode.com/problems/closest-binary-search-tree-value/discuss/70331/Clean-and-concise-java-solution
    //  从上向下在bst中遍历，记录node.val - target的abs的最小值，然后根绝node.val与target的关系来决定接下来去左子树还是右子树，直到查找到叶节点，取最小值即可。
    // 本质是一个树的遍历，记录和target最接近的值。只不过由于是bst，我们只需要每次查找半棵子树。
    private int closestValueByTraverse(TreeNode root, double target){
        //ret
        int val = 0;
        double ret = Double.MAX_VALUE;
        while(root != null){
            // check, find the closer one, record node.val and abs ret
            if(Math.abs(root.val - target) < ret){
                val = root.val;
                ret = Math.abs(root.val - target);
            }

            // only need to check half subtree. Because: if root.val > target, and we know in BST, every node in right subtree of root is larger than root.val, then no one in right subtree could be closer to target than root.
            if(root.val > target){
                root = root.left;
            }else{
                // root is smaller than target, left subtree nodes are all smaller than root, then no one in left subtree could be closer to target than root. So only check right subtree.
                root = root.right;
            }
        }

        return val;
    }


}
