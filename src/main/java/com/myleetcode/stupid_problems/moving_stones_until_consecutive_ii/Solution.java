package com.myleetcode.stupid_problems.moving_stones_until_consecutive_ii;

import java.util.Arrays;

class Solution {
    public int[] numMovesStonesII(int[] stones) {
        return numMovesStonesIIByBrainTeaser(stones);
    }

    // 沙雕题目:
    // https://leetcode.com/problems/moving-stones-until-consecutive-ii/discuss/286707/JavaC%2B%2BPython-Sliding-Window
    // https://leetcode.com/problems/moving-stones-until-consecutive-ii/discuss/286886/No-code-just-chinese-explanation
    private int[] numMovesStonesIIByBrainTeaser(int[] stones){
        if(stones == null || stones.length == 0){
            return new int[]{0, 0};
        }

        // sort first
        Arrays.sort(stones);

        int len = stones.length;
        // if we move rightMost to the left of second stone from right, then remains spaces are: stones[len - 2] - stones[0] - 1 -(n - 3)
        // if we move leftMost to the right of second stone from left, then remains spaces are: stones[len - 1] - stones[1] - 1 - (n - 3)
        // the larger one is the max we need
        int maxMove = Math.max(stones[len - 2] - stones[0] - 1 - (len - 3), stones[len - 1] - stones[1] - 1 - (len - 3));

        int leftP = 0;
        int rightP = 0;
        int minMove = Integer.MAX_VALUE;
        while(rightP < len){
            while(stones[rightP] - stones[leftP] >= len){
                leftP++;
            }

            if(rightP - leftP + 1 == len - 1 && stones[rightP] - stones[leftP] == len -1 - 1){
                minMove = Math.min(minMove, 2);

            }else{
                minMove = Math.min(minMove, len - (rightP - leftP + 1));
            }

            rightP++;
        }

        return new int[]{minMove, maxMove};

    }
}
