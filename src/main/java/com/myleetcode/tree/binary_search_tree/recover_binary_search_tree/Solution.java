package com.myleetcode.tree.binary_search_tree.recover_binary_search_tree;

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
    public void recoverTree(TreeNode root) {
        // recoverTreeByVerify(root); // WRONG
        recoverByInorder(root);
    }

    // solution: https://leetcode.com/problems/recover-binary-search-tree/discuss/32562/Share-my-solutions-and-detailed-explanation-with-recursiveiterative-in-order-traversal-and-Morris-traversal
    // https://leetcode.com/problems/recover-binary-search-tree/discuss/32535/No-Fancy-Algorithm-just-Simple-and-Powerful-In-Order-Traversal
    /*
In-order traversal is really useful in BST. Following in-order traversal, we should have following order: prev.val < curr.val. If not, then we found at least one incorrectly placed node

So the basic idea is to visit the tree with in-order traversal and search for two swapped nodes. Then swap them back.

Now the problem is if we found an incorrect pair where prev.val > curr.val, how do we know which node is the incorrect one? The answer is it depends on whether we have found incorrect node before. So What is that?

Since we get two elements that are swapped by mistake, there must be a smaller TreeNode get a larger value and a larger TreeNode get a smaller value.

If it is the first time we found an incorrect pair, the prev node must be the first incorrect node.
If it is not the first time we found an incorrect pair, the curr node must be the second incorrect node
    */

    // Because we have to change the prevNode, firWrongNode, secWrongNode during the inorder, so we could not pass by parameter, should use the Result to help
    class Result{
        TreeNode firWrongNode;
        TreeNode secWrongNode;
        TreeNode prevNode;

        public Result(){
            this.firWrongNode = null;
            this.secWrongNode = null;

            this.prevNode = null;
        }
    }

    private void recoverByInorder(TreeNode root){
        if(root == null){
            return;
        }

        Result ret = new Result();
        TreeNode prevNode = null;

        // find two wrong nodes
        inorder(root, ret);

        //swap to recover
        int temp = ret.firWrongNode.val;
        ret.firWrongNode.val = ret.secWrongNode.val;
        ret.secWrongNode.val = temp;
    }

    private void inorder(TreeNode curNode, Result ret){
        if(curNode == null){
            return;
        }

        inorder(curNode.left, ret);

        /*
        make this part more clear: the number of times that you will find prev.val >= root.val depends on whether the two nodes that get swapped are next to each other in the sequence of in-order traversal.
Let's assume this is the original in-order traversal sequence of BST: 1 2 3 4 5
If 2 and 3 get swapped, it becomes 1 3 2 4 5 and there is only one time that you will have prev.val >= root.val
If 2 and 4 get swapped, it becomes 1 4 3 2 5 and there are two times that you will have prev.val >= root.val

If during the first time when you find prev.val >= root.val, the previous node "prev" MUST be one of two nodes that get swapped. However, the current node MAY OR MAY NOT be another node that gets swapped, which will depend on whether later during in-order traversal, there is another prev.val >= root.val or not. If there is, then the current node "root" during the 2nd time of prev.val >= root.val will be the other node that gets swapped
        */
        // find wrong nodes:
        // if prevNode larger than curNode, violate the BST attribute
        if(ret.firWrongNode == null && ret.prevNode != null && ret.prevNode.val > curNode.val){
            // if firWrongNode is null, means this is the first time we meet a violation, so this must be the larger wrong node in wrong place, so the prevNode must be the larger wrong node
            ret.firWrongNode = ret.prevNode;
        }

        // !!! here must be careful, we have set the firWrongNode just before, so if these two wrong nodes are adjacent in the wrong tree, we could handle by this.
        if(ret.firWrongNode != null && ret.prevNode != null && ret.prevNode.val > curNode.val){
            // if we have found the firWrongNode, then this violation is caused by the smaller wrong node, so we need to find the last violation, at the last violation, the wrong node must be the curNode
            ret.secWrongNode = curNode;
        }

        //!!! update the prevNode, we must update the prevNode to make it access by father nodes, so we have to use global or use Result, could not pass by parameter
        ret.prevNode = curNode;

        inorder(curNode.right, ret);
    }

    // Wrong idea
    // input [2,3,1], will output [1,2,3], but expected is [2,1,3]
    // intuition:
    // smae thought as BST Veriry.
    // we verify this BST, at the not valid node, swap
    // TC: O(N)
    // SC: O(H)
    private void recoverTreeByVerify(TreeNode root){
        if(root == null){
            return;
        }

        recover(root, null, null);
    }

    private void recover(TreeNode curNode, TreeNode leftBoundary, TreeNode rightBoundary){
        if(curNode == null){
            return;
        }

        if(leftBoundary != null){
            // violate BST attribute, swap
            if(curNode.val < leftBoundary.val){
                int temp = curNode.val;
                curNode.val = leftBoundary.val;
                leftBoundary.val = temp;
            }
        }

        if(rightBoundary != null){
            // violate BST attribute, swap
            if(curNode.val > rightBoundary.val){
                int temp = curNode.val;
                curNode.val = rightBoundary.val;
                rightBoundary.val = temp;
            }
        }

        // expore
        recover(curNode.left, leftBoundary, curNode);
        recover(curNode.right, curNode, rightBoundary);
    }


}
