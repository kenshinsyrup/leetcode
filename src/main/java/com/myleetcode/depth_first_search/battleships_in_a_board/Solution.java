package com.myleetcode.depth_first_search.battleships_in_a_board;

class Solution {
    public int countBattleships(char[][] board) {
        return countBattleshipsByDFSAll(board); // original
        // return countBattleshipsByCountFirst(board); // follow up
    }

    // then, the follow up need us to solve this with one-pass and O(1) SC and not modify the original board
    /*
    kind of tricky: https://leetcode.com/problems/battleships-in-a-board/discuss/90902/Simple-Java-Solution
    if a cell is ., then next;
    if a cell is X,
    since X is connected if they are adjacent horizontally or vertically, so if this cell's upper cell is X or left cell is X, means this cell is not the first cell in a rectangle area of X. otherwise, this cell is the first cell of a area, ie the leftup cell of an area, count++
    */
    // TC: O(R * C)
    // SC: O(1)
    private int countBattleshipsByCountFirst(char[][] board){
        if(board == null || board.length == 0 || board[0] == null || board[0].length == 0){
            return 0;
        }

        int count = 0;
        int rowLen = board.length;
        int colLen = board[0].length;
        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                // if ., next
                if(board[i][j] == '.'){
                    continue;
                }

                // if X
                if(i > 0 && board[i - 1][j] == 'X'){
                    continue;
                }
                if(j > 0 && board[i][j - 1] == 'X'){
                    continue;
                }

                count++;
            }
        }

        return count;
    }

    // intuition: DFSALl
    // visit a cell with DFS, if it's a ".", or visited, next; if it's a "X", mark it as visited, visited all its neighbors, after this X cell processed, then count++
    // TC: O(R * C)
    // SC: O(R * C)
    private int countBattleshipsByDFSAll(char[][] board){
        if(board == null || board.length == 0 || board[0] == null || board[0].length == 0){
            return 0;
        }

        // DFSAll
        int count = 0;
        int rowLen = board.length;
        int colLen = board[0].length;
        boolean[][] visited = new boolean[rowLen][colLen];
        int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                // if not X or visited, next cell
                if(visited[i][j] || board[i][j] == '.'){
                    continue;
                }

                processByDFS(board, i, j, directions, visited);
                count++;
            }
        }

        return count;
    }

    private void processByDFS(char[][] board, int rowIdx, int colIdx, int[][] directions, boolean[][] visited){
        visited[rowIdx][colIdx] = true;

        for(int[] direc: directions){
            int nextRowIdx = rowIdx + direc[0];
            int nextColIdx = colIdx + direc[1];

            // out of boundary or . or visited, continue
            if(nextRowIdx < 0 || nextRowIdx >= board.length || nextColIdx < 0 || nextColIdx >= board[0].length || board[nextRowIdx][nextColIdx] == '.' || visited[nextRowIdx][nextColIdx]){
                continue;
            }

            processByDFS(board, nextRowIdx, nextColIdx, directions, visited);
        }
    }
}
