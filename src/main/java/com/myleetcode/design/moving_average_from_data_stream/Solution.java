package com.myleetcode.design.moving_average_from_data_stream;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    class MovingAverage {

        // https://leetcode.com/problems/moving-average-from-data-stream/discuss/81505/Java-easy-to-understand-solution
        // we could optimize this: 1, we could remember the sum so we dont need to traverse all our vals to caculate sum, just use sum - the first val + current val.
        // then, we dont use List, because List remove(0) cost O(N) time, we could use a queue wihch give us O(1) time to remove first elem
        Deque<Integer> numQueue;
        Integer sum;
        Integer size;
        /** Initialize your data structure here. */
        public MovingAverage(int size) {
            this.numQueue = new ArrayDeque<>();
            this.sum = 0;
            this.size = size;
        }

        // TC: O(1)
        // SC: O(Size)
        public double next(int val) {
            numQueue.offer(val);
            if(numQueue.size() > size){
                sum -= numQueue.poll();
            }

            sum += val;

            return (double)sum  / numQueue.size();
        }

        // List to store  vals, if List larger than Size, remove first
        // use List size as dominent
//     List<Integer> numList;
//     int size;

//     /** Initialize your data structure here. */
//     public MovingAverage(int size) {
//         this.numList = new ArrayList<>();
//         this.size = size;
//     }

//     // TC: O(Size)
//     // SC: O(Size)
//     public double next(int val) {
//         numList.add(val);
//         if(numList.size() > size){
//             numList.remove(0);
//         }

//         int sum = 0;
//         for(int v: numList){
//             sum += v;
//         }

//         return sum * 1.0 / numList.size();
//     }
    }

/**
 * Your MovingAverage object will be instantiated and called as such:
 * MovingAverage obj = new MovingAverage(size);
 * double param_1 = obj.next(val);
 */
}
