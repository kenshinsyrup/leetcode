package com.myleetcode.design;

public class Solution {
    class TicTacToe {
        // design的问题从描述抽象出代码还挺麻烦的
        // 比这个问题还简单点https://leetcode.com/problems/valid-tic-tac-toe-state/，思路取自那里：这个题目不要求我们去validate，也就是player一定按规则走，那么我们只需要检查是否游戏结束了，游戏结束就是要么有人赢了，要么棋盘满了
        // 对于条件2，Once a winning condition is reached, no more moves is allowed.，这样的描述很吓人，其实就是说有人赢了我们renturn

        int[] rows;
        int[] cols;
        int diagonal;
        int antiDiagonal;
        // int steps; 本来考虑要不要考虑棋盘满了的问题，不过这个问题只考虑是否赢，也就是棋盘不满但没人赢和棋盘满了但没人赢都是不赢，返回0.
        int len;

        /** Initialize your data structure here. */
        public TicTacToe(int n) {
            len = n;
            rows = new int[n];
            cols = new int[n];
        }

        /** Player {player} makes a move at ({row}, {col}).
         @param row The row of the board.
         @param col The column of the board.
         @param player The player, can be either 1 or 2.
         @return The current winning condition, can be either:
         0: No one wins.
         1: Player 1 wins.
         2: Player 2 wins. */
        public int move(int row, int col, int player) {
            // 由于条件1A move is guaranteed to be valid and is placed on an empty block.，我们就不考虑输入值是否越界的问题了先.

            // row, col the two parameters are 0based

// same as validate tic-tac-toe, use 1 and -1 to mark palyer's move to count.
            int augend = player == 1 ? 1 : -1;

            // row and col
            rows[row] += augend;
            cols[col] += augend;
            // diagonal
            if(row == col){
                diagonal += augend;
            }
            // anti-diagonal
            if(row + col == len - 1){
                antiDiagonal += augend;
            }

            // 并不需要每次都检查rows和cols的所有元素，每次只需要检查被更改的即可
            if(Math.abs(rows[row]) == len ||
                    Math.abs(cols[col]) == len ||
                    Math.abs(diagonal) == len ||
                    Math.abs(antiDiagonal) == len){
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
