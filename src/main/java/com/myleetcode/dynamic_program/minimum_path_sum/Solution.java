class Solution {
    public int minPathSum(int[][] grid) {
        return minPathSumByDP(grid);
    }

    // TC: O(R * C)
    // SC: O(R * C)
    // intuition: DP. similar: 62, 63, 64, 120, 174, 931
    // dp[i][j] means the min sum path to here, so dp[i][j] = min(dp[i-1][j], dp[i][j-1])
    // base case, the first row and col are the base case so we no need to add extra row and col in dp array.
    private int minPathSumByDP(int[][] grid){
        if(grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0){
            return 0;
        }

        int rowLen = grid.length;
        int colLen = grid[0].length;

        int[][] dp = new int[rowLen][colLen];

        // base
        for(int i = 0; i < rowLen; i++){
            if(i == 0){
                dp[i][0] = grid[i][0];
            }else{
                dp[i][0] = dp[i - 1][0] + grid[i][0];
            }
        }
        for(int j = 0; j < colLen; j++){
            if(j == 0){
                dp[0][j] = grid[0][j];
            }else{
                dp[0][j] = dp[0][j - 1] + grid[0][j];
            }
        }

        // dp
        for(int i = 1; i < rowLen; i++){
            for(int j = 1; j < colLen; j++){
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        return dp[rowLen - 1][colLen - 1];
    }
}