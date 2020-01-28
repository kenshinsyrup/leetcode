package com.myleetcode.tree.binary_search_tree.inorder_successor_in_bst_ii;

import com.myleetcode.utils.node_with_parent.Node;

public /*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node parent;
};
*/
class Solution {
    public Node inorderSuccessor(Node node) {
        return inorderSuccessorByBSTFeature(node);
    }

    /*
    https://leetcode.com/problems/inorder-successor-in-bst-ii/discuss/255801/Java-Solution-with-Explanations

    There're two conditiions:
    1. Node has a right child, and hence its successor is somewhere lower in the tree. To find the successor, go to the right once and then as many times to the left as you could.
    2. Node has no right child, then its successor is somewhere upper in the tree. To find the successor, go up till the node that is left child of its parent. The answer is the parent. Beware that there could be no successor (= null successor) in such a situation.

    H is tree height, range from logN to N.
    TC: O(H)
    SC: O(H)
    */
    private Node inorderSuccessorByBSTFeature(Node node) {
        if (node == null) {
            return null;
        }

        // Has right subtree.
        if (node.right != null) {
            return findSuccessor(node);
        }

        // No rght subtree, in this problem, we need to find the first node whose leftsubtree contains given node
        while (node.parent != null && node.parent.left != node) {
            node = node.parent;
        }
        return node.parent;

    }

    private Node findSuccessor(Node node) {
        Node curNode = node.right;
        while (curNode.left != null) {
            curNode = curNode.left;
        }

        return curNode;
    }
}
