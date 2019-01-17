package com.myleetcode.increasing_order_search_tree;

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
    public TreeNode increasingBST(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        
        if(root == null){
            return null;
        }
        
        traverseTree(root, ret);
        return buildTree(ret);
    }
    
    // in-order traverse, recurrse
    private void traverseTree(TreeNode n, List<Integer> ret){
        // 出口
        if(n == null){
            return;
        }
        
        traverseTree(n.left, ret);
        ret.add(n.val);
        traverseTree(n.right, ret);
    }
    
//     掌握通过array建树的方法
    private TreeNode buildTree(List<Integer> source){
        // head node 辅助
        TreeNode head = new TreeNode(0);
        
        // current pointer
        TreeNode cur = head;
        
        for(int v : source){
            cur.right = new TreeNode(v); // 赋值
            cur = cur.right; // 移动指针
        }
        
        // 辅助node的右节点就是需要的新建的树的第一个节点
        return head.right;
    }
    
}