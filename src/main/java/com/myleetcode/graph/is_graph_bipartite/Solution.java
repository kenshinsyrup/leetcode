package com.myleetcode.graph.is_graph_bipartite;

class Solution {
    // 关于题目：eg1 [[1,3], [0,2], [1,3], [0,2]] 这个graph的意思是，index 0 就是 node0， 有两个连接点node1和node3；index 1就是node1，有两个连接点node0和node2；index 2就是node2，有两个连接点node1和node3；同理最后一个index 3就是node3，有两个连接点node0和node2
    // 关于染色法判断的正确性：Color a node blue if it is part of the first set, else red. We should be able to greedily color the graph if and only if it is bipartite: one node being blue implies all it's neighbors are red, all those neighbors are blue, and so on.
    // Slight explanation of correctness: why is it that we can be sure that as long as we have a cycle, where we try to color one node with a different color the second time, we know for sure the graph is bad? This is because the node just prior to the conflicting node must have the same color as the conflicting node itself. And as long as such a cycle exists, there is no way you can bipartite-partition all nodes within this cycle because, well, these two just won't do. What if there are no cycles? Well, cycle-free graphs are always bipartite.
    // Multiple DFS is required since the nodes might not be fully connected. Each DFS will color one independent subgraph. If one DFS discovers that one subgraph is okay, we can safely move on: it does not matter anymore because this subgraph and its consisting nodes won't further affect any other independent subgraph.

    public boolean isBipartite(int[][] graph) {
        return isBipartiteByDFS(graph);
    }

    // TC: O(V + E),遍历了所有的节点V，并且所有的E都只走了一遍(染过色的直接return了，所以只有第一个node走了所有的edge，后续的都只访问了一下自己的neighbors就立刻return了，所以TC是E),总体即为V + E
    // SC: O(V)
    // intuition: color node by dfs. traver node in graph, for every node,
    private boolean isBipartiteByDFS(int[][] graph){
        // special case
        if(graph == null || graph.length == 0){
            return true;
        }

        // color (3-status, 0 for not visited yet)
        int[] colored = new int[graph.length];
        // traverse every edge: u-v
        for(int i = 0; i < graph.length; i++){
            // initialColor这里是一个优化，最初的时候，考虑到我们每开始一个图中新vertex的dfs的时候，要重置colored数组为0，然后coloredByDFS中shouldColor染色为1.这样是没有问题的，但是运行时间19ms，击败6%左右。但是稍微想一下就可以知道，我们之所以要重置colored数组，是因为我们不知道在他之前的最初一个vertex遍历时经过这个node时给他染了什么颜色，所以，如果我们能知道，就相当于知道了他开始自己的dfs染色的时候已经有了一个initial color，那么直接用这个initial color，这样不用重置整个colored数组，就会在自己bfs过程中已经被之前染过色的都直接return，速度会快很多。
            // 那么重点就变成了如何获得initial color，我们规定了0是为染色状态，那么判定下如果不是0，就说明有过染色，直接用，否则就是没经历过染色，给定1。(!!!注意如果是连通图，任意一个节点的dfs结束所有的node就已经被访问过了; 如果不是连通图，我们可以把不联通的图视作一个单独的图，那么我们for循环到该图第一个节点，即处理第一个属于他的节点时colored[i]肯定是0，并且该非联通部分都是为染色，所以给定initialColor为1也是没问题的)
            int initialColor = 1;
            if(colored[i] != 0){
                initialColor = colored[i];
            }
            if(!coloredByDFS(graph, i, colored, initialColor)){
                return false;
            }

            /*
            未优化时写法
            colored = new int[graph.length];
             if(!coloredByDFS(graph, i, colored, 1)){
                return false;
            }
            */
        }

        return true;

    }

    private boolean coloredByDFS(int[][] graph, int u, int[] colored, int shouldColor){
        // base case, if current color equals to shouldColor, true, we could color this vertex to keep compatible with its parent vertex
        if(colored[u] != 0){
            return colored[u] == shouldColor;
        }

        // color it
        colored[u] = shouldColor;

        int[] children = graph[u];
        for(int i = 0; i < children.length; i++){
            if(!coloredByDFS(graph, children[i], colored, -shouldColor)){
                return false;
            }
        }

        return true;

    }
}
