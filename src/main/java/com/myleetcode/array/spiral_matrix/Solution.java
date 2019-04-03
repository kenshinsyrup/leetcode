package com.myleetcode.array.spiral_matrix;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        return spiralOrderByTraverse(matrix);
    }

    // TC: O(N), N is the number of total number in matrix, because we traverse every number once
    // SC: O(1)
    // 这个题目的意思：是让从左上角开始画螺旋线的方式输出所有数字，对于eg1就是从1开始画螺旋线向右123向下69向左87向上4向右5结束
    // https://leetcode.com/problems/spiral-matrix/discuss/20599/Super-Simple-and-Easy-to-Understand-Solution
    private List<Integer> spiralOrderByTraverse(int[][] matrix){
        List<Integer> ret = new ArrayList<>();

        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            return ret;
        }

        int rowBegin = 0;
        int rowEnd = matrix.length - 1;
        int colBegin = 0;
        int colEnd = matrix[0].length - 1;

        // 对于这种while检查合法性的，在while内部要注意如果对于变量做了操作，要小心，多想一下是不是应该重新检查合法性
        while(rowBegin <= rowEnd && colBegin <= colEnd){
            // to right
            for(int i = colBegin; i<= colEnd; i++){
                ret.add(matrix[rowBegin][i]);
            }
            rowBegin++; // 第一行数字用完了

            // 由于有变量做了改动，再次检查合法性
            if(rowBegin <= rowEnd && colBegin <= colEnd){
                // to down
                for(int i = rowBegin; i <= rowEnd; i++){
                    ret.add(matrix[i][colEnd]);
                }
                colEnd--;// 最后一列数字用完了
            }

            if(rowBegin <= rowEnd && colBegin <= colEnd){
                // to left
                for(int i = colEnd; i >= colBegin; i--){
                    ret.add(matrix[rowEnd][i]);
                }
                rowEnd--; // 最后一行数字用完了
            }

            if(rowBegin <= rowEnd && colBegin <= colEnd){
                // to up
                for(int i = rowEnd; i >= rowBegin; i--){
                    ret.add(matrix[i][colBegin]);
                }
                colBegin++; //第一列数字用完了
            }
        }

        return ret;
    }
}
