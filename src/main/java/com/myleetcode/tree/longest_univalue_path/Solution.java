package com.myleetcode.tree.longest_univalue_path;

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
    int max;
    public int longestUnivaluePath(TreeNode root) {
        // univaluePath(root, max); // and, another problem, integer could not pass like this to use as reference, in Java

        //   path(root); // 不需要接收最后的返回值，最后的返回值当然是root node的长度，但不一定是最长的，所以我们不需要这个，我们需要的是max

        correctPath(root);
        return max;

    }

    // 注意，path()之所以是错误的，是因为，我们的leftLen和rightLen获得方式错了，我们只有在cur node和left node相等时，才能说cur node的leftLen不是0，否则就是0.因为考虑这种情况：不考虑right node的情况下，curnode是1，leftnode是2，leftnode的leftnode是3，leftnode的leftnode的leftnode是3.这时候curnode的长度是0，而不能是1.
    // 另外，计算path的长度的问题，就是一条单向的node串，所以在eg2中，虽然max是2，但是第一个4，其path是1，其node-leftNode这个path是1，node-rightNode这个path是1，这个才是正常的我们说一个path的方法。所以我们才需要在计算max时，让max=Math.max(max, leftLen+rightLen)因为这个是leetcode根据eg2给我们的对于max path的定义。
    // 关于tree中path的正规表达方式可以看这个：https://leetcode.com/problems/path-sum/
    //https://leetcode.com/problems/binary-tree-paths/

    // https://leetcode.com/problems/longest-univalue-path/discuss/174639/Java-Easy-Recursive-Solution-6ms
    private int correctPath(TreeNode node){
        // base case
        if(node == null){
            return 0;
        }

        int curLen = 0;
        int leftLen = 0;
        int rightLen = 0;

        // recurse
        // left subtree path length.
        int left = correctPath(node.left);
        // 重要!!!left 和leftLen的关系。node and root of left subtree, if has same value, node get itself's left path length: leftLen + 1
        if((node.left != null) && (node.left.val == node.val)){
            leftLen = left + 1;
        }

        // recurse
        // right subtree path length
        int right = correctPath(node.right);
        // 重要!!!right和rightLen的关系
        if((node.right != null) && (node.right.val == node.val)){
            rightLen = right + 1;
        }

        // 重要，如何计算max，这个根据eg2来获知，是根据leftLen+rightLen和max, 这个是leetcode的定义
        // 每次获得curLen之后尝试更新下max
        max = Math.max(max, leftLen + rightLen);

        // 重要，如何计算node的path长度,这个是经典定义
        curLen = Math.max(leftLen, rightLen);

        return curLen;

    }

    // 注意！！！
    /*
    [5,4,5,1,1,5] 对应的树是
               1
             /   \
            4     5
           / \   /
          4   4 5
    而不是eg1中的树
    */
    private int path(TreeNode node){ // return cur node length
        // base case
        if(node == null){
            return 0;
        }

        int curLen = 0;
        int leftLen = 0;
        int rightLen = 0;

        // recurse
        // left subtree path length.
        leftLen = path(node.left);
        // node and root of left subtree, if has same value, node get itself's left path length: leftLen + 1
        if((node.left != null) && (node.left.val == node.val)){
            leftLen += 1;
        }

        // recurse
        // right subtree path length
        rightLen = path(node.right);
        if((node.right != null) && (node.right.val == node.val)){
            rightLen += 1;
        }

        // 重要！！！如何计算的正确的长度，根据leetcode的定义，当node.val == node.left.val == node.right.val时，curLen == leftLen + rightLen. 否则curLen = max(leftLen, rightLen)
        if((node.left != null) && (node.right != null) && (node.left.val == node.val) && (node.right.val == node.val)){
            curLen = leftLen + rightLen;
        }else{
            curLen = Math.max(leftLen, rightLen);
        }

        // 每次获得curLen之后尝试更新下max
        max = Math.max(max, curLen);

        // return curLen
        return curLen;

    }

    // wrong.
    // a function to return univalue path length of a tree with given node as root
    private int univaluePath(TreeNode node, Integer max){
        // base case
        if(node == null){
            return 0;
        }

        int length = 0;

        int left = 0;
        if(node.left != null && node.left.val == node.val){
            if(node.val == node.left.val){
                left = 1 + univaluePath(node.left, max);
            }else{
                left = univaluePath(node.left, max);
            }
        }

        max = Math.max(left, max);

        // right univalue path
        int right = 0;
        if(node.right != null){
            if(node.val == node.right.val){
                right = 1 + univaluePath(node.right, max);
            }else{
                right = univaluePath(node.right, max);
            }
        }

        max = Math.max(right, max);

        System.out.println("v: "+node.val+ "left: "+left+" right: " + right +" max: "+max);
        System.out.println("***");

        // return longer one
        return left > right ? left : right;

    }
}
