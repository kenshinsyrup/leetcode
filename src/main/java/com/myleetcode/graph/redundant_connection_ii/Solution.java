package com.myleetcode.graph.redundant_connection_ii;

class Solution {
    public int[] findRedundantDirectedConnection(int[][] edges) {
        return findRedundantDirectedConnectionByUF(edges);
    }

    // https://leetcode.com/problems/redundant-connection-ii/discuss/108045/C++Java-Union-Find-with-explanation-O(n)/196095
    // the thoughts behind the sol is:
    // since we only need to remove exactly 1 edge we could get a valid tree
    // and we know in the situation below will give us a invalid tree:
    /*
There are two cases for the tree structure to be invalid. Remember these two cases are independent, means maybe 1 or maybe 2 or maybe 1and2
1) A node having two parents;
   including corner case: e.g. [[4,2],[1,5],[5,2],[5,3],[2,4]]
2) A circle exists
    */
    // so, we could conclude that there must be one and only one single node which has two parents
    // then on this node, we know it must have two edge linked it with other nodes, and must one of the two edges is the redundant one.
    // so the sol will like:
    /*
1) Check whether there is a node having two parents.
    If so, store them as candidates A and B, and set the second edge invalid.
2) Perform normal union find(now we thind edge A is valid and we should build the MST successfully with all the edges, of course except the invalid edge B).
    If find cycle:(means our assumption is not correct, if we want use edge A and other edges except edge B to buidl the MST, we failed)
            if canFir exists, means the invalid case is 1and2, return canFir;
            else if canFir not exists, means the invalid case is 1, return current edge;
    else(means no cycle, so the invalid case is 2, and since no cycle with edgeA, then must be the B)
           return canSec
    */
    /*
    for reference:
    invalid case 1: [[1,2], [1,3], [2,3]]
    invalid case 2: [[1,2], [2,3], [3,4], [4,1], [1,5]]
    invalid case 1and 2: [[1, 2], [2, 3], [3, 1], [4, 3]]
    */
    // TC: O(M * logN)
    // SC: O(N)
    private int[] findRedundantDirectedConnectionByUF(int[][] edges){
        if(edges == null || edges.length == 0 || edges[0] == null || edges[0].length == 0){
            return new int[0];
        }

        // first to check all edges to find the node with two parents, then the two edges are our candidates
        int nodeNum = edges.length;
        int[] parent = new int[nodeNum + 1]; // because nodes are 1based
        // two candidates
        int[] canFir = new int[]{-1, -1};
        int[] canSec = new int[]{-1, -1};
        for(int[] edge: edges){
            int nodeU = edge[0];
            int nodeV = edge[1];

            if(parent[nodeV] != 0){// means nodeV already have a parent, but nodeU should be its parent too, so we find the two parents node
                // canFir store the already find edge of nodeV
                canFir = new int[]{parent[nodeV], nodeV};
                // canSec store the current find edge of nodeV
                canSec = new int[]{nodeU, nodeV};
            }else{
                parent[nodeV] = nodeU;
            }
        }

        // then, we assume canSec is the redundant edge, so we could use all edges including canFir to build a MST, use UF to build it
        int[] roots = new int[nodeNum + 1];
        for (int i = 1; i < roots.length; i++) {
            roots[i] = i;
        }

        for(int[] edge: edges){
            int nodeU = edge[0];
            int nodeV = edge[1];

            // skip canSec, because it's invalid
            if(nodeU == canSec[0] && nodeV == canSec[1]){
                continue;
            }

            // since only one redundant edge, and we already remove it, then we must have a valid MST, so if we find cycle(root of nodeU and nodeV same) means our assumption is not correct
            int rootU = find(roots, nodeU);
            int rootV = find(roots, nodeV);
            if(rootU == rootV){ // cycle
                if(canFir[0] != -1){ // (invalid case 1and2)if canFir exists, and we find cycle with it, then it's the bad guy
                    return canFir;
                }else{ // (invalid case 1)otherwise, the cycle edge is the bad guy
                    return edge;
                }
            }

            // union
            union(roots, rootU, rootV);
        }

        // invalid case 2
        return canSec;
    }

    private int find(int[] roots, int node){
        if(node == roots[node]){
            return node;
        }

        roots[node] = find(roots, roots[node]);

        return roots[node];
    }

    private void union(int[] roots, int nodeU, int nodeV){
        roots[nodeU] = nodeV;
    }
}
