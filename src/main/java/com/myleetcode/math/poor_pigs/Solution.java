package com.myleetcode.math.poor_pigs;

public class Solution {
    public int poorPigs(int buckets, int minutesToDie, int minutesToTest) {
        return poorPigsByMath(buckets, minutesToDie, minutesToTest);
    }

    /*
    Bad Problem.
    Formulation, check it with the Solution explanation:
    https://leetcode.com/problems/poor-pigs/discuss/94278/Very-Clear-Explanation-by-Short-Examples
    */
    private int poorPigsByMath(int buckets, int minutesToDie, int minutesToTest) {
        if (buckets <= 0 || minutesToTest <= 0) {
            return 0;
        }

        int pigNum = 0;
        while (Math.pow(minutesToTest / minutesToDie + 1, pigNum) < buckets) {
            pigNum++;
        }

        return pigNum;
    }
}
