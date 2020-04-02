package com.myleetcode.tree.find_elements_in_a_contaminated_binary_tree;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode(int x) { val = x; }
     * }
     */
    class FindElements {

        Set<Integer> numSet = new HashSet<>();

        /*
       Caculate children val.
       */
        public FindElements(TreeNode root) {
            if (root == null) {
                return;
            }

            root.val = 0;
            preorder(root.left, 2 * root.val + 1);
            preorder(root.right, 2 * root.val + 2);
        }

        private void preorder(TreeNode curNode, int val) {
            if (curNode == null) {
                return;
            }

            curNode.val = val;
            numSet.add(curNode.val);

            preorder(curNode.left, 2 * curNode.val + 1);
            preorder(curNode.right, 2 * curNode.val + 2);
        }

//     /*
//     Caculate self val based on position.
//     */
//     public FindElements(TreeNode root) {
//         if(root == null){
//             return;
//         }

//         root.val = 0;
//         numSet.add(root.val);
//         preorder(root.left, root.val, true);
//         preorder(root.right, root.val, false);
//     }

//     private void preorder(TreeNode curNode, int parentVal, boolean isLeft){
//         if(curNode == null){
//             return;
//         }

//         if(isLeft){
//             curNode.val = 2 * parentVal + 1;
//         }else{
//             curNode.val = 2 * parentVal + 2;
//         }
//         numSet.add(curNode.val);

//         preorder(curNode.left, curNode.val, true);
//         preorder(curNode.right, curNode.val, false);
//     }

        public boolean find(int target) {
            return numSet.contains(target);
        }
    }

/**
 * Your FindElements object will be instantiated and called as such:
 * FindElements obj = new FindElements(root);
 * boolean param_1 = obj.find(target);
 */
}
