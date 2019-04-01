package com.myleetcode.linked_list.convert_sorted_list_to_binary_search_tree;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

import com.myleetcode.utils.list_node.ListNode;
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
    public TreeNode sortedListToBST(ListNode head) {
        return sortedListToBSTByList(head);
    }

    //TC: O(N), N is the length of linkedlist
    // SC: O(N), N extra space needed because we store linkedlist to list, recursion costs O(logN), totally is O(N)
    // intuition: convert the linkedlist to sorted array, then recursively build the tree
    private TreeNode sortedListToBSTByList(ListNode head){
        // special case
        if(head == null){
            return null;
        }

        List<Integer> nodeList = new ArrayList<>();
        while(head != null){
            nodeList.add(head.val);
            head = head.next;
        }

        return buildBST(nodeList, 0, nodeList.size() - 1);

    }

    private TreeNode buildBST(List<Integer> nodeList, int start, int end){
        if(start > end){
            return null;
        }
        if(start == end){//!!!重要，检查start==end时输出leaf node, 主要是与其他题目保持比较一致的写法，虽然这道题这里不检查没问题,还是写出来比较合适
            return new TreeNode(nodeList.get(start));
        }

        int mid = start + (end - start) / 2;
        TreeNode midNode = new TreeNode(nodeList.get(mid));

        TreeNode leftNode = buildBST(nodeList, start, mid - 1); // 不能包含重复node，所以左边是[start, mid - 1], 右边是[mid+1, end], 中间是mid
        TreeNode rightNode = buildBST(nodeList, mid + 1, end);

        midNode.left = leftNode;
        midNode.right = rightNode;

        return midNode;
    }
}
