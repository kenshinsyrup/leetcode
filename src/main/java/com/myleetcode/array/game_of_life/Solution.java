package com.myleetcode.array.game_of_life;

class Solution {
    public void gameOfLife(int[][] board) {
        gameOfLifeByTraverse(board);
    }

    // for the Follow Ups: solution section
    // https://leetcode.com/problems/game-of-life/discuss/73217/Infinite-board-solution
    // https://leetcode.com/problems/game-of-life/discuss/73286/How-to-answer-these-frequent-interview-follow-up-question-for-game-of-life

    // TC: O(R*C)
    // SC: O(1)
    // intuition: the simultaneously means we should use a prevState board to generate nextState board
    // we could first make a copy of the board, then we change the board but when we need to check cell's neighbors to apply the rules, we should check the boardCopy, not the board we are mutating.
    // This will cost SC: O(R*C), but since we only want to check the state of original board, we could use a trick here, since cell only has 1 or 0 state, so if a cell changes from 0 to 1, we mark it with 2, if a cell changes from 1 to 0, we mark it with -1.
    // after we apply rules on all cells, we change -1 to 0 and change 2 to 1
    private void gameOfLifeByTraverse(int[][] board){
        if(board == null || board.length == 0 || board[0] == null || board[0].length == 0){
            return;
        }

        int rowLen = board.length;
        int colLen = board[0].length;
        int[][] direcs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {-1, -1}, {1, 1}, {-1, 1}, {1, -1}};// 8 neighbors

        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){

                // 1 get liveNeighbors #
                int liveNeighbors = 0;
                for(int[] direc: direcs){
                    int rowIdx = i + direc[0];
                    int colIdx = j + direc[1];
                    if(rowIdx < 0 || rowIdx >= rowLen || colIdx < 0 || colIdx >= colLen){
                        continue;
                    }

                    // original live neighbors
                    if(board[rowIdx][colIdx] == 1 || board[rowIdx][colIdx] == -1){
                        liveNeighbors++;
                    }
                }

                // 2 apply rules: rule2 is implicit
                // rule1 or rule3
                if((board[i][j] == 1) && (liveNeighbors < 2 || liveNeighbors > 3)){
                    board[i][j] = -1; // cell now dead but originally live
                }
                // rule4
                if(board[i][j] == 0 && liveNeighbors == 3){
                    board[i][j] = 2; // cell now live but originally dead
                }
            }
        }

        // get the final board
        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                if(board[i][j] == 2){
                    board[i][j] = 1;
                }else if(board[i][j] == -1){
                    board[i][j] = 0;
                }
            }
        }
    }
}
