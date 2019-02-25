package com.myleetcode.math.valid_tic_tac_toe_state;

public class Solution {
    public boolean validTicTacToe(String[] board) {
        // https://leetcode.com/problems/valid-tic-tac-toe-state/discuss/178806/Java-Solution
        // first player always use X
        // cout X with +1 and count O with -1. Then, when we get a +3 or -3 we know we get a line. And, this also can tell us if the steps are wrong.

        // we have 4 'lines' to fill: row, cololum, diagonals and anti-diagonals. For row and cololum, we have 3 rows and 3 cololums in this board. For diagonal and anti-diagonal, we have 1 for each in this board.

        int len = board.length;
        int[] rows = new int[len];
        int[] cols = new int[len];
        int diagonal = 0;
        int antiDiagonal = 0;

        // and, we should consider if the board is legal. we use a variable steps to count players' move. X we +1 and O we -1. so, because X is the first to move, so: 1 if X win, then (winX)&&(steps==1); 2 if O win, then(winO)&&(steps==0); 3 if no one win, then (steps<=1)&&(!winX)&&(!winO)
        int steps = 0;
        boolean winX = false;
        boolean winO = false;

        for(int i = 0; i < len; i++){
            char[] chars = board[i].toCharArray();
            for(int j = 0; j < chars.length; j++){
                if(chars[j] == 'X'){
                    // 注意！重复的格子，要把格子所属于的行列斜线及反斜线都记录
                    // rows and cols
                    steps++;
                    rows[i]++;
                    cols[j]++;

                    // 如果也是diagonal，那么记录
                    if(i == j){
                        diagonal++;
                    }

                    // 如果也是anti-diagonal，那么记录
                    if(i + j == len - 1){
                        antiDiagonal++;
                    }
                }else if(chars[j] == 'O'){
                    // rows and cols
                    steps--;
                    rows[i]--;
                    cols[j]--;

                    // 如果也是diagonal，那么记录
                    if(i == j){
                        diagonal--;
                    }

                    // 如果也是anti-diagonal，那么记录
                    if(i + j == len - 1){
                        antiDiagonal--;
                    }
                }
            }
        }

        // check if someone wins
        // X win by row
        for(int i = 0; i < rows.length; i++){
            if(rows[i] == len){
                winX = true;
            }
        }
        // X win by col
        for(int i = 0; i < cols.length; i++){
            if(cols[i] == len){
                winX = true;
            }
        }
        // X win by diagonal
        if(diagonal == len){
            winX = true;
        }
        // X win by anti-diagonal
        if(antiDiagonal == len){
            winX = true;
        }

        // O win by row
        for(int i = 0; i < rows.length; i++){
            if(rows[i] == -len){
                winO = true;
            }
        }
        // O win by col
        for(int i = 0; i < cols.length; i++){
            if(cols[i] == -len){
                winO = true;
            }
        }
        // O win by diagonal
        if(diagonal == -len){
            winO = true;
        }
        // O win by anti-diagonal
        if(antiDiagonal == -len){
            winO = true;
        }

        // final check
        if((winX && steps == 1 && !winO) || // X win and board is legal
                (winO && steps == 0 && !winX) || // O win and board is legal
                (!winX && !winO && (steps == 0 || steps == 1))){ // noone win and board is legal
            return true;
        }

        return false;
    }
}