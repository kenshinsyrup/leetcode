package com.myleetcode.tree.two_sum_iv_input_is_a_bst;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public boolean findTarget(TreeNode root, int k) {
        // return findTargetByDFSAll(root, k);// WRONG
        // return findTargetByDFS(root, k);
        return findTargetByInorderList(root, k);
    }

    // 解法1，inroder到list，然后two sum的经典解法，双指针求和
    // TC: O(N)
    // SC: O(N)
    // intuition: 1 we could convert the BST to list by inorder traverse, then two pointer to search sum equals to k to solve the two sum problem. This will cost TC O(N) and SC O(N) where N is the number of nodes
    private boolean findTargetByInorderList(TreeNode root, int k){
        if(root == null){
            return false;
        }

        List<Integer> nodeList = new ArrayList<>();
        inorder(root, nodeList);

        int start = 0;
        int end = nodeList.size() - 1;
        while(start < end){
            int sum = nodeList.get(start) + nodeList.get(end);

            if(sum == k){
                return true;
            }else if(sum < k){
                start++;
            }else{
                end--;
            }
        }
        return false;
    }

    private void inorder(TreeNode node, List<Integer> nodeList){
        if(node == null){
            return;
        }

        inorder(node.left, nodeList);
        nodeList.add(node.val);
        inorder(node.right, nodeList);
    }

    // 解法2, Two Sum的经典方法，找差
    // TC: O(N)
    // SC: O(N)
    private boolean findTargetByDFS(TreeNode root, int k){
        Set<Integer> completmentSet = new HashSet<>();
        return dfs(root, k, completmentSet);
    }

    private boolean dfs(TreeNode node, int k, Set<Integer> completmentSet){
        if(node == null){
            return false;
        }

        int target = k - node.val;
        if(completmentSet.contains(target)){
            return true;
        }

        completmentSet.add(node.val);

        if(dfs(node.left, k, completmentSet)){
            return true;
        }

        if(dfs(node.right, k, completmentSet)){
            return true;
        }

        return false;

    }

    // Wrong, because misunderstanding the problem. 这个题目要求的是任意两个node和为k，而不是任意node与subtree中的node，举例就是输入为[2,1,3] 4,那么应该是true，因为两个叶子1和3虽然是sibling但是却可以和为4. 所以我们可以借助map或者set来按照two sum的方式来做
    // TC: O(N*H), O(N*log(N)), we visited all nodes ie recursion is N, in every recursion, we do a recursion which's height is H.
    // SC: O(N*H)
    // we could reduce the cost by do search in BST itself. we us DFSAll to check all nodes, for every node, we get a target form k - node.val, then do a DFS to check if the target exists in the node's subtree. when we do this, we only need to check half subtree, if target > node.val, then search right subtree, otherwise left subtree.
    private boolean findTargetByDFSAll(TreeNode root, int k){
        if(root == null){
            return false;
        }

        // 这里findDFS还会对root自己做一次是否等于k-root.val的检查，所以在输入为[1] 2这样的内容时，会返回正确，原因是root用了两次，这样是不对的,需要避免root被使用两次的情况
        // if(findDFS(root, k - root.val)){
        //     return true;
        // }
        if(findDFS(root, k - root.val, true)){
            return true;
        }

        // 这里可以优化下，如果要检查左子树，那么肯定要满足条件 k < 2*root.val,因为root的左子树都小于root，那么两个小于root的值相加不可能大于root+root。这样可以短路一些肯定不可能成功的情况
        // if(findTargetByDFSAll(root.left, k)){
        //     return true;
        // }
        if((k < 2 * root.val) && findTargetByDFSAll(root.left, k)){
            return true;
        }

        // 同理，这里可以优化为，额外检查 k > 2*root
        // if(findTargetByDFSAll(root.right, k)){
        //     return true;
        // }
        if((k > 2 * root.val) && findTargetByDFSAll(root.right, k)){
            return true;
        }

        return false;
    }

    private boolean findDFS(TreeNode node, int target, boolean isRoot){
        if(node == null){
            return false;
        }

        System.out.println("node val:"+node.val+" isRoot"+isRoot+" tar: "+target);

        if(!isRoot && node.val == target){
            return true;
        }

        if(node.val < target){
            return findDFS(node.right, target, false);
        }

        if(node.val > target){
            return findDFS(node.left, target, false);
        }

        return false;
    }

}
