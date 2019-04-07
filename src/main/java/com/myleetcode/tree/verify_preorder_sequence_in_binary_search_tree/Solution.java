package com.myleetcode.tree.verify_preorder_sequence_in_binary_search_tree;

import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    public boolean verifyPreorder(int[] preorder) {
        // return verifyPreorderByDFS(preorder);// Wrong

        return verifyPreorderByDFS(preorder);
    }

    // TC: O(N^2) worst, O(N*log(N)) if the BST is balanced
    // SC: O(N), recursion
    // this is a preorder array, 与98. Validate Binary Search Tree并不是同样的问题
    // 思路还是一样的，要让左边的子树的所有值小于root小于右边子树的所有值
    // intuition: in BST, we have left < root < right, then we could dfs the given array, if it violates this rule then false
    // https://leetcode.com/problems/verify-preorder-sequence-in-binary-search-tree/discuss/68193/Constant-space-O(nlogn)-(worst-case-O(n2))-recursive-Java-solution
    private boolean verifyPreorderByDFS(int[] preorder){
        // special case
        if(preorder == null || preorder.length == 0 || preorder.length == 1){
            return true;
        }

        return verify(preorder, 0, preorder.length - 1);
    }

    // 每次找到左右子树，然后检查。对于任意一棵树，其范围为[left:right] in preorder. min and max is the BST check
    private boolean verify(int[] preorder, int left, int right){
        // ！！！base case true
        if(left >= right){
            return true;
        }

        int val = preorder[left];

        // find the right subtree
        int rightRootIdx = left + 1; // here must make the default be left+1, not 0, because we may not come into the if condition.
        // !!! 注意这样写是错误的，这样的话rightRootIdx在没有右子树的时候一直没有移动，导致for循环结束时rightRootIdx没有在正确的位置
        // for(int i = left + 1; i <= right; i++){
        //     if(preorder[i] > val){
        //         rightRootIdx = i;
        //         break;
        //     }
        // }
        while(rightRootIdx <= right){
            if(preorder[rightRootIdx] > val){
                break;
            }
            rightRootIdx++;
        }

        // left < root < right, we checked the left part during the find rightRootIdx procedure, so we check the root < right part
        for(int i = rightRootIdx; i <= right; i++){
            if(preorder[i] < val){
                return false;
            }
        }


        boolean leftIsBST = verify(preorder, left + 1, rightRootIdx - 1);
        boolean rightIsBST = verify(preorder, rightRootIdx, right);

        return leftIsBST && rightIsBST;

    }

    private boolean verifyPreorderByDFSWrong(int[] preorder){
        // special case
        if(preorder == null || preorder.length == 0){
            return true;
        }

        // convert the preorder array to queue for convience because queue gives us O(1) time to remove the first elem
        Queue<Integer> valQueue = new ArrayDeque<>();
        for(int i = 0; i < preorder.length; i++){
            valQueue.offer(preorder[i]);
        }

        return preorderByDFS(valQueue, Integer.MIN_VALUE, Integer.MAX_VALUE);

    }

    private boolean preorderByDFS(Queue<Integer> valQueue, int low, int high){
        if(valQueue == null || valQueue.size() == 0){
            return true;
        }

        int val = valQueue.poll();
        if(val < low || val > high){
            return false;
        }

        boolean left = preorderByDFS(valQueue, low, val);
        boolean right = preorderByDFS(valQueue, val, high);

        // !!! must be &&, not ||, because all subtrees must be BST
        return left && right;
    }
}
