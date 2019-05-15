package com.myleetcode.backtracking.n_queens;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    public List<List<String>> solveNQueens(int n) {
        // return solveNQueensByDFSBacktracking(n); // Wrong
        // return solveNQueensByDFSBacktrackingII(n); // TLE
        return solveNQueensByDFSBacktrackingIII(n);

    }

    /*
    Time complexity : O(N!). There is N possibilities to put the first queen, not more than N (N - 2) to put the second one, not more than N(N - 2)(N - 4) for the third one etc. In total that results in O(N!) time complexity.
Space complexity : O(N^2) to keep an information about board
    */
    // the correct solution: https://leetcode.com/problems/n-queens/discuss/19805/My-easy-understanding-Java-Solution
    public List<List<String>> solveNQueensByDFSBacktrackingIII(int n) {
        List<List<String>> res = new ArrayList<List<String>>();

        if(n <= 0){
            return res;
        }

        // board
        char[][] board = new char[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                board[i][j] = '.';
            }
        }

        // from col 0
        backtrackingIII(board, 0, res);

        return res;
    }

    private void backtrackingIII(char[][] board, int colIndex, List<List<String>> res) {
        if(colIndex == board.length) {
            List<String> snippet = new ArrayList<String>();
            for(int i = 0; i < board.length; i++) {
                snippet.add(new String(board[i]));
            }
            res.add(snippet);
            return;
        }

        // explore rows in cur colIndex
        for(int i = 0; i < board.length; i++) {
            if(validateIII(board, i, colIndex)) {
                board[i][colIndex] = 'Q';

                backtrackingIII(board, colIndex + 1, res);

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


    // TLE
    // to correct it, we'd better put queen into the board actually, then if board is valid, we convert it to List<String> snippet then add to ret. means we need to buidl a board
    private List<List<String>> solveNQueensByDFSBacktrackingII(int n){
        if(n <= 0){
            return new ArrayList<>();
        }

        // init board
        String[][] board = new String[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                board[i][j] = ".";
            }
        }

        boolean[][] used = new boolean[n][n];

        // use Set because every time we are trying to every cell, so there will be duplicates
        Set<List<String>> retSet = new HashSet<>();

        backtrackingII(board, n, used, retSet);

        return new ArrayList<>(retSet);
    }

    private void backtrackingII(String[][] board, int num, boolean[][] used, Set<List<String>> retSet){
        // done with n valid queens
        if(num == 0){
            List<String> snippet = new ArrayList<>();
            for(int i = 0; i < board.length; i++){
                StringBuilder sb = new StringBuilder();

                for(int j = 0; j < board[0].length; j++){
                    sb.append(board[i][j]);
                }

                snippet.add(sb.toString());
            }
            retSet.add(snippet);
            return;
        }

        int rowLen = board.length;
        int colLen = board[0].length;

        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                // expore next queen cell
                // !!! vadiate cell, if row i is used, or col j is used, or cell(i,j) are in the diagonal or anti-diagonal line of cell(rowIdx, colIdx), not valid.
                if(validateII(rowLen, colLen, i, j, used)){
                    // mark and put
                    used[i][j] = true;
                    board[i][j] = "Q";

                    backtrackingII(board, num - 1, used, retSet);

                    // unmark and remove
                    used[i][j] = false;
                    board[i][j] = ".";
                }
            }
        }
    }

    private boolean validateII(int rowLen, int colLen, int rowIdx, int colIdx, boolean[][] used){
        // row or col
        // diagonal or anti-diagonal
        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                if(used[rowIdx][colIdx]){
                    return false;
                }

                if(used[rowIdx][j] || used[i][colIdx]){
                    return false;
                }

                if(used[i][j]){
                    if(((i + j) == (rowIdx + colIdx)) || ((i + colIdx) == (rowIdx + j))){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    // WRONG: This is wrong, alghouth it looks correct.
    // The reason why it's wrong is: every time we try one cell, then try next valid cell, but in backtracking func we are make i from 0 and j from 0 which is must, because we need to try every cell every time, but, since we do so, we have many chance make mistake. for eg, we have already put queen in (0,1) and (1,3), then we backtracking to explore next, we will find (2,0) is correct, after wo done (2,0), we try (2,1),(2,2),(2,3) then we try(3,0),(3,1),(3,2),(3,3), but here's the mistake, when find the (3,2), we find it's valid, so we put it, alghouth we put at the (3,2) in board, our snippet have recored it at the 2th index, which is actually wrong. So the mistake is the corresponding relation between the put and the snippet.
    // intuition: this is a All Combination Problem, we solve this type problem with DFS Backtracking
    // implement: we have a board[n][n] and we should place n quenes, we put a quene at a cell if it's valid, we have a usedRow and usedCol to check this, then we put another quene and repeat, if we put all n quenes, then we record this combination
    private List<List<String>> solveNQueensByDFSBacktracking(int n){
        List<List<String>> ret = new ArrayList<>();

        if(n <= 0){
            return ret;
        }

        boolean[][] used = new boolean[n][n];
        List<String> snippet = new ArrayList<>();

        backtracking(n, used, snippet, ret);

        return ret;
    }

    private void backtracking(int n, boolean[][] used, List<String> snippet, List<List<String>> ret){
        // done with n valid queens
        if(snippet.size() == n){
            ret.add(new ArrayList<>(snippet));
            return;
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                // expore next queen cell
                // !!! vadiate cell, if row i is used, or col j is used, or cell(i,j) are in the diagonal or anti-diagonal line of cell(rowIdx, colIdx), not valid.
                if(validate(n, i, j, used)){
                    // valid to place a queen at (rowIdx, colIdx)
                    // build the row str represents we put the queen here
                    StringBuilder sb = new StringBuilder();
                    for(int k = 0; k < n; k++){
                        if(k != j){
                            sb.append(".");
                        }else{
                            sb.append("Q");
                        }
                    }

                    // mark
                    used[i][j] = true;
                    snippet.add(sb.toString());

                    backtracking(n, used, snippet, ret);

                    // unmark this row and col after done
                    used[i][j] = false;
                    snippet.remove(snippet.size() - 1);
                }
            }
        }
    }

    private boolean validate(int n, int rowIdx, int colIdx, boolean[][] used){
        // row or col
        // diagonal or anti-diagonal
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(used[rowIdx][colIdx]){
                    return false;
                }

                if(used[rowIdx][j] || used[i][colIdx]){
                    return false;
                }

                if(used[i][j]){
                    if(((i + j) == (rowIdx + colIdx)) || ((i + colIdx) == (rowIdx + j))){
                        return false;
                    }
                }
            }
        }

        return true;
    }

}

