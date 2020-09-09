package com.myleetcode.breadth_first_search.snakes_and_ladders;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    public int snakesAndLadders(int[][] board) {
        return snakesAndLaddersByBFS(board);
    }

    /*
    Problem explaination: https://www.youtube.com/watch?v=OutDY_ICb80
    Solution: https://leetcode.com/problems/snakes-and-ladders/discuss/173682/Java-concise-solution-easy-to-understand/206065

    TC: O(R * C)
    SC: O(R * C)
    */
    private int snakesAndLaddersByBFS(int[][] board) {
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return 0;
        }

        int len = board.length;
        boolean[] visited = new boolean[len * len + 1];

        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(1);
        int move = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                // for (int i = size - 1; i >= 0; i--) {
                int num = queue.poll();
                if (visited[num]) {
                    continue;
                }
                visited[num] = true;

                if (num == len * len) {
                    return move;
                }

                for (int dice = 1; dice <= 6; dice++) {
                    if (num + dice > len * len) {
                        break;
                    }

                    int next = num + dice;
                    // If value is not -1, means we have to take the snake or ladder to its destination.
                    int value = getBoardValue(board, next);
                    if (value > 0) {
                        next = value;
                    }

                    if (!visited[next]) {
                        queue.offer(next);
                    }
                }
            }

            move++;
        }

        return -1;
    }

    /*
    originalRowidx and originalColIdx is based on the given board, ie the start point is the left bottom cell.
    But we are given a int[][] board which has the normal coordination system, ie the start point is the left up cell.
    So we have to normalize the originalIdx to idx to correctly get the cell value in given board.

    Check the handdrawing diagram in the leetcode problem description to understand this clearly. Consider the two pairs:
    originalRowIdx 0, originalColIdx 0
    originalRowIdx 1, originalColIdx 5
    */
    private int getBoardValue(int[][] board, int num) {
        int len = board.length;

        int originalRowIdx = (num - 1) / len;
        int rowIdx = len - 1 - originalRowIdx;

        int originalColIdx = (num - 1) % len;
        int colIdx = originalRowIdx % 2 == 0 ? originalColIdx : len - 1 - originalColIdx;

        return board[rowIdx][colIdx];
    }
}
