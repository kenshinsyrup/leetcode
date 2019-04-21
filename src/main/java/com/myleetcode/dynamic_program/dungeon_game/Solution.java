package com.myleetcode.dynamic_program.dungeon_game;

class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        // return calculateMinimumHPByDP(dungeon);// WRONG
        return caculateMinimumHPByDPII(dungeon);
    }

    // TC: O(R * C)
    // SC: O(R * C)
    // solution: https://leetcode.com/problems/dungeon-game/discuss/52805/Best-solution-I-have-found-with-explanations
    // 在这个solution里，作者用1作为了每个cell需要的最少的hp，这样的话，P位置需要max(1, 1 + (-dungeon[iP][jP]))这样，不是很容易明白这个1+(-dungeon)中的1是什么意思，可以换成0来处理如下：
    // https://leetcode.com/problems/dungeon-game/discuss/52826/a-very-clean-and-intuitive-solution-with-explanation
    // 反向dp，dp[i][j] 代表的是当前pos需要的拥有的hp，由于hp==1是基础条件否则K就跪了，所以我们要额外考虑这个
    // 那么由于我们只能知道最后一个位置也就是P应该拥有的hp，所以我们用倒序的方式来计算dp，直到得到dp[0][0]为解
    // 对于一个dungeon来说，其内的值代表我们会消耗多少hp，所以-dungeon代表的就是在这个dungeon我们需要的hp
    // 对于P位置，我们在这个位置至少需要的hp为(-dungeon[iP][jP]), 但是这个值如果小于0那么是不可行的，所以我们在P这个位置需要的hp就是max(0, (-dungeon[iP][jP])).
    // 对于有邻居的其他的dungeon来说，最后一行和最后一列是base，我们可以处理
    /// 其他正常的有两个邻居的dungeon来说，pos(i, j)需要的最少的hp就是其两个邻居的小者和-dungeon[i][j]的和。
    // 最后，因为我们处理的是hp在每个dungeon刚好为0的情况，题目要求的是不能死亡，所以要将hp+1就是需要的最小的hp
    // intuition: DP. similar: 62, 63, 64, 120, 174, 931
    private int caculateMinimumHPByDPII(int[][] dungeon){
        if(dungeon == null || dungeon.length == 0){
            return 0;
        }

        int rowLen = dungeon.length;
        int colLen = dungeon[0].length;

        int[][] dp = new int[rowLen][colLen];
        // init
        for(int i = rowLen - 1; i >= 0; i--){
            if(i == rowLen - 1){
                dp[i][colLen - 1] = Math.max(0, -dungeon[i][colLen - 1]);
            }else{
                dp[i][colLen - 1] = Math.max(0, dp[i + 1][colLen - 1] + (-dungeon[i][colLen - 1]));
            }
        }
        for(int j = colLen - 1; j >= 0; j--){
            if(j == colLen - 1){
                dp[rowLen - 1][j] = Math.max(0, -dungeon[rowLen - 1][j]);
            }else{
                dp[rowLen - 1][j] = Math.max(0, dp[rowLen - 1][j + 1] + (-dungeon[rowLen - 1][j]));
            }
        }

        // dp
        for(int i = rowLen - 1 - 1; i >= 0; i--){
            for(int j = colLen - 1 - 1; j >= 0; j--){
                dp[i][j] = Math.max(0, Math.min(dp[i + 1][j], dp[i][j + 1]) + (-dungeon[i][j]));
            }
        }

        return dp[0][0] + 1;
    }


    // WRONG: this is incorrect because we should not to caculate the min health we need in the dp, because this mkaes no sense. if we have the min health of pos(i,j), how do you know you could reach pos(i+1,j) safely with this health?
    // this problem has one import point: any time should not equal or below 0
    // dp[i][j] means the min health we need to reach pos(i, j), so dp[i][j] =
    /*if dp[i-1][j] + (-dungeon) < 0 && dp[i][j-1] + (-dungeon) < 0, return
    else if dp[i-1][j] + (-dungeon) >= 0, dp[i][j] = it
    else if dp[i][j-1] + (-dungeon) >= 0, dp[i][j] = it
    else dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + (-dungeon[i][j])*/
    // base case is the first row and col because they only could be reached by the row or col before them, so we dont need extra row or col in dp array
    private int calculateMinimumHPByDP(int[][] dungeon){
        if(dungeon == null || dungeon.length == 0){
            return 0;
        }

        int rowLen = dungeon.length;
        int colLen = dungeon[0].length;

        int[][] dp = new int[rowLen][colLen];
        //init
        for(int i = 0; i < rowLen; i++){
            if(i == 0){
                dp[i][0] = -dungeon[i][0];
            }else{
                dp[i][0] = dp[i - 1][0] + (-dungeon[i][0]);
            }
        }
        for(int j = 0; j < colLen; j++){
            if(j == 0){
                dp[0][j] = -dungeon[0][j];
            }else{
                dp[0][j] = dp[0][j - 1] + (-dungeon[0][j]);
            }
        }

        // dp
        for(int i = 1; i < rowLen; i++){
            for(int j = 1; j < colLen; j++){
                if(dp[i-1][j] + (-dungeon[i][j]) < 0 && dp[i][j-1] + (-dungeon[i][j]) < 0){
                    return Integer.MAX_VALUE;
                }else if(dp[i - 1][j] + (-dungeon[i][j]) >= 0){
                    dp[i][j] = dp[i - 1][j] + (-dungeon[i][j]);
                }else if(dp[i][j - 1] + (-dungeon[i][j]) >= 0){
                    dp[i][j] = dp[i][j - 1] + (-dungeon[i][j]);
                }else{
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + (-dungeon[i][j]);
                }
            }
        }

        return dp[rowLen - 1][colLen - 1];

    }
}
