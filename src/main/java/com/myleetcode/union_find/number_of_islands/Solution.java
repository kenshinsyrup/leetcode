package com.myleetcode.union_find.number_of_islands;

class Solution {
    // 重点是理解题意，关于island的概念：island就是所有水平或者垂直有连接的1组成的一个联系。周围全是0.
    // 所以，example1中，所有的1都是相连的，所以island就是1
    // example2中，左上角4个1是相连的，1个island；中间的单独的一个1周围全是0，又是一个island；右下角两个1，又组成一个island。所以是3个island

    public int numIslands(char[][] grid) {
        // return numIslandsByDFS(grid);
        return numIslandsByUF(grid);
    }

    // intuition: This problem has two classic solutions, 1 is DFS; 2 is Union Find

    // Union Find
    // TC: amortized O(R*C), if only use PathCompression, not use UnionByRand O(R * C * log(R*C)). If we use PathCompression and UnionByRand both, TC should be amortized O(R*C)
    // SC: O(R * C)
    // here is a solution use PathCompression and UnionByRank both: https://leetcode.com/problems/number-of-islands/discuss/56354/1D-Union-Find-Java-solution-easily-generalized-to-other-problems

    // this problem is a Connected Components Find problem so we could use UF to solve it
    // we could flat the grid to 1-d array to build the UF tree(represented by array), then at first all components have the root -1 means no islands. Then 1 we traverse the grid, if we find a '1', we mark the the node itself as its root and increase the num of island temporarily. Then 2 we use Find to look up if it belongs to a existing islands, we check its four direcs to do this, if true, we Union them to a whole component and reduce the num of islands; if false, we find a new islands component.
    private int numIslandsByUF(char[][] grid){
        // special case
        if(grid == null || grid.length == 0 || grid[0].length == 0){
            return 0;
        }

        int rowLen = grid.length;
        int colLen = grid[0].length;

        // build roots array and init
        int[] roots = new int[rowLen * colLen];
        for(int i = 0; i < roots.length; i++){
            roots[i] = -1;
        }

        // init rank
        int[] rank = new int[rowLen * colLen];

        // explore
        int count = 0;
        int[][] direcs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                // if not island, continue
                if(grid[i][j] == '0'){
                    continue;
                }

                // current island node, convert idx in grid to idx in UF tree
                int curNode =  i * colLen + j;
                // mark self as root
                roots[curNode] = curNode;
                // increase num of island temporarily
                count++;

                // check adjacent nodes, Find if the node belongs to a existing island component
                for(int[] direc: direcs){
                    // first look up adjacent node's idx in grid and check if it's island
                    int nextNodeRowIdx = i + direc[0];
                    int nextNodeColIdx = j + direc[1];
                    if(nextNodeRowIdx < 0 || nextNodeRowIdx >= rowLen || nextNodeColIdx < 0 || nextNodeColIdx >= colLen || grid[nextNodeRowIdx][nextNodeColIdx] == '0'){
                        continue;
                    }

                    // next island node
                    int nextNode = nextNodeRowIdx * colLen + nextNodeColIdx;
                    // !!! if adjacent island is not visited yet(roots[nextNode] == -1), then we continue
                    if(roots[nextNode] == -1){
                        continue;
                    }

                    // then find roots of current node and next node, if not the same, Union and reduce the num of island
                    int curRoot = find(roots, curNode);
                    int nextRoot = find(roots, nextNode);
                    if(curRoot != nextRoot){
                        unionByRank(roots, rank, curRoot, nextRoot);
                        count--;
                    }
                }
            }
        }

        return count;

    }

    private int find(int[] roots, int node){
        if(roots[node] != node){
            roots[node] = find(roots, roots[node]);
        }

        return roots[node];
    }

    private void unionByRank(int[] roots, int[] rank, int root1, int root2){
        if(rank[root1] > rank[root2]){
            roots[root2] = root1;
        }else if(rank[root2] > rank[root1]){
            roots[root1] = root2;
        }else{
            roots[root2] = root1;
            rank[root1]++;
        }
    }


    // DFS
    // TC: O(R*C)
    // SC: O(R*C)
    private int numIslandsByDFS(char[][] grid){
        // special case
        if(grid == null || grid.length == 0 || grid[0].length == 0){
            return 0;
        }

        int rowLen = grid.length;
        int colLen = grid[0].length;

        int count = 0;
        boolean[][] visited = new boolean[rowLen][colLen];// remember the cells(actually only the islands ie '1's) that we have already visited
        int[][] direcs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                if(!visited[i][j] && grid[i][j] == '1'){
                    // 如果是1，那么开始找所有相连的1，所有相连的1他们组成的就是岛.找到的这些1都被标记为visited，这里可以直接标记为0水
                    traverseByDFS(grid, i, j, visited, direcs);

                    // 上面一步traverse完了所有与当前区块链接且为1的区域后，就标记好了一个岛，count islands
                    count++;
                }
            }
        }

        return count;

    }

    private void traverseByDFS(char[][] grid, int rowIdx, int colIdx, boolean[][] visited, int[][] direcs){
        // process current node
        visited[rowIdx][colIdx] = true;

        // explore adjacent nodes
        for(int[] direc: direcs){
            int nextRowIdx = rowIdx + direc[0];
            int nextColIdx = colIdx + direc[1];

            // here we should only visit islands, not any water ie '0', because we only want to count the island num, so we should start a brand new DFS when we done with connected '1's.
            // so we could find, here the visited is actually visitedOnes. So a tradeoff is we could not use the visited array, we could mark the visited '1's in grind to '0' to save the space, but this will change the input.
            if(nextRowIdx < 0 || nextRowIdx >= grid.length || nextColIdx < 0 || nextColIdx >= grid[0].length || visited[nextRowIdx][nextColIdx] || grid[nextRowIdx][nextColIdx] != '1'){
                continue;
            }

            traverseByDFS(grid, nextRowIdx, nextColIdx, visited, direcs);
        }
    }
}