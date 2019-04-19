package com.myleetcode.tree.same_tree;

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
    public boolean isSameTree(TreeNode p, TreeNode q) {
        
        // 递归出口：1，同为null为true；2不同为null但有一方为null为false；3同非为null，则值不同为false。如果值相同，则只要左右子树相同则为true，进入递归
        if(p == null && q == null){
            return true;
        }
        
        // if((p != null && q == null) || (p == null && q != null)){
        //     return false;
        // }
//         注意这样写和注释的效果一样，因为基于第一个 if(p == null && q == null)，那么if(p == null || q == null）就是和注释的部分是一个意思了已经
        if(p == null || q == null){
            return false;
        }
        
       if(p.val != q.val){
           return false;
       }
        
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}