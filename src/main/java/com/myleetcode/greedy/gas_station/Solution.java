package com.myleetcode.greedy.gas_station;

class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        return canCompleteCircuitByGreedy(gas, cost);
    }

    // TC: O(N)
    // SC: O(1)
    private int canCompleteCircuitByGreedy(int[] gas, int[] cost){
        if(gas == null || gas.length == 0 || cost == null || cost.length == 0 || gas.length != cost.length){
            return -1;
        }

        int gasLen = gas.length;
        int start = 0;
        int totalBalance = 0;
        int curBalance = 0;
        for(int i = 0; i < gasLen; i++){
            // gas[i] - cost[i] is current station gas and current road's balance

            totalBalance += gas[i] - cost[i]; // total balance, this is an for convience code, has no business with the greedy logic, actually we could use another for loop before theis for loop, to do a traverse to caculate the totalBalance then if < 0 return -1. Write here just because we could save that additional for loop

            curBalance += gas[i] - cost[i]; // greedy: current start station's balance
            // greedy, given a..b..c..d, if a is tart, then a could reach b, then b is current i, then if b could not reach c, the start(a) could not reach i+1(c), and we know if a could not reach c then any node between a and c could not reach c, so we reset the start to i+1(c), anc reset the curBalance = 0
            if(curBalance < 0){
                curBalance = 0;
                start = i + 1;
            }
        }

        if(totalBalance < 0){
            return -1;
        }

        // solution has the proof: Why this works
        return start;
    }
}
