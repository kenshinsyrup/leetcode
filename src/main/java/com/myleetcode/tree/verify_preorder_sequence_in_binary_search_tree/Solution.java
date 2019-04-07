package com.myleetcode.tree.verify_preorder_sequence_in_binary_search_tree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

class Solution {
    public boolean verifyPreorder(int[] preorder) {
        // return verifyPreorderByDFS(preorder);// Wrong

        // return verifyPreorderByDFS(preorder);

        // return verifyPreorderByTwoStack(preorder);
        return verifyPreorderByOneStack(preorder);
    }

    // TC: O(N)
    // SC: O(N)
    // but we could optimize this solution because the problem dont need us to provide the whole inorder stakc, it just want to know if this is a BST. So we could keep the topvalue of inStack for check in stead of the whole inStack.
    private boolean verifyPreorderByOneStack(int[] preorder){
        // special case
        if(preorder == null || preorder.length == 0 || preorder.length == 1){
            return true;
        }

        // we use a value to help us keep the top value of inStack, because the inStack is empty at start so we make this value be the min
        int topInInStack = Integer.MIN_VALUE;

        Deque<Integer> preStack = new ArrayDeque<>();

        for(int i = 0; i < preorder.length; i++){
            int curElem = preorder[i];

            // if curElem is smaller than top in inStack, then inorder is violated.
            if(curElem < topInInStack){
                return false;
            }

            while(!preStack.isEmpty() && curElem > preStack.peek()){
                int preTop = preStack.pop();

                // the same, if preTop is smaller than top in inStack, then inorder is violated
                if(preTop < topInInStack){
                    return false;
                }

                // valid, kee the new top in inStack
                topInInStack = preTop;
            }

            // after process all smaller nodes, push curElem to preTop
            preStack.push(curElem);
        }

        return true;
    }

    // TC: O(N)
    // SC: O(N)
    // 利用preorder和inorder的关系：对于preorder，我们可以通过利用两个stack可以将其转换成inorder traversal。
    // 那么如果我们把preorder array转成inorder array，对于BST来说，inorder必须是ascengding的顺序，这样inorder是BST，推出preorder是BST
    private boolean verifyPreorderByTwoStack(int[] preorder){
        // special case
        if(preorder == null || preorder.length == 0 || preorder.length == 1){
            return true;
        }

        // we use two stacks to help us, preStack to store all left node, meaning the preStack is ascending from top to bottom. if curElem is bigger than preStack.top, then we could not push this curElem in because this should be somenode's right node, so we pop all inorder(ascending) nodes smaller than curElem to inStack, then our inStack is descending from top to bottom.
        // then we push curElem to preStack
        // at last, we push all nodes in preStack(must be ascending) to inStack
        // then we check inStack is inorder or not to verify BST
        Deque<Integer> preStack = new ArrayDeque<>();
        Deque<Integer> inStack = new ArrayDeque<>();

        for(int i = 0; i < preorder.length; i++){
            int curElem = preorder[i];

            // push all nodes smaller than curElem to inStack
            while(!preStack.isEmpty() && curElem > preStack.peek()){
                int preTop = preStack.pop();

                // put to inStack
                inStack.push(preTop);
            }

            // after process all its left nodes, curElem push to preOrder
            preStack.push(curElem);
        }

        // !!! push everything left in preStack to inStack
        while(!preStack.isEmpty()){
            inStack.push(preStack.pop());
        }

        // check if inStack is valid
        int upperNum = Integer.MAX_VALUE;
        while(!inStack.isEmpty()){
            if(upperNum < inStack.peek()){
                return false;
            }

            upperNum = inStack.pop();
        }

        return true;
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