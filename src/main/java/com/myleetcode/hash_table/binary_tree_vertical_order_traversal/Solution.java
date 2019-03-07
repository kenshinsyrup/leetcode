package com.myleetcode.hash_table.binary_tree_vertical_order_traversal;

import com.myleetcode.utils.tree_node.TreeNode;

import java.util.*;

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
    public List<List<Integer>> verticalOrder(TreeNode root) {

        // special case
        if(root == null){
            return new ArrayList<List<Integer>>();
        }

        // return verticalOrderByBFS(root);
        // return verticalOrderByBFSII(root);
        return verticalOrderByBFSAndTreeMap(root);
    }

    // 3
    private List<List<Integer>> verticalOrderByBFSAndTreeMap(TreeNode root){
        // keep node-column pair
        Map<TreeNode, Integer> nodeCol = new HashMap<TreeNode, Integer>();

        // keep column-node.vals pair
        Map<Integer, List<Integer>> colNodes = new TreeMap<Integer, List<Integer>>();

        // queue for BFS
        Queue<TreeNode> nodeQ = new LinkedList<TreeNode>();

        // init
        nodeQ.add(root);
        nodeCol.put(root, 0);

        while(!nodeQ.isEmpty()){
            TreeNode curNode = nodeQ.poll();

            // get its column num, check if this column num as a key existing in colNodes: if exist, put it in list; if not, create list and put it in.
            Integer col = nodeCol.get(curNode);
            if(!colNodes.containsKey(col)){
                colNodes.put(col, new ArrayList<Integer>());
            }
            colNodes.get(col).add(curNode.val);

            // check children nodes, set their column number and put to nodeCol, and enqueue
            if(curNode.left != null){
                nodeCol.put(curNode.left, col - 1);
                nodeQ.add(curNode.left);
            }
            if(curNode.right != null){
                nodeCol.put(curNode.right, col + 1);
                nodeQ.add(curNode.right);
            }
        }

        // result
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        for(Integer col: colNodes.keySet()){
            ret.add(colNodes.get(col));
        }

        return ret;
    }


    // 2
    // TC: O(n)
// 实际上，这里能用minCol和maxCol就记录得到所有的col nums是一种特例，因为col nums是连续的。所以实际也可以通过使用TreeMap来记录col-node.vals的方式来达到同样的目的，也就是3
    private List<List<Integer>> verticalOrderByBFSII(TreeNode root){
        // keep node-column pair
        HashMap<TreeNode, Integer> nodeCol = new HashMap<TreeNode, Integer>();

        // keep column-node.vals pair
        HashMap<Integer, List<Integer>> colNodes = new HashMap<Integer, List<Integer>>();

        // queue for BFS
        Queue<TreeNode> nodeQ = new LinkedList<TreeNode>();

        // init
        nodeQ.add(root);
        nodeCol.put(root, 0);
        int minCol = 0;
        int maxCol = 0;

        while(!nodeQ.isEmpty()){
            TreeNode curNode = nodeQ.poll();

            // get its column num, check if this column num as a key existing in colNodes: if exist, put it in list; if not, create list and put it in.
            Integer col = nodeCol.get(curNode);
            if(!colNodes.containsKey(col)){
                colNodes.put(col, new ArrayList<Integer>());
            }
            colNodes.get(col).add(curNode.val);

            // check children nodes, set their column number and put to nodeCol, and enqueue
            if(curNode.left != null){
                nodeCol.put(curNode.left, col - 1);
                nodeQ.add(curNode.left);
                // min col num
                minCol = Math.min(minCol, col - 1);
            }
            if(curNode.right != null){
                nodeCol.put(curNode.right, col + 1);
                nodeQ.add(curNode.right);
                // max col num
                maxCol = Math.max(maxCol, col + 1);
            }
        }

        // result
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        for(int i = minCol; i <= maxCol; i++){
            ret.add(colNodes.get(i));
        }

        return ret;

    }

    // 1
    // TC: BFS use O(n), sort use O(nlogn), total is O(nlogn).
    // SC: O(n) additional space
    // 但是TC可以不这么大的，因为我们发现其实主要的操作只是O(n),sort那个占用了大部分时间的操作反而不是重点，所以可以优化的。比如，我们知道col-nums一定是连续的，因为我们从0开始，左-1，右+1，所以只要我们记录一个最大的col num，记录一个最小的col num，那么for(i = min; i<= max; i++){colNodes.get(i)}这样就可以顺序获取到所有的node values。降低TC到O(n).也就是2.
    private List<List<Integer>> verticalOrderByBFS(TreeNode root){
        // intuition: since it said from top to bottom when vertical order traverse, we could congsider if this means we should use BFS? And, if we use BFS, we find we could get left and right child of current node, this is 3-status: 0, -1, 1 and make sense.
        // so, we know we could use BFS and make root at column 0, left child -1 and right child +1  based on root. So we could distinguish columns. for the record, map is good, we could have a map to keep node-column pair.
        // but it's too hard to reach all column with this only map, so we could use another map to keep column-node.values pair for help.

        // keep node-column pair
        HashMap<TreeNode, Integer> nodeCol = new HashMap<TreeNode, Integer>();

        // keep column-node.vals pair
        HashMap<Integer, List<Integer>> colNodes = new HashMap<Integer, List<Integer>>();

        // queue for BFS
        Queue<TreeNode> nodeQ = new LinkedList<TreeNode>();

        // init
        nodeQ.add(root);
        nodeCol.put(root, 0);
        // colNodes.put(0, new ArrayList<TreeNode>(root)); no need, we should update colNodes when BFS

        while(!nodeQ.isEmpty()){
            TreeNode curNode = nodeQ.poll();

            // get its column num, check if this column num as a key existing in colNodes: if exist, put it in list; if not, create list and put it in.
            Integer col = nodeCol.get(curNode);
            if(!colNodes.containsKey(col)){
                colNodes.put(col, new ArrayList<Integer>());
            }
            colNodes.get(col).add(curNode.val);

            // check children nodes, set their column number and put to nodeCol, and enqueue
            if(curNode.left != null){
                nodeCol.put(curNode.left, col - 1);
                nodeQ.add(curNode.left);
            }
            if(curNode.right != null){
                nodeCol.put(curNode.right, col + 1);
                nodeQ.add(curNode.right);
            }
        }

        // get column nums and sort ascending. Stupid Java, keySet() returns set and its sorting syntax is too hard to write. so just convert set to list.
        List<Integer> cols = new ArrayList<Integer>(colNodes.keySet());
        Collections.sort(cols);

        // result
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        for(Integer col: cols){
            ret.add(colNodes.get(col));
        }

        return ret;

    }
}
