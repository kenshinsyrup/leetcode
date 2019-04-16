package com.myleetcode.union_find.number_of_connected_components_in_an_undirected_graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public int countComponents(int n, int[][] edges) {
        // return countComponensByDFS(n, edges);
        return countComponentsByUnionFind(n, edges);
    }

    // TC: O(N + M*(log*(N))), could be looked as O(N + M). actually because normal cases # of edges >> # of nodes, so could be looked as O(M*(log*(N))) or O(M)
    // here N is because we need a N loop to init root array
    // M*(log*(N)) is far more better than O(M*logN) where M is edges number and N is total nodes,  because with path-compression, onely the first tiem find may cost O(N) then every time find this vertex again is O(1), so find is called log*(N) here * is not "multiply", is called "star" meaning how many "log" button do you press to get the N to 0, actually normal case the log*(N) is less than 10, for eg, log*(65535) is 5.
    //
    // there's another way to solve this proble with Union-Find, because this is good at looking for connected components
    private int countComponentsByUnionFind(int n, int[][] edges){
        if(n <= 0){
            return 0;
        }
        if(edges == null || edges.length == 0 || edges[0] == null || edges[0].length == 0){
            return n;
        }

        // init our components and roots
        int[] roots = new int[n];
        for(int i = 0; i < n; i++){
            roots[i] = i;
        }

        int componentNum = n;
        for(int[] edge: edges){
//             for every edge(u,v), we find the root of vertex and v to see if they are in the same component, if not, union them and reduce the total components num
            int rootU = find(edge[0], roots);
            int rootV = find(edge[1], roots);
            if(rootU != rootV){
                // union
                roots[rootU] = roots[rootV];

                // reduce the components number
                componentNum--;
            }
        }

        return componentNum;
    }

    // find given vertex's root and do path-compression
    private int find(int vertex, int[] roots){
        // if vertex is not the component's root that it belongs to, then do continue find and do path-compression
        if(roots[vertex] != vertex){
            roots[vertex] = find(roots[vertex], roots);
        }

        // retrun vertex's root after find
        return roots[vertex];
    }

    // Time : O(V + E)
    // Space: O(V + E), (E is at most V^2 for sure), DFS is O(V) for both time and space, because basically you just DFS every vertex
    // intuition: same like count islands problem. DFSAll on graph and keep record all visited node should work
    private int countComponensByDFS(int n, int[][] edges){
        // could not has no nodes
        if(n <= 0){
            return 0;
        }
        // !!!check. because we could have no any edges but have many nodes so is no edges we just return n is correct.
        if(edges == null || edges.length == 0 || edges[0] == null || edges[0].length == 0){
            return n;
        }

        // build adjacent-list form graph
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for(int[] edge: edges){
            // !!! undirected graph, add both vertex as start node in every edge to graph map
            graph.putIfAbsent(edge[0], new ArrayList<>());
            graph.get(edge[0]).add(edge[1]);

            graph.putIfAbsent(edge[1], new ArrayList<>());
            graph.get(edge[1]).add(edge[0]);
        }

        // visited, since this is undirected, we only need one two-state indicator to mark nodes. here we know the given graph has n nodes
        boolean[] visitedNodes = new boolean[n];

        int count = 0;
        for(int i = 0; i < n; i++){
            if(!visitedNodes[i]){
                count++;
                graphDFS(graph, visitedNodes, i);
            }
        }

        return count;
    }

    private void graphDFS(Map<Integer, List<Integer>> graph, boolean[] visitedNodes, int curNode){
        // base case, visited
        if(visitedNodes[curNode]){
            return;
        }

        // record current node
        visitedNodes[curNode] = true;

        // dfs child, must check if this curNode exists in graph as start node.
        if(graph.get(curNode) != null){
            for(int childNode: graph.get(curNode)){
                graphDFS(graph, visitedNodes, childNode);
            }
        }
    }
}
