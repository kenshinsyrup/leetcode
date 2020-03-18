package com.myleetcode.bad_problems.bulb_switcher;

public class Solution {
    public int bulbSwitch(int n) {
        // return bulbSwitchBySimulate(n);MLE
        return bulbSwitchByMath(n);
    }

    /*
    Simulate

    MLE.
    */
    private int bulbSwitchBySimulate(int n) {
        if (n <= 0) {
            return 0;
        }

        int[] bulbs = new int[n];
        int step = 1;
        // Round 1, idx 0, turn on all
        for (int i = 0; i < n; i++) {
            bulbs[i] = 1;
        }
        step++;

        // Round x, idx x-1, toggle every x
        while (step < n) {
            for (int i = step - 1; i < n; i += step) {
                bulbs[i] = 1 - bulbs[i];
            }

            step++;
        }

        // Because first round is turn on all bulbs, so here need check whether n>1.
        if (n > 1) {
            // Round nth, idx n-1, toggle the last bulb.
            bulbs[n - 1] = 1 - bulbs[n - 1];
        }

        int count = 0;
        for (int i = 0; i < n; i++) {
            if (bulbs[i] == 1) {
                count++;
            }
        }

        return count;
    }

    /**
     * Stupid problem.
     * https://leetcode.com/problems/bulb-switcher/discuss/77104/Math-solution..
     * https://leetcode.com/problems/bulb-switcher/discuss/77132/The-simplest-and-most-efficient-solution-well-explained
     * https://leetcode.com/problems/bulb-switcher/discuss/77144/One-line-java-with-explanation
     */
    private int bulbSwitchByMath(int n) {
        return (int) Math.sqrt(n);
    }
}

