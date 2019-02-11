package com.myleetcode.divide_and_conquer.kth_smallest_element_in_a_bst;

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
    public int kthSmallest(TreeNode root, int k) {
//         https://leetcode.com/problems/kth-smallest-element-in-a-bst/discuss/63660/3-ways-implemented-in-JAVA-(Python)%3A-Binary-Search-in-order-iterative-and-recursive
        // 这个用dfs来做in order的遍历应该是最直观的想法。link above里面DFS in-order iterative的基本思路就是把root和左子树压入栈，然后pop并计数。如果pop完了还没有达到k个，那么压入右子树，继续。
        
        // follow up:For each node, we store the number of nodes in its subtree, including itself. Then we just search for a node with k - 1 elements before it and return its value. Insert and delete would update these counts and would remain O(log n).


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
}