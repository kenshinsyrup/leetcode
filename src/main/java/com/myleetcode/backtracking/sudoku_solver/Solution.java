package com.myleetcode.backtracking.sudoku_solver;

import java.util.HashSet;
import java.util.Set;

class Solution {
    public void solveSudoku(char[][] board) {
        solveSudokuByBacktracking(board);
    }

    // https://leetcode.com/problems/sudoku-solver/discuss/15752/Straight-Forward-Java-Solution-Using-Backtracking
    // intuition: this is a backtracking problem, we need try num through 1-9 to fill one empty cell till find solution
    private void solveSudokuByBacktracking(char[][] board){
        if(board == null || board.length < 9 || board[0] == null || board[0].length < 9){
            return;
        }

        // we use the idea in 36. Valid Sudoku to help us check if we could place one char at one cell
        // for check if a place is valid to put num k
        Set<Character>[] rows = new HashSet[9];
        Set<Character>[] cols = new HashSet[9];
        Set<Character>[] boxes = new HashSet[9];
        //init
        for(int i = 0; i < 9; i++){
            rows[i] = new HashSet<>();
            cols[i] = new HashSet<>();
            boxes[i] = new HashSet<>();
        }
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                int boxIdx = (i / 3) * 3 + j / 3;

                // add as used
                rows[i].add(board[i][j]);
                cols[j].add(board[i][j]);
                boxes[boxIdx].add(board[i][j]);
            }
        }

        backtracking(board, rows, cols, boxes);
    }

    // straight forward idea, this is not very efficient but very intuitive, maybe later to find a more efficient solution because dont think this will appear in an interview = =.
    private boolean backtracking(char[][] board, Set<Character>[] rows, Set<Character>[] cols, Set<Character>[] boxes){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                char curChar = board[i][j];
                int boxIdx = (i / 3) * 3 + j / 3;

                if(curChar == '.'){
                    for(char k = '1'; k <= '9'; k++){
                        if(!(rows[i].contains(k) || cols[j].contains(k) || boxes[boxIdx].contains(k))){
                            // put it
                            board[i][j] = k;
                            rows[i].add(k);
                            cols[j].add(k);
                            boxes[boxIdx].add(k);

                            // continue explore
                            if(backtracking(board, rows, cols, boxes)){
                                return true;
                            }

                            // remove it
                            board[i][j] = '.';
                            rows[i].remove(k);
                            cols[j].remove(k);
                            boxes[boxIdx].remove(k);
                        }
                    }
                    //!!! if we have exhausted all 10 nums in this cell means this "path" to here is totally wrong so we need return false.
                    return false;
                }
            }
        }

        // since there must existing one solution to this sudoku, so after we traversed all the matrix we return true
        return true;
    }
}
