package com.myleetcode.backtracking.n_queens_ii;

class Solution {
    public int totalNQueens(int n) {
        return solveNQueensByDFSBacktrackingIII(n);
    }

    // same as 51. N-Queens, just record num even easier.
    /*
    Time complexity : O(N!). There is N possibilities to put the first queen, not more than N (N - 2) to put the second one, not more than N(N - 2)(N - 4) for the third one etc. In total that results in O(N!) time complexity.
Space complexity : O(N^2) to keep an information about board
    */
    // the correct solution: https://leetcode.com/problems/n-queens/discuss/19805/My-easy-understanding-Java-Solution
    public int solveNQueensByDFSBacktrackingIII(int n) {
        if(n <= 0){
            return 0;
        }

        // board
        char[][] board = new char[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                board[i][j] = '.';
            }
        }

        Result ret = new Result(0);

        // from col 0
        backtrackingIII(board, 0, ret);

        return ret.val;
    }

    private void backtrackingIII(char[][] board, int colIndex, Result ret) {
        if(colIndex == board.length) {
            ret.val++;
            return;
        }

        // explore rows in cur colIndex
        for(int i = 0; i < board.length; i++) {
            if(validateIII(board, i, colIndex)) {
                board[i][colIndex] = 'Q';

                backtrackingIII(board, colIndex + 1, ret);

                board[i][colIndex] = '.';
            }
        }
    }

    private boolean validateIII(char[][] board, int rowIdx, int colIdx) {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j] == 'Q'){
                    if(i == rowIdx || j == colIdx){
                        return false;
                    }
                    if(i + j == rowIdx + colIdx || i + colIdx == rowIdx + j){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    class Result{
        int val;
        public Result(int val){
            this.val = val;
        }
    }
}
