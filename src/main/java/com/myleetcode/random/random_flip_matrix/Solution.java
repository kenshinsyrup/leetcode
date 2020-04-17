package com.myleetcode.random.random_flip_matrix;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Solution {

    /*
    https://leetcode.com/problems/random-flip-matrix/discuss/375240/Well-Explained-Python-with-example-call-random-once-every-time
    https://leetcode.com/problems/random-flip-matrix/discuss/155341/Easy-Algorithm-Explanation-Step-By-Step

Description
We will be using modern method of the Fisher–Yates shuffle.
Please read how the original algorithm works: https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#Examples.

Overview:
generate random from 0 to n : m
swap m and n
decrease n

    */
    private Map<Integer, Integer> map;
    private int rowLen;
    private int colLen;
    private int remain;
    private Random rand;

    public Solution(int n_rows, int n_cols) {
        this.rowLen = n_rows;
        this.colLen = n_cols;
        this.rand = new Random();
        this.map = new HashMap<>();
        this.remain = rowLen * colLen;
    }

    public int[] flip() {

        // generate index, decrease remain number of values
        // a random index from [0, self.remain-1] is valid range
        int idx = rand.nextInt(remain);
        remain--;

        // if there was a swap operation already happened at this index before, find the exact index should be operated this time.
        int realIdx = idx;
        if (map.containsKey(idx)) {
            realIdx = map.get(idx);
        }

        // if there is a swap operation happened at this tail index, find the exact index
        int tail = remain;
        if (map.containsKey(remain)) {
            tail = map.get(remain);
        }

        // swap this index with actual tail index
        map.put(idx, tail);

        return new int[]{realIdx / colLen, realIdx % colLen};
    }

    public void reset() {
        map = new HashMap<>();
        remain = rowLen * colLen;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(n_rows, n_cols);
 * int[] param_1 = obj.flip();
 * obj.reset();
 */
