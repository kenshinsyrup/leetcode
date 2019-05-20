package com.myleetcode.math.self_crossing;

class Solution {
    public boolean isSelfCrossing(int[] x) {
        return isSelfCrossingByCheck(x);
    }

    // intuition: tricky problem
    // solution and explaination
    // https://leetcode.com/problems/self-crossing/discuss/79131/Java-Oms-with-explanation
    // https://leetcode.com/problems/self-crossing/discuss/79131/Java-Oms-with-explanation/195274
    /*
    we only need to consider how could we reach the tail of the line, ie the 1st line.
Categorize the self-crossing scenarios, there are 3 of them:
// 1. Fourth line crosses first line and works for fifth line crosses second line and so on...
// 2. Fifth line meets first line and works for the lines after
// 3. Sixth line crosses first line and works for the lines after
    */
    private boolean isSelfCrossingByCheck(int[] x){
        if(x == null || x.length <= 3){
            return false;
        }

        for(int i = 3; i < x.length; i++){
            // !!! line is 1based
            // the 4th line: if 3th <= 1th && 4th >= 2th, crossing
            if(i >= 3 && x[i - 1] <= x[i - 3] && x[i] >= x[i - 2]){
                return true;
            }

            // the 5th line: if 4th==2th && 5th+1th>=3th, crossing
            if(i >= 4 && x[i - 1] == x[i - 3] && x[i] + x[i - 4] >= x[i-2]){
                return true;
            }

            // 6th line: if 4th >= 2th && 5th<=3th && 5th+1th >= 3th && 4th-6th<=2th
            if(i >= 5 && x[i - 2] >= x[i - 4] && x[i - 1] <= x[i - 3] && x[i - 1] + x[i - 5] >= x[i - 3] && x[i - 2] - x[i] <= x[i - 4]){
                return true;
            }
        }

        return false;

    }

}
