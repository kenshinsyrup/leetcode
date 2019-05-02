package com.myleetcode.array.spiral_matrix_ii;

class Solution {
    public int[][] generateMatrix(int n) {
        return generateMatrixByCoordinate(n);
    }

    // TC: O(N)
    // SC: O(1) extra space
    // intuition: given a n, so we will have a ret[n][n] 2-d array
    // so we have 1-n^2 num, we need to put them to the 2-d array
    /*left = 0; right = n-1; top = 0, bottom = n-1;
    leftTemp from left move to right, leftTemp <= right, put to (top, leftTemp), top++
    topTemp from top move to bottom, topTemp <= bottom, put to (topTemp, right), right--
    rightTemp from right move to left, rightTemp >= left, put to (bottom, rightTemp), bottom--
    bottomTemp from bottom move to up, bottomTemp >= up, put to (bottomTemp, left), left++*/
    // and because we are update left, right, top, bottom, so we'd better add additional top<=bottom or left<=right  restrictions to every while.
    private int[][] generateMatrixByCoordinate(int n){
        if(n <= 0){
            return new int[0][0];
        }
        if(n == 1){
            return new int[][]{{1}};
        }

        int left = 0;
        int right = n - 1;
        int top = 0;
        int bottom = n - 1;

        int[][] ret = new int[n][n];
        // traverse and put
        int num = 0;
        while(left <= right && top <= bottom){

            // first row, left to right
            int leftTemp = left;
            while(leftTemp <= right && top <= bottom){
                num++;
                ret[top][leftTemp] = num;

                leftTemp++;
            }
            top++;

            // last col, top to right
            int topTemp = top;
            while(topTemp <= bottom && left <= right){
                num++;
                ret[topTemp][right] = num;

                topTemp++;
            }
            right--;

            // last row, right to left
            int rightTemp = right;
            while(rightTemp >= left && top <= bottom){
                num++;
                ret[bottom][rightTemp] = num;

                rightTemp--;
            }
            bottom--;

            // first col, bottom to up
            int bottomTemp = bottom;
            while(bottomTemp >= top && left <= right){
                num++;
                ret[bottomTemp][left] = num;

                bottomTemp--;
            }
            left++;
        }

        return ret;

    }

}
