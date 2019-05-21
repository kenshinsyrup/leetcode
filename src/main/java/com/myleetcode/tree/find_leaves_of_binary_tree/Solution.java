package com.myleetcode.tree.find_leaves_of_binary_tree;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayList;
import java.util.List;

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
    public List<List<Integer>> findLeaves(TreeNode root) {
        // return findLeavesByDFS(root);
        return findLeavesByHeight(root);
    }

    // TC: O(N)
    // SC: O(H)
    // https://leetcode.com/problems/find-leaves-of-binary-tree/discuss/83778/10-lines-simple-Java-solution-using-recursion-with-explanation
    // optimize thought: actually we could not really reach leaves and remove them and repeat. we could use the Tree Height concept to help us solve this problem: Height is the distance from current node to leaf, and leaf has 0 height. so this problem could be simplized to add all same height nodes to List.
    private List<List<Integer>> findLeavesByHeight(TreeNode root){
        List<List<Integer>> ret = new ArrayList<>();

        if(root == null){
            return ret;
        }

        getHeightAndPutNodesByDFS(root, ret);

        return ret;
    }

    private int getHeightAndPutNodesByDFS(TreeNode curNode, List<List<Integer>> ret){
        if(curNode == null){
            return -1;
        }

        int leftH = getHeightAndPutNodesByDFS(curNode.left, ret);
        int rightH = getHeightAndPutNodesByDFS(curNode.right, ret);

        // current node's height
        int curH = Math.max(leftH, rightH) + 1;

        // height is the index of the nodes's container in the ret List, ie leaf should be in the 0th container, father of leaf should be in the 1th container ...
        // check if ret has such container, if not, add
        if(curH > ret.size() - 1){
            ret.add(new ArrayList<>());
        }
        // put
        ret.get(curH).add(curNode.val);

        return curH;
    }

    private int getHeight(TreeNode curNode){
        if(curNode == null){
            return -1;
        }

        int leftH = getHeight(curNode.left);
        int rightH = getHeight(curNode.right);

        return Math.max(leftH, rightH) + 1;
    }

    // TC: O(H * N), H is height, N is total nodes #
    // SC: O(H)
    // intuition: Postorder Tree Traversal
    // DFS, when we reach a leaf's parent, put it leaf child into the leaves List, then set it to null. when all done, we get the outtest leaves. then we do DFS again... until root is null. here's a tricky part, we should process the root carefully, if root is leaf, we put it into leafList and set it directly to null because it has no parent.
    private List<List<Integer>> findLeavesByDFS(TreeNode root){
        List<List<Integer>> ret = new ArrayList<>();

        if(root == null){
            return ret;
        }

        while(root != null){
            List<Integer> leafList = new ArrayList<>();

            // !!! here we must process the root specially, because it has no parent, so we should collect it and set it to null here
            boolean rootIsLeaf = dfs(root, leafList);
            if(rootIsLeaf){
                leafList.add(root.val);
                root = null;
            }

            ret.add(new ArrayList(leafList));
        }

        return ret;
    }

    // return true if is leaf
    private boolean dfs(TreeNode curNode, List<Integer> leafList){
        // base case
        if(curNode == null){
            return true;
        }

        // if leaf, return true
        if(curNode.left == null && curNode.right == null){
            return true;
        }

        // if not leaf, explore leaf, our purpus is to find the parent node of leaf then process
        // explore to find leaves
        boolean left = dfs(curNode.left, leafList);
        boolean right = dfs(curNode.right, leafList);

        // add to leafList and set to null
        if(left && curNode.left != null){
            leafList.add(curNode.left.val);
            curNode.left = null;
        }
        if(right && curNode.right != null){
            leafList.add(curNode.right.val);
            curNode.right = null;
        }

        return false;
    }
}
