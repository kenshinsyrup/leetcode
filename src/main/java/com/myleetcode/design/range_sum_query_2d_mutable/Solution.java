package com.myleetcode.design.range_sum_query_2d_mutable;

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

        // build the BITree
        for(int i = 0; i < rowLen; i++){
            for(int j = 0; j < colLen; j++){
                update(i, j, matrix[i][j]);
            }
        }
    }

    // TC: O(log(R * C))
    public void update(int arrRowIdx, int arrColIdx, int val) {
        int rowLen = this.matrix.length;
        int colLen = this.matrix[0].length;

        // 1 get the diff to update the BITree
        int diff = val - this.matrix[arrRowIdx][arrColIdx];

        // 2 update the matrix
        this.matrix[arrRowIdx][arrColIdx] = val;

        // 3 update the BIT
        for(int nodeRowIdx = arrRowIdx + 1; nodeRowIdx <= rowLen; nodeRowIdx += nodeRowIdx & (-nodeRowIdx)){
            for(int nodeColIdx = arrColIdx + 1; nodeColIdx <= colLen; nodeColIdx += nodeColIdx & (-nodeColIdx)){
                // update BITree
                this.bITree[nodeRowIdx][nodeColIdx] += diff;
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return sum(row2, col2) - sum(row2, col1 - 1) - sum(row1 - 1, col2) + sum(row1 - 1, col1 - 1);
    }

    // TC: O(log(R * C))
    private int sum(int arrRowIdx, int arrColIdx){
        int sum = 0;

        for(int nodeRowIdx = arrRowIdx + 1; nodeRowIdx >= 1; nodeRowIdx -= nodeRowIdx & (-nodeRowIdx)){
            for(int nodeColIdx = arrColIdx + 1; nodeColIdx >= 1; nodeColIdx -= nodeColIdx & (-nodeColIdx)){
                sum += this.bITree[nodeRowIdx][nodeColIdx];
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
