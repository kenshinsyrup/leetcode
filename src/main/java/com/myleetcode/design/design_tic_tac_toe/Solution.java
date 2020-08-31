package com.myleetcode.design.design_tic_tac_toe;

public class Solution {
    class TicTacToe {
    /*
    !!! The key observation is that in order to win Tic-Tac-Toe you must have the entire row or column. Thus, we don't need to keep track of an entire n^2 board. We only need to keep a count for each row and column. If at any time a row or column matches the size of the board then that player has won.

    This is why we could solve the problem by count sum of row, col, diagonal and antiDiagonal.
    And this is why we only track the diagnol and antidiagnol of the board, not every diagonal or antiDiagonal of each cell.
    */
        // the thoughts is dirved from https://leetcode.com/problems/valid-tic-tac-toe-state/

        private int[] rows;
        private int[] cols;
        private int diagonal;
        private int antiDiagonal;
        private int len;

        /**
         * Initialize your data structure here.
         */
        public TicTacToe(int n) {
            this.len = n;
            this.rows = new int[len];
            this.cols = new int[len];
            this.diagonal = 0;
            this.antiDiagonal = 0;
        }

        /**
         * Player {player} makes a move at ({row}, {col}).
         *
         * @param row    The row of the board.
         * @param col    The column of the board.
         * @param player The player, can be either 1 or 2.
         * @return The current winning condition, can be either:
         * 0: No one wins.
         * 1: Player 1 wins.
         * 2: Player 2 wins.
         */
        public int move(int row, int col, int player) {
            int augend = player == 1 ? 1 : -1;

            // Row.
            this.rows[row] += augend;
            // Col.
            this.cols[col] += augend;
            // Diagonal
            if (row == col) {
                this.diagonal += augend;
            }
            // Anti-Diagonal
            if (row + col == len - 1) {
                this.antiDiagonal += augend;
            }

            // Check affected row, col, diagonal and anti-diagonal if applicable.
            if (Math.abs(this.rows[row]) == len ||
                    Math.abs(this.cols[col]) == len ||
                    Math.abs(this.diagonal) == len ||
                    Math.abs(this.antiDiagonal) == len) {
                return player;
            }

            return 0;
        }
    }

/**
 * Your TicTacToe object will be instantiated and called as such:
 * TicTacToe obj = new TicTacToe(n);
 * int param_1 = obj.move(row,col,player);
 */
}
