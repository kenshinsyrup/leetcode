package com.myleetcode.breadth_first_search.zero_one_matrix;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int[][] updateMatrix(int[][] matrix) {
        // return updateMatrixByDFSAll(matrix);// WRONG
        // return updateMatrixByBFS(matrix);
        return updateMatrixByBFSII(matrix);
    }

    // TC: O(N^2) or say O(R*C), R is # of rows and C is # of cols
    // SC: O(N^2)
    /*
For our BFS routine, we keep a queue, q to maintain the queue of cells to be examined next.
We start by adding all the cells with 0s to q.
Intially, distance for each 0 cell is 0 and distance for each 1 is INT_MAX, which is updated during the BFS.
Pop the cell from queue, and examine its neighbours. If the new calculated distance for neighbour {i,j} is smaller, we add {i,j} to q and update dist[i][j].
    */
    private int[][] updateMatrixByBFSII(int[][] matrix){
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            return new int[0][0];
        }

        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        int[][] ret = new int[rowLen][colLen];

        int[][] directions = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};// 4 direction neighbors

        // queue for node, node is (i,j)
        Deque<int[]> nodeQueue = new ArrayDeque<>();
        // build the queue
        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                if(matrix[i][j] == 0){
                    nodeQueue.offer(new int[]{i, j});
                    ret[i][j] = 0;
                }else{
                    ret[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        while(!nodeQueue.isEmpty()){
            int[] curNode = nodeQueue.poll();
            int curX = curNode[0];
            int curY = curNode[1];

            for(int i = 0; i < directions.length; i++){
                int xAug = curX + directions[i][0];
                int yAug = curY + directions[i][1];

                if(xAug >= 0 && xAug < rowLen && yAug >= 0 && yAug < colLen){
                    // relax
                    if(ret[xAug][yAug] > ret[curX][curY] + 1){
                        ret[xAug][yAug] = ret[curX][curY] + 1;
                        // since we updated this node, we need consider we may need update its neighbors, so we put it into the queue
                        nodeQueue.offer(new int[]{xAug, yAug});
                    }
                }
            }
        }

        return ret;
    }


    // we could optimize this by isolate the nested for loop with the Queue BFS
    // TC: O(N^3)
    // SC: O(N^2)
    // this solution costs a lot because in the nested for loop, we have a Queue to do BFS, the nested for loop cost O(N^2), and the BFS cost O(N) in worst case(although has nested for loop in the BFS it's actually costant time).
    private int[][] updateMatrixByBFS(int[][] matrix){
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            return new int[0][0];
        }

        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        int[][] ret = new int[rowLen][colLen];

        int[][] directions = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};// 4 direction neighbors

        // queue for node, node is (i,j)
        Deque<int[]> nodeQueue = new ArrayDeque<>();

        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                if(matrix[i][j] == 0){
                    ret[i][j] = 0;
                    continue;
                }

                int dist = 0;
                nodeQueue.offer(new int[]{i, j});
                while(!nodeQueue.isEmpty()){
                    int size = nodeQueue.size();
                    for(int s = 0; s < size; s++){
                        int[] curNode = nodeQueue.poll();
                        if(matrix[curNode[0]][curNode[1]] == 0){
                            // find 0, clear queue
                            nodeQueue.clear();
                            ret[i][j] = dist;
                            break;
                        }

                        for(int k = 0; k < directions.length; k++){
                            int xAug = curNode[0] + directions[k][0];
                            int yAug = curNode[1] + directions[k][1];
                            if(xAug >= 0 && xAug < rowLen && yAug >= 0 && yAug < colLen){
                                nodeQueue.offer(new int[]{xAug, yAug});
                            }
                        }
                    }

                    // increase the dist per level
                    dist++;
                }
            }
        }

        return ret;
    }


    // but DFS is not good at these Nearest problem, it's hard to avoid cycle and at the mean time to get the real nearest dist.
    // intuition: DFS to find the neareast 0 in 4 directions, record the smallest one. DFSAll.
    private int[][] updateMatrixByDFSAll(int[][] matrix){
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            return new int[0][0];
        }

        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        int[][] ret = new int[rowLen][colLen];


        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                // reset visited
                boolean[][] visited = new boolean[rowLen][colLen];
                ret[i][j] = dfsFindZero(matrix, i, j, visited, 0);
            }
        }

        return ret;
    }


    private int dfsFindZero(int[][] matrix, int rowIdx, int colIdx, boolean[][] visited, int dist){
        // special case
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        if(rowIdx < 0 || rowIdx > rowLen - 1 || colIdx < 0 || colIdx > colLen - 1){
            return Integer.MAX_VALUE;
        }

        // base, check self is 0 or not, if 0, return dist
        if(matrix[rowIdx][colIdx] == 0){
            return dist;
        }

        // self is not 0, then traverse 4 neighbors
        if(visited[rowIdx][colIdx]){ // avoid cycle
            return Integer.MAX_VALUE;
        }
        visited[rowIdx][colIdx] = true; // visited
        dist += 1; // dist increase

        int min = Integer.MAX_VALUE;
        int[][] directions = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};// 4 direction neighbors
        // we choose the smallest dist in our 4 neighbors
        for(int i = 0; i < directions.length; i++){
            int xAug = rowIdx + directions[i][0];
            int yAug = colIdx + directions[i][1];

            min = Math.min(min, dfsFindZero(matrix, xAug, yAug, visited, dist));
        }

        return min;
    }
}
