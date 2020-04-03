package com.myleetcode.tree.n_ary_tree_preorder_traversal;

/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/

import com.myleetcode.utils.n_aray_tree_node.Node;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class Solution {
    public List<Integer> preorder(Node root) {
        // return preorderByRecursion(root);
        return preorderByIteration(root);
    }

    /*
    TC: O(N)
    SC: O(N)
    */
    private List<Integer> preorderByIteration(Node root) {
        List<Integer> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }

        Node node = root;
        Deque<Node> nodeStack = new ArrayDeque<>();
        nodeStack.push(node);
        while (!nodeStack.isEmpty()) {
            Node curNode = nodeStack.pop();

            ret.add(curNode.val);

            for (int i = curNode.children.size() - 1; i >= 0; i--) {
                nodeStack.push(curNode.children.get(i));
            }
        }

        return ret;
    }

    /*
    TC: O(N)
    SC: O(H)
    */
    private List<Integer> preorderByRecursion(Node root) {
        List<Integer> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }

        recursion(root, ret);

        return ret;
    }

    private void recursion(Node node, List<Integer> ret) {
        if (node == null) {
            return;
        }

        ret.add(node.val);

        for (Node child : node.children) {
            recursion(child, ret);
        }
    }
}
