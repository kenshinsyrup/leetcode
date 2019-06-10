package com.myleetcode.divide_and_conquer.kth_smallest_element_in_a_bst;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.Stack;

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
    // 2 recursive
    // for kthSmallestByRecursive to count and record ans
    // better keep these two variables in a wrapper class
    /*
    private static int number = 0;
    private static int count = 0;
    */

    // 3 recursive with helper class withour global variable.
    class Ans{
        int count;
        int number;

        Ans(int k){
            count = k;
        }
    }

    public int kthSmallest(TreeNode root, int k) {
        //         https://leetcode.com/problems/kth-smallest-element-in-a-bst/discuss/63660/3-ways-implemented-in-JAVA-(Python)%3A-Binary-Search-in-order-iterative-and-recursive
        // 这个用dfs来做in order的遍历应该是最直观的想法。link above里面DFS in-order iterative的基本思路就是把root和左子树压入栈，然后pop并计数。如果pop完了还没有达到k个，那么压入右子树，继续。
        // Intuition: traverse inorder, keep counting, when k is 0 we get the node.val and this is the answer.
        // traverse a tree inorder, we have recursive and iterative way.

        // special case
        if(root == null || k < 0){
            return -1;
        }

        // 1 iterative, in-order traversal with stack
        // return kthSmallestByIterative(root, k);

        // 2 recursive, in-order traversal
        /*
        count = k;
        number = 0;
        kthSmallestByRecursive(root);
        return number;
        */

        // 3 recursive, in-order traversal with helper class withour global variable.
        // Ans ans = new Ans(k);
        // kthSmallestByRecursiveWithHelpClass(root, ans);
        // return ans.number;

        // 4 Divide and Conquer, find kth problem
        return kthSmallestByDivideAndConquer(root, k);
    }

    private int kthSmallestByDivideAndConquer(TreeNode root, int k){
        //         如果用divide and conquer的话。这种找第k个的，都应该可以用同一套思路：我们从当前开始分组，分成左右两组，如果左边个数大于k了，那么k肯定在左边，右边抛弃掉，然后继续用同样思路处理左边。如果左边个数小于k，那么k肯定在右边，那么抛弃左边，继续同样的思路处理右边。如果刚好左边刚好等于k，那左边最后一个就是k。
//         不过这个题目有个小注意的点，这个k是node的个数，而一个node，他左边的子树的个数，和右边的子树的个数，再要加上自己，才是整个树的个数。要记住node本身要占一个数。

        // 左边子树的个数
        int leftCounts = countTree(root.left);
//         如果k等于当前节点的左子树的节点数加上节点自己，那么说明我们找到了第k个节点，就是当前节点。这里也是BST的特性。
        if(k == leftCounts + 1){
            return root.val;
        }else if(k <= leftCounts){
//             说明第k个节点在左子树里面。
            return kthSmallest(root.left, k);
        }else{
//             说明在右子树。在右子树的话，就要考虑到，我们要找的的是第k个，但是第leftCounts+1个已经有了，所以在右子树要找的就是k - leftCounts -1个
            return kthSmallest(root.right, k - leftCounts -1);
        }
    }

    private int countTree(TreeNode node){
        if(node == null){
            return 0;
        }

        return 1 + countTree(node.left) + countTree(node.right);
    }


    private int kthSmallestByIterative(TreeNode node, int k){
        // inorder , dfs
        Stack<TreeNode> nodeS = new Stack<TreeNode>();

        // init
        // left to leaf
        while(node != null){
            nodeS.push(node);
            node = node.left; // to the most left
        }

        while(k > 0 && !nodeS.isEmpty()){
            TreeNode n = nodeS.pop(); // get 'center' root
            k--;
            if(k == 0){
                return n.val;
            }

            // here we must get and check n.right before we push to stack. Otherwise, there's a possibility that n.right is null and we must not push null to stack because if there's null node in stack, the n.val above will encounter runtime error.
            TreeNode right = n.right; // get 'right' node of 'center' node
            while(right != null){
                nodeS.push(right);
                right = right.left; // continue go to the new subtree's most left
            }
        }

        return -1;

    }


    // 2 recursive
    /*
    private void kthSmallestByRecursive(TreeNode node){
        if(node == null){
            return;
        }

        kthSmallestByRecursive(node.left); // to the left most

        // process 'center'
        count--;
        if(count == 0){
            number = node.val;
            return;
        }

        kthSmallestByRecursive(node.right); // go to the 'right' node and then continue to the left most

    }
    */

    // 3 recursive with helper class withour global variable.
    private void kthSmallestByRecursiveWithHelpClass(TreeNode node, Ans ans){
        if(node == null){
            return;
        }

        kthSmallestByRecursiveWithHelpClass(node.left, ans); // to the left most

        // process 'center'
        ans.count--;
        if(ans.count == 0){
            ans.number = node.val;
            return;
        }

        kthSmallestByRecursiveWithHelpClass(node.right, ans); // go to the 'right' node and then continue to the left most

    }

}