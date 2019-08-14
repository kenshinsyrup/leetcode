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
        // return verticalOrderByBFS(root);
        return verticalOrderByBFSII(root);
    }

    // 2
    // 根据1，TC可以不这么大的，因为我们发现其实主要的操作只是O(n),sort那个占用了大部分时间的操作反而不是重点，所以可以优化的。比如，我们知道col-nums一定是连续的，因为我们从0开始，左-1，右+1，所以只要我们记录一个最大的col num，记录一个最小的col num，那么for(i = min; i<= max; i++){colNodes.get(i)}这样就可以顺序获取到所有的node values。降低TC到O(n).也就是2.
    // 实际上，这里能用minCol和maxCol就记录得到所有的col nums是一种特例，因为col nums是连续的
    // TC: O(N)
    // SC: O(N)
    private List<List<Integer>> verticalOrderByBFSII(TreeNode root){
        // special case
        if(root == null){
            return new ArrayList<List<Integer>>();
        }

        // keep node-column pair
        Map<TreeNode, Integer> nodeColMap = new HashMap<>();

        // keep column->List<Node Val> pair
        Map<Integer, List<Integer>> colNodesMap = new HashMap<>();

        // queue for BFS
        Deque<TreeNode> nodeQueue = new ArrayDeque<>();

        // init
        nodeQueue.offer(root);
        nodeColMap.put(root, 0);
        int minCol = 0; // left most col
        int maxCol = 0; // right most col
        while(!nodeQueue.isEmpty()){
            TreeNode curNode = nodeQueue.poll();

            // get its column num, check if this column num as a key existing in colNodes: if exist, put it in list; if not, create list and put it in.
            Integer col = nodeColMap.get(curNode);
            if(!colNodesMap.containsKey(col)){
                colNodesMap.put(col, new ArrayList<Integer>());
            }
            colNodesMap.get(col).add(curNode.val);

            // check children nodes, set their column number and put to nodeCol, and enqueue
            if(curNode.left != null){
                nodeQueue.offer(curNode.left);
                nodeColMap.put(curNode.left, col - 1);

                // min col num
                minCol = Math.min(minCol, col - 1);
            }
            if(curNode.right != null){
                nodeQueue.add(curNode.right);
                nodeColMap.put(curNode.right, col + 1);

                // max col num
                maxCol = Math.max(maxCol, col + 1);
            }
        }

        // result
        List<List<Integer>> ret = new ArrayList<>();
        for(int i = minCol; i <= maxCol; i++){
            ret.add(colNodesMap.get(i));
        }

        return ret;
    }

    // 1
    // intuition: since it said from top to bottom when vertical order traverse, we could congsider if this means we should use BFS? And, if we use BFS, we find we could get left and right child of current node, this is 3-status: 0, -1, 1 and make sense.
    // so, we know we could use BFS and make root at column 0, left child -1 and right child +1  based on root. So we could distinguish columns. for the record, map is good, we could have a map to keep node-column pair.
    // but it's too hard to reach all column with this only one map, so we could use another map to keep column-node.values pair for help.
    // TC: O(N * logN), BFS use O(n), sort use O(nlogn), total is O(nlogn)
    // SC: O(n) additional space
    private List<List<Integer>> verticalOrderByBFS(TreeNode root){
        // special case
        if(root == null){
            return new ArrayList<List<Integer>>();
        }

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
