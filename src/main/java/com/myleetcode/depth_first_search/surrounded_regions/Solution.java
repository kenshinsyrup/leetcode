package com.myleetcode.depth_first_search.surrounded_regions;

class Solution {
    public void solve(char[][] board) {
        solveByDFS(board);
    }

    // TC: O(R*C), for loop. (necessary operations is O(V + E), V is the total Os at border, E is the total edges adjacent with V)
    // SC: O(R*C)
    // intuition: according to the problem, we just need find all the O at border and their adjacent Os, except these, we flip all cells to X
    // so it  looks like bfs or dfs either is good, so we could try dfs
    private void solveByDFS(char[][] board){
        if(board == null || board.length == 0 || board[0] == null || board[0].length == 0){
            return;
        }

        int rowLen = board.length;
        int colLen = board[0].length;

        boolean[][] visited = new boolean[rowLen][colLen];
        boolean[][] notFlip = new boolean[rowLen][colLen];
        int[][] direcs = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};


        for(int i= 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                if(isBorderO(board, i, j)){
                    dfs(board, i, j, visited, notFlip, direcs);
                }
            }
        }

        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                if(board[i][j] == 'O' && !notFlip[i][j]){
                    board[i][j] = 'X';
                }
            }
        }
    }

    // find all adjacent Os of given border O
    private void dfs(char[][] board, int rowIdx, int colIdx, boolean[][] visited, boolean[][] notFlip, int[][] direcs){
        // notFlip
        notFlip[rowIdx][colIdx] = true;

        // visited
        visited[rowIdx][colIdx] = true;

        int rowLen = board.length;
        int colLen = board[0].length;
        // traverse children
        for(int[] direc: direcs){
            int nextRowIdx = rowIdx + direc[0];
            int nextColIdx = colIdx + direc[1];

            // validate
            if(nextRowIdx >= 0 && nextRowIdx <= rowLen - 1 && nextColIdx >= 0 && nextColIdx <= colLen - 1){
                // !!! only check adjacent Os, if no adjacent O, dont expore
                if(board[nextRowIdx][nextColIdx] == 'O' && !visited[nextRowIdx][nextColIdx]){
                    dfs(board, nextRowIdx, nextColIdx, visited, notFlip, direcs);
                }
            }
        }
    }

    private boolean isBorderO(char[][] board, int i, int j){
        int rowLen = board.length;
        int colLen = board[0].length;

        return ((i == 0 || i == rowLen - 1) || (j == 0 || j == colLen - 1)) && board[i][j] == 'O';
    }
}
