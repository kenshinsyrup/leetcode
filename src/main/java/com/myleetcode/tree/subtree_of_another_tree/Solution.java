package com.myleetcode.tree.subtree_of_another_tree;

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
    public boolean isSubtree(TreeNode s, TreeNode t) {
        // please read leetcode solution thoroughly

        // special case
        if(s == null || t == null){
            return false;
        }

        // return isSubTreeByPreOrder(s, t);

        return dfsTraverse(s, t);
    }

    // 1
    // classic and very good problem, help understand difference of pre-order, in-order, post-order
    //pre-order
    // eg1:  s[3,4,1,2,5] t[4,1,2]
    // eg2:  s[3,4,1,2,0,5] t[4,1,2] t is substructure of s, only pre-order traverse does not destroy the structure of tree

    // in-order
    // eg1: s[1,4,2,3,5] t[1,4,2]
    // eg2: s[1,4,0,2,3,5] t[1,4,2] we could see t is not substructure of s by in-order, this is not right

    // post-order
    // eg1: s[1,2,4,5,3] t[1,2,4]
    // eg2: s[1,0,2,4,5,3] t[1,2,4] we could see t is not substructure of s by post-order, this is not right

    // use pre-order traverse to keep tree's structure while traversing its nodes.
    // pre-order to a string, check substring
    private boolean isSubTreeByPreOrder(TreeNode s, TreeNode t){
        StringBuilder sb = new StringBuilder();

        dfsPreOrder(s, sb, true);
        String strS = sb.toString();

        // empty sb content
        sb.delete(0, sb.length());

        dfsPreOrder(t, sb, true);
        String strT = sb.toString();

        return strS.indexOf(strT) == -1 ? false : true;

    }
    /* for this case, we know we must distinguish left and right child of root, so we should append lnull or rnull to sb to make difference of 1,2,3 and 2,lnull,3 and 2,3,rnull
    [1,2,3]
    [2,3]
    */
    /*for this case, we should add two identifier at both sides of node.val to make right decision
    [12]
    [2]
    */
    private void dfsPreOrder(TreeNode node, StringBuilder sb, boolean isLeft){
        if(node == null){
            if(isLeft){
                sb.append("lnull");
            }else{
                sb.append("rnull");
            }
            return;
        }

        sb.append("#");
        sb.append(node.val);
        sb.append("*");

        dfsPreOrder(node.left, sb, true);
        dfsPreOrder(node.right, sb, false);
    }

    // 2 这个题其实用双重dfs的方式来做的话思路也很清晰，还是蛮推荐的
    // can also use nested dfs to solve
    // we use dfs to traverse tree s, use every node as root once to compare its subtree with t tree
    // use another dfs to implement the comparision
    // TC: O(m*n)
    // SC: O(n), n recursion
    private boolean dfsTraverse(TreeNode nodeS, TreeNode t){
        if(nodeS == null){
            return false;
        }

        // compare, nodeS as root, find subtree same as t
        if(dfsCompare(nodeS, t)){
            return true;
        }

        // traverse s, we should use every node of s as root to check if there's a same subtree as t
        return dfsTraverse(nodeS.left, t) ||
                dfsTraverse(nodeS.right, t);
    }


    // compare every node by dfs
    private boolean dfsCompare(TreeNode nodeS, TreeNode nodeT){
        // base case, if any is null
        if(nodeS == null && nodeT == null){
            return true;
        }
        if(nodeS == null || nodeT == null){
            return false;
        }

        // no null, compare current node.val
        if(nodeS.val != nodeT.val){
            return false;
        }

        // current node.val is equal, compare left and right subtree
        return dfsCompare(nodeS.left, nodeT.left) &&
                dfsCompare(nodeS.right, nodeT.right);
    }

}
