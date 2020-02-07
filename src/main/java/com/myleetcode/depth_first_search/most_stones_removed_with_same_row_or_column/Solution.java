package com.myleetcode.depth_first_search.most_stones_removed_with_same_row_or_column;

import java.util.HashSet;
import java.util.Set;

class Solution {
    public int removeStones(int[][] stones) {
        return removeStonesByDFS(stones);
    }

    /*
    DFS, similar with 200. Number of Islands
    https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/discuss/209369/Java-recursive-DFS-Short-and-easy-to-understand

    Thought is most important for this problem.
    We look all the stones which is connected by each other as a island, one island eventually collapse to one 'stone'. Here connected means has same row or column.
    So if we get all the islands number, then we only need apply this: numberOfStones - numberIslands. This is the number of stones we remove from the original given stones.

    TC: O(N)
    SC: O(N)
    */
    private int removeStonesByDFS(int[][] stones){
        if(stones == null || stones.length == 0){
            return 0;
        }

        int islandNum = 0;
        Set<int[]> visited = new HashSet<>();
        for(int[] stone: stones){
            if(!visited.contains(stone)){
                dfs(stones, visited, stone);
                islandNum++;
            }
        }

        return stones.length - islandNum;
    }

    private void dfs(int[][] stones, Set<int[]> visited, int[] stone){
        visited.add(stone);

        for(int[] nextStone: stones){
            if(!visited.contains(nextStone) && (stone[0] == nextStone[0] || stone[1] == nextStone[1])){
                dfs(stones, visited, nextStone);
            }
        }
    }

}
