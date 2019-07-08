package com.myleetcode.tree.binary_search_tree.construct_binary_search_tree_from_preorder_traversal;

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
    public TreeNode bstFromPreorder(int[] preorder) {
        return bstFromPreorderByRecursion(preorder);
    }

    /*
    出错点：
    1 忘记检查leftSubtree和rightSubtree的存在性与否，后续在反应过来之后，检查的过程还是有错误。实际上，最好的方式是查找rightSubtreeStart。
    我们知道给定array的范围为[start, end], rootIdx是start, 所以，leftSubtree和rightSubtree的可选范围为[start+1, end]. 在此之中，我们可以去查找leftSubtreeStart和rightSubtreeStart，对于leftSubtreeStart，我们可以简单的设置为start+1；rightSubtreeStart则设置为end+1，即假设rightSubtree不存在，然后我们在start+1到end之间尝试搜索rightSubtreeStart，此搜索会有两个结果，如果搜索到，那么存在rightSubtree，那么[leftSubtreeStart,rightSubtreeStart-1]即为leftSubtree范围(特殊情况就是搜索到的rightSubtreeStart等于leftSubtreeStart即所有元素均为rightSubtree，那么该范围不存在node为null，正确)；如果搜索不到，那么leftSubtree范围为[leftSubtreeStart, end]，仍然是[leftSubtreeStart,rightSubtreeStart-1]。至于rightSubtree，其范围为[rightSubtreeStart, end], 如果搜索到了rightSubtreeStart则该范围存在，否则由于默认值为end+1则该范围不存在，所以node为null，正确
    */

    // intuition: recursion
    // since the Tree is a BST, so for a given preorder array [start, end] representing this BST, every elem we met is a root of a subtree, let's say its idx is rootIdx, then for all elems in [rootIdx+1, end], elems smaller than rootIdx val is in the left subtree and larger than rootIdxval is in the right subtree
    // TC: O(N)
    // SC: O(N)
    private TreeNode bstFromPreorderByRecursion(int[] preorder){
        if(preorder == null || preorder.length == 0){
            return null;
        }

        return buildBSTByPreorder(preorder, 0, preorder.length - 1);
    }

    private TreeNode buildBSTByPreorder(int[] preorder, int start, int end){
        // base case, out of boundary, null node
        if(start < 0 || end >= preorder.length || start > end){
            return null;
        }
        // base case, non null leaf node
        if(start == end){
            return new TreeNode(preorder[start]);
        }

        // root
        int rootIdx = start;
        TreeNode rootNode = new TreeNode(preorder[rootIdx]);

        // get left and right subtree range
        int leftSubtreeStart = rootIdx + 1; // !!!
        int rightSubtreeStart = end + 1; // !!!
        for(int i = rootIdx + 1; i <= end; i++){
            if(preorder[i] > preorder[rootIdx]){
                rightSubtreeStart = i;
                break;
            }
        }

        TreeNode leftSubtree = buildBSTByPreorder(preorder, leftSubtreeStart, rightSubtreeStart - 1);
        TreeNode rightSubtree = buildBSTByPreorder(preorder, rightSubtreeStart, end);

        rootNode.left = leftSubtree;
        rootNode.right = rightSubtree;

        return rootNode;
    }
}
