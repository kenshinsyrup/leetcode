package com.myleetcode.array.flipping_an_image;

class Solution {
    public int[][] flipAndInvertImage(int[][] A) {
        
        // special case
        if(A == null || A.length == 0 || A[0] == null || A[0].length == 0){
            return A;
        }
        
        // 按照题目，翻译出代码
        int temp = 0;
        for(int i = 0; i < A.length; i++){
            int left = 0;
            int right = A[0].length - 1;
      
            while(left <= right){
                // flip
                temp = A[i][left];
                A[i][left] = A[i][right];
                A[i][right] = temp;
                
                // invert。重要！！！这里要考虑的是left和right相等时，不要重复的被1减两次。一个位置的数字，只invert一次。
                if(left < right){
                    A[i][left] = 1 - A[i][left];
                    A[i][right] = 1 - A[i][right];
                }else{
                    A[i][left] = 1 - A[i][left];
                }
                
                // move
                left++;
                right--;
            }
        }
        
        return A;
        
    }
    
}