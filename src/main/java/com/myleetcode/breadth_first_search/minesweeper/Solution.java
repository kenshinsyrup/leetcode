package com.myleetcode.breadth_first_search.minesweeper;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public char[][] updateBoard(char[][] board, int[] click) {
        return updateBoardByBFS(board, click);
    }

    /*
    BFS:

    N is nodes number.
    TC: O(N)
    SC: O(N)
    */
    private char[][] updateBoardByBFS(char[][] board, int[] click) {
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return board;
        }
        if (click == null || click.length == 0) {
            return board;
        }

        // Check the given click cell is Mine or not.
        if (board[click[0]][click[1]] == 'M') {
            board[click[0]][click[1]] = 'X';
            return board;
        }

        int rowLen = board.length;
        int colLen = board[0].length;
        int[][] direcs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        boolean[][] queued = new boolean[rowLen][colLen];

        Deque<int[]> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(click);
        queued[click[0]][click[1]] = true;
        while (!nodeQueue.isEmpty()) {
            int[] curNode = nodeQueue.poll();
            int rowIdx = curNode[0];
            int colIdx = curNode[1];

            // For every queued node, first check its surrounding mine counts.
            int count = 0;
            for (int[] direc : direcs) {
                int nextRowIdx = rowIdx + direc[0];
                int nextColIdx = colIdx + direc[1];

                if (nextRowIdx < 0 || nextRowIdx >= rowLen || nextColIdx < 0 || nextColIdx >= colLen) {
                    continue;
                }

                if (board[nextRowIdx][nextColIdx] == 'M') {
                    count++;
                }
            }
            // If there're mines surrounding this cell, update the number to this cell.
            if (count != 0) {
                board[rowIdx][colIdx] = (char) ('0' + count);
            } else { // Otherwise, mark this cell as B and enqueue all its surrounding cells.
                board[rowIdx][colIdx] = 'B';

                for (int[] direc : direcs) {
                    int nextRowIdx = rowIdx + direc[0];
                    int nextColIdx = colIdx + direc[1];

                    if (nextRowIdx < 0 || nextRowIdx >= rowLen || nextColIdx < 0 || nextColIdx >= colLen) {
                        continue;
                    }

                    if (!queued[nextRowIdx][nextColIdx]) {
                        nodeQueue.offer(new int[]{nextRowIdx, nextColIdx});
                        queued[nextRowIdx][nextColIdx] = true;
                    }
                }
            }
        }

        return board;
    }
}
