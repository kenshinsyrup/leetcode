package com.myleetcode.symmetric_tree;

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
    public boolean isSymmetric(TreeNode root) {
//         recursive
       // return isMirrorRecursive(root, root);
        
//         iterative
        return isMirrorIterative(root, root);
        
    }
    
    // recursive
//     Time: 访问了每一个node，并且每一个node定处理时间为O(1),所以总体是O(n)
    private boolean isMirrorRecursive(TreeNode leftRoot, TreeNode rightRoot){
        // 同为null， true
        if(leftRoot == null && rightRoot == null){
            return true;
        }
        
//         不同为null
        if((leftRoot == null && rightRoot != null) || (leftRoot != null && rightRoot == null)){
            return false;
        }
        
//         同不为null, val不同定非mirro，同则根据左右子树定isMirror判定
        if(leftRoot.val != rightRoot.val){
            return false;
        }
        return isMirrorRecursive(leftRoot.left, rightRoot.right)&&isMirrorRecursive(leftRoot.right, rightRoot.left);
    }
    
    // iterative: 这里使用Queue
    private boolean isMirrorIterative(TreeNode leftRoot, TreeNode rightRoot){
//         initialize queue
        Queue<TreeNode> nodeQ = new LinkedList<>();
        
//         add queue head
        nodeQ.add(leftRoot);
        nodeQ.add(rightRoot);
        
        // start queue traverse
        while(!nodeQ.isEmpty()){
            TreeNode nodeA = nodeQ.poll();
            TreeNode nodeB = nodeQ.poll();
            
//             注意这里与recurse方法不同，不是return true。当前从队列中取出的两个node均为null，只能说明这两个node相同（也镜像），需要继续判断queue后续的内容，需要整个queue都判断完，没有发现不镜像的，才是镜像
            if(nodeA == null && nodeB == null){
                continue;
            }
            
//        结合上面的判断，这个判断相当于:if((nodeA == null && nodeB != null) || (nodeA != null && nodeB == null))
            if(nodeA == null || nodeB == null){
                return false;
            }
            
            if(nodeA.val != nodeB.val){
                return false;
            }
            
//             要镜像，比较的是当前两个node的左子和右子，以及右子和左子
            nodeQ.add(nodeA.left);
            nodeQ.add(nodeB.right);
            nodeQ.add(nodeA.right);
            nodeQ.add(nodeB.left);
        }
        
        return true;
    }
}