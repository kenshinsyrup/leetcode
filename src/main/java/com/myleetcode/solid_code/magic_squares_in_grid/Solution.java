package com.myleetcode.solid_code.magic_squares_in_grid;

import java.util.HashSet;
import java.util.Set;

class Solution {
    public int numMagicSquaresInside(int[][] grid) {
        return numMagicSquaresInsideByTraversal(grid);
    }

    // intuition:
    // naive sol.
    // 1. loop every 3*3 sub-grid, cost O(R*C)
    // 2. for every sub-grid, we check if its magic, cost O(3*3). if is, count
    // totally cost O(R*C)
    private int numMagicSquaresInsideByTraversal(int[][] grid){
        if(grid == null || grid.length < 3 || grid[0] == null || grid[0].length < 3){
            return 0;
        }

        int rowLen = grid.length;
        int colLen = grid[0].length;
        int count = 0;
        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                if(isMagic(grid, i, j)){
                    count++;
                }
            }
        }

        return count;
    }

    // TC: O(3*3)
    private boolean isMagic(int[][] grid, int rowIdx, int colIdx){
        int rowLen = grid.length;
        int colLen = grid[0].length;

        if(rowIdx + 3 > rowLen || colIdx + 3 > colLen){
            return false;
        }

        Set<Integer> numSet = new HashSet<>();
        int sum = grid[rowIdx][colIdx] + grid[rowIdx][colIdx + 1] + grid[rowIdx][colIdx + 2];
        // check row sum
        for(int i = rowIdx; i < rowIdx + 3; i++){
            int rowSum = 0;
            for(int j = colIdx; j < colIdx + 3; j++){
                // only 1-9
                if(grid[i][j] < 1 || grid[i][j] > 9){
                    return false;
                }
                // distinct
                if(numSet.contains(grid[i][j])){
                    return false;
                }
                numSet.add(grid[i][j]);

                rowSum += grid[i][j];
            }

            if(rowSum != sum){
                return false;
            }
        }
        // check col sum
        for(int i = colIdx; i < colIdx + 3; i++){
            int colSum = 0;
            for(int j = rowIdx; j < rowIdx + 3; j++){
                colSum += grid[j][i];
            }

            if(colSum != sum){
                return false;
            }
        }
        // check diagonal
        int leftDiaSum = 0;
        int rightDiaSum = 0;
        for(int i = 0; i < 3; i++){
            leftDiaSum += grid[rowIdx + i][colIdx + i];
            rightDiaSum += grid[rowIdx + i][colIdx + 2 - i];
        }
        if(leftDiaSum != sum || rightDiaSum != sum){
            return false;
        }

        return true;
    }
}
