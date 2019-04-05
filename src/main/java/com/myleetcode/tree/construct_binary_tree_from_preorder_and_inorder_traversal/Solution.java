package com.myleetcode.tree.construct_binary_tree_from_preorder_and_inorder_traversal;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.HashMap;
import java.util.Map;

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
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // return buildTreeByRecursion(preorder, inorder);
        return buildTreeByRecursionWithMap(preorder, inorder);
    }

    // TC: O(N)
    // SC: O(N)
    // actually we could reduce the time to O(N), because in every recursion we try to find a value in array that makes we cost a lot of time(log(N) ~ N). if we could make the lookup operation to O(1) then we could get a O(N) TC. So we could use a map to pre-process the inorder array to store value-index to map which gives us O(1) time to find any specific value's exact index.
    private TreeNode buildTreeByRecursionWithMap(int[] preorder, int[] inorder){
        if(preorder == null || preorder.length == 0){
            return null;
        }
        if(inorder == null || inorder.length == 0){
            return null;
        }
        if(preorder.length != inorder.length){
            return null;
        }

        // preprocess inorder array to map
        Map<Integer, Integer> inorderMap = new HashMap<>();
        for(int i = 0; i < inorder.length; i++){
            inorderMap.put(inorder[i], i);
        }

        return buildTreeNode(preorder, 0, inorderMap, 0, inorder.length - 1);

    }

    private TreeNode buildTreeNode(int[] preorder, int preStart, Map<Integer, Integer> inorderMap, int inStart, int inEnd){
        if(preorder.length == 0 || preStart >= preorder.length){
            return null;
        }
        if(inStart > inEnd){
            return null;
        }

        TreeNode root = new TreeNode(preorder[preStart]);

        int rootIdx = inorderMap.get(root.val);

        int leftPreStart = preStart + 1;
        TreeNode leftNode = buildTreeNode(preorder, leftPreStart, inorderMap, inStart, rootIdx - 1);

        int rightPreStart = preStart + (rootIdx - inStart) + 1;
        TreeNode rightNode = buildTreeNode(preorder, rightPreStart, inorderMap, rootIdx + 1, inEnd);

        root.left = leftNode;
        root.right = rightNode;

        return root;
    }

    // TC: O(N*log(N)), worst O(N^2)
    // SC: O(N)
    /*
    Space: O(N)
Because each node we create a helper(), the recursion stack will cost O(N)

Time: O(N log N) for a balanced tree, O(N^2) for a skew tree
As mentioned above, the helper() runs O(N) time, and for each helper(), there is a for-loop to search the inorder index.

For a balanced tree, the range of the search will be reduced by half each time, so the search costs O(log n)
Therefore the time is O(N) * O(log N) = O(N log N)

For a skew tree, the range of the search will only be reduced by 1, so the search still costs O(N)
Therefore the time is O(N) * O(N) = O(N^2)
    */
    // https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/discuss/34538/My-Accepted-Java-Solution
    // 重点是明白preorder和inorder之间的关系，preorder的某个元素vi，在inorder中所有vi左边的肯定是位于vi的左子树，在vi右边的肯定在vi的右子树
    // 然后就是，左子树的root和右子树的root在preorder中的index如何计算
    private TreeNode buildTreeByRecursion(int[] preorder, int[] inorder){
        if(preorder == null || preorder.length == 0){
            return null;
        }
        if(inorder == null || inorder.length == 0){
            return null;
        }
        if(preorder.length != inorder.length){
            return null;
        }

        // build tree
        return buildNode(preorder, 0, inorder, 0, inorder.length - 1);

    }

    // [preStart:] is current tree's preorder array, the index preStart is its root
    // [inStart:inEnd] is current tree's inorder array, say the preorder[preStart] value为preV0, and preV0 is at the index of iIn in inorder, then [inStart:iIn) is the left subtree of root, (iIn:inEnd] is the right subtree of root
    private TreeNode buildNode(int[] preorder, int preStart, int[] inorder, int inStart, int inEnd){
        // check
        if(preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0){
            return null;
        }
        // check preorder array
        if(preStart >= preorder.length){
            return null;
        }
        // check inorder array
        if(inStart > inEnd){
            return null;
        }

        // construct root
        TreeNode root = new TreeNode(preorder[preStart]);

        // find the root idx in inorder array
        int rootIdx = 0;
        for(int i = inStart; i <= inEnd; i++){
            if(inorder[i] == root.val){
                rootIdx = i;
            }
        }

        // left node
        int leftPreStart = preStart + 1;
        TreeNode leftNode = buildNode(preorder, leftPreStart, inorder, inStart, rootIdx - 1);

        // right node, !!!这个rightPreStart的计算方式比较重要，leftPreStart+1
        int rightPreStart = preStart + (rootIdx - inStart) + 1;
        TreeNode rightNode = buildNode(preorder, rightPreStart, inorder, rootIdx + 1, inEnd);

        root.left = leftNode;
        root.right = rightNode;

        return root;
    }
}
