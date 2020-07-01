package com.myleetcode.backtracking.word_search;

class Solution {
    public boolean exist(char[][] board, String word) {
        // return existByBacktracking(board, word);
        return existByBacktrackingII(board, word);
    }

    /*
    More general format.
    */
    private boolean existByBacktrackingII(char[][] board, String word) {
        // special case
        if (word == null || word.length() == 0) {
            return true;
        }
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return false;
        }

        int[][] direcs = new int[][]{{0, -1}, {0, 1}, {1, 0}, {-1, 0}};
        int rowLen = board.length;
        int colLen = board[0].length;
        boolean[][] visited = new boolean[rowLen][colLen];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (backtrackingII(board, word, visited, sb, direcs, i, j)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean backtrackingII(char[][] board, String word, boolean[][] visited, StringBuilder sb, int[][] direcs, int rowIdx, int colIdx) {
        sb.append(board[rowIdx][colIdx]);
        visited[rowIdx][colIdx] = true;
        if (sb.toString().equals(word)) {
            return true;
        }
        if (sb.length() >= word.length()) {
            sb.deleteCharAt(sb.length() - 1);
            visited[rowIdx][colIdx] = false;
            return false;
        }

        int rowLen = board.length;
        int colLen = board[0].length;
        for (int[] direc : direcs) {
            int nextRowIdx = rowIdx + direc[0];
            int nextColIdx = colIdx + direc[1];
            if (nextRowIdx < 0 || nextRowIdx >= rowLen || nextColIdx < 0 || nextColIdx >= colLen) {
                continue;
            }
            if (visited[nextRowIdx][nextColIdx]) {
                continue;
            }
            if (board[nextRowIdx][nextColIdx] != word.charAt(sb.length())) { // If next needed char is not the char in board, continue.
                continue;
            }

            if (backtrackingII(board, word, visited, sb, direcs, nextRowIdx, nextColIdx)) {
                return true;
            }
        }

        sb.deleteCharAt(sb.length() - 1);
        visited[rowIdx][colIdx] = false;
        return false;
    }

    // intuition:
    // backtracking
    private boolean existByBacktracking(char[][] board, String word) {

        // we have source list char[][] board, we have target String word
        // we need a temp StringBuilder to sotre mid-result
        // we need a idx to tell us we are finding which char in word
        // according to eg 3, dont use the same char more than once

        int[][] direction = new int[][]{{0, 0}, {0, -1}, {0, 1}, {1, 0}, {-1, 0}};
        boolean[][] visiting = new boolean[board.length][board[0].length];
        StringBuilder sb = new StringBuilder();
        int wordIdx = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (backtracking(board, word, visiting, sb, direction, i, j, wordIdx)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean backtracking(char[][] board, String word, boolean[][] visiting, StringBuilder sb, int[][] direction, int i, int j, int wordIdx) {
        if (sb.length() == word.length()) {
            return true;
        }

        // 循环所有节点时，注意能查找的下一个节点只能是当前节点的四个方向之一
        for (int k = 0; k < direction.length; k++) {
            int nextI = i + direction[k][0];
            int nextJ = j + direction[k][1];

            if (nextI < 0 || nextI > board.length - 1 || nextJ < 0 || nextJ > board[0].length - 1) {
                continue;
            }

            // check
            if (!visiting[nextI][nextJ] && board[nextI][nextJ] == word.charAt(wordIdx)) {
                sb.append(board[nextI][nextJ]);
                wordIdx++;
                visiting[nextI][nextJ] = true;

                if (backtracking(board, word, visiting, sb, direction, nextI, nextJ, wordIdx)) {
                    return true;
                }

                sb.deleteCharAt(sb.length() - 1);
                wordIdx--;
                visiting[nextI][nextJ] = false;
            }
        }

        return false;
    }

}