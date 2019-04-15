package com.myleetcode.hash_table.valid_sudoku;

import java.util.HashSet;
import java.util.Set;

class Solution {
    public boolean isValidSudoku(char[][] board) {
        return isValidSudokuByHashSet(board);
    }

    // A Sudoku board (partially filled) could be valid but is not necessarily solvable.
    // Only the filled cells need to be validated according to the mentioned rules.
    // intuition: use map to represent the matrix.
    // most important part is the sub-boxes, acording to the solution: could use box_index = (row / 3) * 3 + col / 3 where / is an integer division, row is a row number, and col is a column number
    private boolean isValidSudokuByHashSet(char[][] board){
        if(board == null || board.length < 9 || board[0] == null || board[0].length < 9){
            return false;
        }

        // rows, array of set, set is chars at row
        Set<Character>[] rows = new HashSet[9];
        // cols
        Set<Character>[] cols = new HashSet[9];
        // sub boxes
        Set<Character>[] boxes = new HashSet[9];

        // init
        for(int i = 0; i < 9; i++){
            rows[i] = new HashSet<>();
            cols[i] = new HashSet<>();
            boxes[i] = new HashSet<>();
        }

        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                char c = board[i][j];
                if(c != '.'){
                    int boxIdx = (i / 3) * 3 + j / 3; // !!!
                    if(rows[i].contains(c) || cols[j].contains(c) || boxes[boxIdx].contains(c)){
                        return false;
                    }
                    rows[i].add(c);
                    cols[j].add(c);
                    boxes[boxIdx].add(c);
                }
            }
        }

        return true;

    }
}
