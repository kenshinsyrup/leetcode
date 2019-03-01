package com.myleetcode.array.rotate_image;

class Solution {
    public void rotate(int[][] matrix) {
        // 采用屏幕坐标系
        // 思路肯定是layer by layer，就看你写不写的出来
        // n*n, 第一圈
        // point i in [(0, 0), (n-2, 0)] -> [(n-1,0), (n-1, n-2)]
        // point i in [(n-1,0), (n-1, n-2)] -> [(n-1,n-1), (1,n-1)]
        // point i in[(n-1,n-1), (1, n-1)] -> [(0, n-1), (0, 1)]
        // point i in [(0, n-1), (0, 1)] -> [(0,0), (n-2, 0)]

        // n-1*n-1圈...
        // 直到最后一圈,偶数中间有四个格子右上角格子坐标为(n/2,n/2)，奇数中间有一个格子坐标(n/2,n/2).所以循环的次数也就是‘圈数’就是n/2

        // 遍历一边就可以解出，并且是不需要额外空间

        // special case
        if(matrix == null || matrix.length == 1){
            return;
        }

        rotateByInplace(matrix);
    }

    private void rotateByInplace(int[][] matrix){
        int len = matrix.length;

        // 这个题就是，思路你肯定有，但很难写出来
        // 1、层数，这个比较简单，数数就算出来了，肯定是len/2
        // 2、j的范围(看最上面的转换方式列出来的坐标)，在最外层的时候肯定是0到len-1-1,因为我们旋转到时候，我们每操作完一层,去掉一层，所以j的范围被i控制，就是i到len-1-i
        // 3、再接下来重要的就是，如何确定四个方向上的image的坐标，这个最重要，也最麻烦
        // 圈
        for(int i = 0; i < len / 2; i++){
            // 点
            for(int j = i; j < len - 1 - i; j++){
                // figure out points
                int up = matrix[i][j];// 上面，左到右
                int right = matrix[j][len - 1 - i];//右边，上到下
                int bottom = matrix[len - 1 - i][len - 1 - j];//下面，右到左
                int left = matrix[len - 1 - j][i];//左边，下到上

                // exchange
                matrix[j][len - 1 - i] = up;
                matrix[len - 1 - i][len - 1 - j] =  right;
                matrix[len - 1 - j][i] = bottom;
                matrix[i][j] = left;

                // 逆时针了
                // matrix[i][j] = right;
                // matrix[j][len - 1 - i] = bottom;
                // matrix[len - 1 - i][len - 1 - j] = left;
                // matrix[len - 1 - j][i] = up;
            }
        }
    }
}
