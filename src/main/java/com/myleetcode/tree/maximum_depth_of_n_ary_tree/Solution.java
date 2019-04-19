package com.myleetcode.tree.maximum_depth_of_n_ary_tree;

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
    public int maxDepth(Node root) {
        // 不知道为什么归类到了bfs。这个题目目测的话，用dfs和bfs都可以做，但是关于最大深度这样的问题直觉还是dfs比较好说?
//         都写一下试试
        
//         special case
        if(root == null){
            return 0;
        }
        
        // bfs work
       /* 
       return maxDepthByBFS(root);
       */
        
        // dfs recursive
        /*return maxDepthByDFSRecursion(root);*/
        
        // dfs iterative. stack
        return maxDepthByDFSIterative(root);
        
    }
    
    // DFS by recursion
    private int maxDepthByDFSRecursion(Node node){
        //exit
        if(node == null){
            return 0;
        }
        // 这里要验证的是null和isEmpty，因为如果children字段存在但是是空，也是没有子，高度也是1
        if(node.children == null || node.children.isEmpty()){
            return 1;
        }
        
        List<Integer> depths = new ArrayList<>();
        for(Node childNode: node.children){
           depths.add(maxDepthByDFSRecursion(childNode));
        }
        
        int max = -1;
        for(Integer depth: depths){
            max = Math.max(max, depth);
        }
        return max + 1;
    }
    
    // DFS by iterative. stack。要掌握
    private int maxDepthByDFSIterative(Node node){
        // use two stack to record the relation between current node and current depth
        Stack<Node> nodeStack = new Stack<>();
        Stack<Integer> depthStack = new Stack<>();
        
        nodeStack.push(node);
        depthStack.push(1);
        
        int max = -1;
        
        while(!nodeStack.isEmpty()){
            Node currentNode = nodeStack.pop();
            Integer currentDepth = depthStack.pop();
            
            // 每遍历到一个node，取出该node对应的depth与max做比较得到max
            max = Math.max(max, currentDepth);
            
            // 每次把当前node所有的子node的高度计算出来，就是当前depth+1其实，注意只加一次1.
            // 然后把所有子node及他们对应的各自的高度（相同，都是当前depth+1）推入对应的栈
            if(currentNode.children != null){
                currentDepth++;
                for(Node childNode: currentNode.children){
                    nodeStack.push(childNode);
                    depthStack.push(currentDepth);
                }
            }
        }
        
        return max;
        
    }
    
    
//     BFS by iterative(写BFS就是为了不写recursion)
    private int maxDepthByBFS(Node root){
        Queue<Node> nodeQ = new LinkedList<Node>();
        
        nodeQ.add(root);
        
        int level = 0;
        
        while(!nodeQ.isEmpty()){
            int size = nodeQ.size();// level control
            level++;
            
            for(int i = 0; i < size; i++){
                Node currentNode = nodeQ.poll();
                
                // push to queue
                for(Node childNode: currentNode.children){
                    nodeQ.add(childNode);
                }
            }
        }
        
        return level;
    }
}