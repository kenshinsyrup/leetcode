package com.myleetcode.graph.clone_graph;

import com.myleetcode.utils.clone_graph.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {}

    public Node(int _val,List<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
};
*/
class Solution {
    public Node cloneGraph(Node node) {
        // https://leetcode.com/problems/clone-graph/discuss/42462/Java-solution-with-DFS-and-BFS
        // HashMap用于去环，如果我们在clone一个node时，发现这个node已经在map的key里，说明我们处理过了，那么直接return他的value，这样就没有再对他的neighbors做dfs递归，去掉环才能保证我们不会陷入死循环
        // 比如eg1，我们输入1,那么clone一个copy1,然后1作为key(去环的关键)，copy1作为value存入store 然后dfs处理1的neighbors: 2, 4
        // 先处理其中一个neighbor比如2，那么就是clone 2，那么dfs递归了，然后我们store没有2，那么new一个copy2，然后2和copy2存入store，然后dfs处理2的neighbors: 1,3
        // 先处理一个neighbor比如1，store已经存在1，直接返回copy1。那么这个copy1被加入copy2的neighbors；然后继续处理另一个neighbor 3，做一个copy3，存入store，处理3的neighbors:2,4
        // 先处理2，store已经有了2，返回copy2，那么copy2被copy3的neighbors加入；再处理4，store已经有了4，返回copy4，那么copy4被copy3的neighbors加入
        // 3处理完了，return copy3，那么copy2的neighbors增加copy3；
        // 没想eg1这么深，不写了，差不多得了
        Map<Node, Node> store = new HashMap<>();

        return cloneNode(node, store);
    }

    // dfs from given node to clone graph
    // recurse: clone current node; traverse all its neighbors by dfs
    private Node cloneNode(Node node, Map<Node, Node> store){
        // base case: we reach a node has been copied, return
        if(store.containsKey(node)){
            return store.get(node);
        }

        // make a new Node, with give node's val, then build its neighbors, then store it in the store map, copyNode is the value and the value of given node
        Node copyNode = new Node(node.val, new ArrayList<>());

        store.put(node, copyNode); // must put in store first, then do the following dfs

        for(Node n: node.neighbors){
            copyNode.neighbors.add(cloneNode(n, store));
        }

        return store.get(node);

    }
}
