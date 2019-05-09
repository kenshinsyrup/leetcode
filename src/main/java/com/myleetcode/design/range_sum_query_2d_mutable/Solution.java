package com.myleetcode.design.range_sum_query_2d_mutable;

public class Solution {
    class NumMatrix {

        // Binary Indexed Tree: much better than Segment Tree to solve Range Query Problem
        // https://en.wikipedia.org/wiki/Fenwick_tree
        // https://www.hackerearth.com/zh/practice/data-structures/advanced-data-structures/fenwick-binary-indexed-trees/tutorial/
        // https://www.geeksforgeeks.org/binary-indexed-tree-or-fenwick-tree-2/

        // foundational concetp: how to represent a negative int to binary form?(normally 2's compliment)

        // solution: https://leetcode.com/problems/range-sum-query-2d-mutable/discuss/75917/Share-my-Java-2-D-Binary-Indexed-Tree-Solution

        int[][] matrix;
        int[][] bITree;

        // TC: O((R * C) * log(R * C))
        // SC: O(R * C)
        public NumMatrix(int[][] matrix) {
            if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
                return;
            }

            int rowLen = matrix.length;
            int colLen = matrix[0].length;

            this.matrix = new int[rowLen][colLen];
            this.bITree = new int[rowLen + 1][colLen + 1]; // !!! for convience to implement, we should add one more row and col

            // init
            for(int i = 0; i < rowLen; i++){
                for(int j = 0; j < colLen; j++){
                    update(i, j, matrix[i][j]);
                }
            }
        }

        // TC: O(log(R * C))
        public void update(int row, int col, int val) {
            int rowLen = this.matrix.length;
            int colLen = this.matrix[0].length;

            // get the delta of after update
            int delta = val - this.matrix[row][col];

            // update the matrix
            this.matrix[row][col] = val;

            // update the tree with the delta(diff), update the needed pos(row+1, col+1) and its children
            // !!! be aware of the one more row and col in BIT
            for(int i = row + 1; i <= rowLen; i += i & (-i)){
                for(int j = col + 1; j <= colLen; j += j & (-j)){
                    this.bITree[i][j] += delta;
                }
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            return sum(row2, col2) - sum(row2, col1 - 1) - sum(row1 - 1, col2) + sum(row1 - 1, col1 - 1);
        }

        // TC: O(log(R * C))
        // sum() returns the sum of leftcornor (0,0) to right cornor (row,col) elements in given array
        private int sum(int row, int col){
            int sum = 0;
            // lookup the given node and all ancestors value
            // !!! be aware of the one more row and col in BIT
            for(int i = row + 1; i > 0; i -= i & (-i)){
                for(int j = col + 1; j > 0; j -= j & (-j)){
                    sum += this.bITree[i][j];
                }
            }
            return sum;
        }
    }

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * obj.update(row,col,val);
 * int param_2 = obj.sumRegion(row1,col1,row2,col2);
 */
}
