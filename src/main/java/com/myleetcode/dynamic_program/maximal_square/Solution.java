package com.myleetcode.dynamic_program.maximal_square;

class Solution {
    public int maximalSquare(char[][] matrix) {
        return maximalSquareByDP(matrix); // more general, same with 85. Maximal Rectangle
        // return maximalSquareByDPII(matrix);
    }

    // TC: O(R * C)
    // SC: O(R * C)
    // https://leetcode.com/problems/maximal-square/discuss/61802/Extremely-Simple-Java-Solution-:)/63328
    // 最难的地方在于想出recursion公式：
    /*dp[i][j] 代表在以i, j这一格为右下角的正方形边长。
如果这一格的值也是1，那这个正方形的边长就是他的上面，左手边，和斜上的值的最小边长 +1。因为如果有一边短了缺了，都构成不了正方形。所以实际上要求的就是上，左，左上，三个都不是0，因为是0说明这三个dp位置对应的matrix cell是0，那么当前这个[i][j]cell肯定不可能是一个正方形的最右下角，所以肯定dp是0.
在左上，左，上，三个dp都非0的情况下，我们当前这个cell想作为正方形的右下角，就需要在这三个中找到最小的边加入，因为这个cell作为右下角，要使用(i-1,j)和(i,j-1)这两个右下角代表的正方形的各一条边，同时新正方形的左上角cell肯定在(i-1,j-1)代表的正方形内，所以满足条件的正方形的边，必是这三个cell代表的正方形的最小者的边再加上新的这个1
*/
    // https://leetcode.com/problems/maximal-square/discuss/164120/Python-or-DP-tm
    // try to find the longest edge of a square in a given matrix
    // use the dp[i][j] represents the longest edge of square in the given matrix which's left up corner is [0][0] and right bolow corner is [i][j].
    // dp[i][j] = max(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1.
    // find the largest dp[i][j] then its square is the answer
    private int maximalSquareByDPII(char[][] matrix){
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            return 0;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dp = new int[rows][cols];

        // base, the longest edge in the first row and col must be 1 if the matrix is 1
        for(int i = 0; i < rows; i++){
            if(matrix[i][0] == '1'){
                dp[i][0] = 1;
            }
        }
        for(int j = 0; j < cols; j++){
            if(matrix[0][j] == '1'){
                dp[0][j] = 1;
            }
        }

        // dp
        for(int i = 1; i < rows; i++){
            for(int j = 1; j < cols; j++){
                if(matrix[i][j] == '1'){
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                }
            }
        }

        // find the max edge
        int maxEdge = 0;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                maxEdge = Math.max(maxEdge, dp[i][j]);
            }
        }

        return maxEdge * maxEdge;
    }

    // TC: O(R^2 * C)
    // SC: O(R * C)
    // intuition: DP. 85, 221, 304
    // looks very familar with 85, this is a square, 85 is a rectangle
    // we could solve this problem with the solution like 85
    private int maximalSquareByDP(char[][] matrix){
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            return 0;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dp = new int[rows][cols];

        // base
        // dp[0][0]
        if(matrix[0][0] == '1'){
            dp[0][0] = 1;
        }
        // dp[i][0]
        for(int i = 1; i < rows; i++){
            if(matrix[i][0] == '1'){
                dp[i][0] = 1;
            }
        }
        // dp[0][j]
        for(int j = 1; j < cols; j++){
            if(matrix[0][j] == '1'){
                dp[0][j] = dp[0][j - 1] + 1;
            }
        }

        // dp
        for(int i = 1; i < rows; i++){
            for(int j = 1; j < cols; j++){
                if(matrix[i][j] == '1'){
                    dp[i][j] = dp[i][j - 1] + 1;
                }
            }
        }

        // caculate the area of square
        int maxArea = 0;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(matrix[i][j] == '1'){
                    int width = dp[i][j];

                    for(int k = i; k >= 0; k--){
                        width = Math.min(width, dp[k][j]);

                        // check square
                        if(i - k + 1 == width){
                            maxArea = Math.max(maxArea, width * (i - k + 1));
                        }
                    }
                }
            }
        }

        return maxArea;

    }
}
