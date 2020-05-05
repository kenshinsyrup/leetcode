package com.myleetcode.tree.complete_binary_tree_inserter;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode() {}
     * TreeNode(int val) { this.val = val; }
     * TreeNode(int val, TreeNode left, TreeNode right) {
     * this.val = val;
     * this.left = left;
     * this.right = right;
     * }
     * }
     */
    class CBTInserter {

    /*
    Also, we have solution could use the complete feature, convert the tree into a nodeList, then put nodes or find parent by its index.
    https://leetcode.com/problems/complete-binary-tree-inserter/discuss/178424/C%2B%2BJavaPython-O(1)-Insert

    Store tree nodes to a list in bfs order.
    Node nodeList[i] has left child nodeList[2 * i + 1] and nodeList[2 * i + 2]

    So when insert the Nth node (0-indexed), we push it into the list.
    And we can find its parent nodeList[(N - 1) / 2] directly.
    */

        /*
        Improved BFS solution.
        Improve the time complexity of insert to O(number of nodes at last two levels of the tree), though still O(n) in worst case.
        */
        TreeNode root;
        Deque<TreeNode> nodeQueue;

        public CBTInserter(TreeNode root) {
            this.root = root;
            this.nodeQueue = new ArrayDeque<>();
            if (this.root != null) {
                this.nodeQueue.offer(this.root);
            }
        }

        /*
        TC: O(N)
        SC: O(N)
        */
        public int insert(int v) {
            if (this.root == null) {
                this.root = new TreeNode(v);
                this.nodeQueue.offer(this.root);
                return this.root.val;
            }

            // !!! The most important part is, we should treat a node as useful if it has 0 or 1(left) child, and insert new node as its child. Although after we insert right child to curNode it's complete, we should poll it out next loop, that way we could guarantee we only add everynode's children into queue exactly once.
            while (!nodeQueue.isEmpty()) {
                TreeNode curNode = nodeQueue.peek();

                // If has no children, means we need insert in its left.
                if (curNode.left == null && curNode.right == null) {
                    // Build its left child.
                    curNode.left = new TreeNode(v);

                    return curNode.val;
                } else if (curNode.right == null) { // If only has left child.
                    // Build its right child.
                    curNode.right = new TreeNode(v);

                    return curNode.val;
                } else { // Has two children, this node has no use anymore.
                    nodeQueue.poll();

                    nodeQueue.offer(curNode.left);
                    nodeQueue.offer(curNode.right);
                }
            }

            return -1;

        }

        public TreeNode get_root() {
            return this.root;
        }


//     /*
//     Naive BFS solution.
//     */
//     TreeNode root;

//     public CBTInserter(TreeNode root) {
//         this.root = root;
//     }

//     /*
//     TC: O(N)
//     SC: O(N)
//     */
//     public int insert(int v) {
//         if(this.root == null){
//             this.root = new TreeNode(v);
//             return this.root.val;
//         }

//         // BFS, insert in the first available position.
//         Deque<TreeNode> nodeQueue = new ArrayDeque<>();
//         nodeQueue.offer(this.root);
//         while(!nodeQueue.isEmpty()){
//             TreeNode curNode = nodeQueue.poll();

//             if(curNode.left != null){
//                 nodeQueue.offer(curNode.left);
//             }else{
//                 curNode.left = new TreeNode(v);
//                 return curNode.val;
//             }

//             if(curNode.right != null){
//                 nodeQueue.offer(curNode.right);
//             }else{
//                 curNode.right = new TreeNode(v);
//                 return curNode.val;
//             }
//         }

//         return -1;

//     }

//     public TreeNode get_root() {
//         return this.root;
//     }
    }

/**
 * Your CBTInserter object will be instantiated and called as such:
 * CBTInserter obj = new CBTInserter(root);
 * int param_1 = obj.insert(v);
 * TreeNode param_2 = obj.get_root();
 */
}


/**
 * Your CBTInserter object will be instantiated and called as such:
 * CBTInserter obj = new CBTInserter(root);
 * int param_1 = obj.insert(v);
 * TreeNode param_2 = obj.get_root();
 */
