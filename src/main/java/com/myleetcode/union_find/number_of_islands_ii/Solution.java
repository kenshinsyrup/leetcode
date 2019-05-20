package com.myleetcode.union_find.number_of_islands_ii;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        return numIslandsByUnionFind(m, n, positions);
    }

    // https://leetcode.com/problems/number-of-islands-ii/discuss/75470/Easiest-Java-Solution-with-Explanations
    // intuition: Union Find to find connected components after every addLand op
    // note 1: compress the 2-d grapd to 1-d array to build the Union-Find roots array(Represent the Union-Find Tree)
    // note 2: for every node(the cell in positions, ie the addLand() param), we check it's four direcs to find if there's adjacent islands, if has, find it's root and union to it.
    // note 3: at start, every elem in root array, has init root -1, menas no island at first. then when we do addLand, we make it's root itself, then find adjacent island, if has, update root, if not, it's a isolated island.
    // TC: O(L + M*N), L is addLand ops
    // SC: O(M*N)
    private List<Integer> numIslandsByUnionFind(int m, int n, int[][] positions){
        List<Integer> ret = new ArrayList<>();

        if(m <= 0 || n <= 0 || positions == null || positions[0].length == 0){
            return ret;
        }

        int[][] direcs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        int[] roots = new int[m * n];// union find array, represent tree

        // at first, no island
        for(int i = 0; i < m * n; i++){
            roots[i] = -1;
        }

        // add land
        int count = 0;
        for(int[] pos: positions){
            // current land pos map to the roots array
            int curNodeIdx = pos[0] * n + pos[1];

            // !!! check if we have already addLand ath this pos, if has, record the count and continue
            if (roots[curNodeIdx] != -1) {    // duplicate position
                ret.add(count);
                continue;
            }

            // init, we make it's root be itself
            roots[curNodeIdx] = curNodeIdx;

            // since we add a land, we increasing count
            count++;

            //then we check adjacent 4 positions to find if it connects to a existing island
            for(int[] direc: direcs){
                // out of graph range, continue
                // if(pos[0] + direc[0] < 0 || pos[0] + direc[0] >= m || pos[1] + direc[1] < 0 || pos[1] + direc[1] >= n){
                //     continue;
                // }

                int adjX = pos[0] + direc[0];
                int adjY = pos[1] + direc[1];
                if(adjX < 0 || adjX >= m || adjY < 0 || adjY >= n){
                    continue;
                }

                // adjacent node
                int nextNodeIdx = adjX * n + adjY;
                // if adjacent is not island, continue
                if(roots[nextNodeIdx] == -1){
                    continue;
                }

                // if island, find its root and if curNode's root is not the same, union
                int curNodeRoot = find(roots, curNodeIdx);
                int nextNodeRoot = find(roots, nextNodeIdx);
                if(curNodeRoot != nextNodeRoot){
                    roots[curNodeRoot] = nextNodeRoot;

                    // since we find curent island belongs to an existing island, we decreasing count
                    count--;
                }
            }

            // after current addLand op, record the island # to ret
            ret.add(count);
        }

        return ret;
    }

    // !!!
    private int find(int[] roots, int node){
        if(roots[node] == node){
            return node;
        }

        roots[node] = find(roots, roots[node]);
        return roots[node];
    }
}
