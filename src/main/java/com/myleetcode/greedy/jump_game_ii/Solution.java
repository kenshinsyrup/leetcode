package com.myleetcode.greedy.jump_game_ii;

class Solution {
    // 55. Jump Game

    public int jump(int[] nums) {
        return jumpByGreedy(nums);
    }

    // TC: O(N)
    // SC: O(1)
    // You can assume that you can always reach the last index.
    // https://leetcode.com/problems/jump-game-ii/discuss/280568/Java-100-Solution-Explained
    // https://leetcode.com/problems/jump-game-ii/discuss/18014/Concise-O(n)-one-loop-JAVA-solution-based-on-Greedy
    // The main idea is based on greedy. Let's say the Range of the Current Jump is [curBegin, curEnd], curFarthest is the farthest point that all points in [curBegin, curEnd] can reach.
    // Once the Current Point i reaches curEnd, then trigger another jump, so we shouldset the new curEnd with curFarthest, then keep the above steps, as the following:
    private int jumpByGreedy(int[] nums){
        if(nums == null || nums.length <= 1){
            return 0;
        }

        int jump = 0;
        int curEnd = 0;
        int curFarthest = 0;

        // !!! here we only need i < len-1, not len. Because we are caculate the jump step we need, so we only try the pos i that we MAY NEED JUMP, obviously if i is len-1 we dont need jump
        for(int i = 0; i < nums.length - 1; i++){
            curFarthest = Math.max(curFarthest, i + nums[i]); // at point i, we could reach the range is i+A[i]

            if(i == curEnd){// if cur point i, reach the farhtest range of current jump, ie the curEnd, so we have to make another jump here, so we need update the curEnd with the curFarthest which means the farthest range we could reach from cur pos
                jump++; // have to do a jump

                curEnd = curFarthest;// update a new end for this new jump
            }
        }

        return jump;
    }
}
