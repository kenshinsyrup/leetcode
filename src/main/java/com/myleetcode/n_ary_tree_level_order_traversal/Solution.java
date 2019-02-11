package com.myleetcode.n_ary_tree_level_order_traversal;

/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val,List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/
class Solution {
    public List<List<Integer>> levelOrder(Node root) {
//         looks like binary tree traversal by bfs
        
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        
        // special case
        if(root == null){
            return ret;
        }
        
        traversalByBFS(root, ret);
        
        return ret;
        
    }
    
    private void traversalByBFS(Node root, List<List<Integer>> ret){
        Queue<Node> nodeQ = new LinkedList<Node>();
        
        nodeQ.add(root);
        
        int level = 0;
        
        while(!nodeQ.isEmpty()){
            int queueSize = nodeQ.size();// level control
            level++;
            
            List<Integer> values = new ArrayList<>();
            
            for(int i = 0; i < queueSize; i++){
                Node currentNode = nodeQ.poll();
                values.add(currentNode.val);
                
                // add all child nodes to queue
                for(Node child: currentNode.children){
                    nodeQ.add(child);
                }
            }
            
            ret.add(values);
            
        }

    }
}