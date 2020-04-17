package com.myleetcode.random.random_pick_with_blacklist;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Solution {

    /*
    Map blacklist index to not-black
    https://leetcode.com/problems/random-pick-with-blacklist/discuss/144624/Java-O(B)-O(1)-HashMap
    https://leetcode.com/problems/random-pick-with-blacklist/discuss/146533/Super-Simple-Python-AC-w-Remapping

    */

    Map<Integer, Integer> blackNotMap;
    Random rand;
    int validLen;

    public Solution(int N, int[] blacklist) {
        this.rand = new Random();
        this.blackNotMap = new HashMap<>();

        for (int blackIdx : blacklist) {
            this.blackNotMap.put(blackIdx, -1);
        }

        this.validLen = N - this.blackNotMap.size();

        int idx = N - 1;
        for (int blackIdx : blacklist) {
            if (blackIdx < this.validLen) { // need re-mapping
                // Find the first not blacklist idx from backward.
                while (this.blackNotMap.containsKey(idx)) {
                    idx--;
                }
                // Re-map the blackIdx in range [0:validLen-1] to the not blacklist idx out of this range.
                this.blackNotMap.put(blackIdx, idx);
                idx--;
            }
        }
    }

    public int pick() {
        int idx = this.rand.nextInt(this.validLen);

        if (blackNotMap.containsKey(idx)) {
            return blackNotMap.get(idx);
        }
        return idx;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(N, blacklist);
 * int param_1 = obj.pick();
 */
