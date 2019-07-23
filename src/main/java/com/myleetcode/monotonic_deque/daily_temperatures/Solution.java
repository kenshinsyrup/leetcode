package com.myleetcode.monotonic_deque.daily_temperatures;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int[] dailyTemperatures(int[] T) {
        return dailyTemperaturesByMonoDeque(T);
    }

    // intuition: Mono Deque to get NGE
    // keep a descending mono deque, is peekLast temperature smaler than curIdx temperature, then peekLast's NGE find, record it, pollLast and repeat
    // TC: O(N)
    // SC: O(N)
    private int[] dailyTemperaturesByMonoDeque(int[] temperatures){
        if(temperatures == null || temperatures.length == 0){
            return new int[0];
        }

        int len = temperatures.length;
        int[] ret = new int[len];
        Deque<Integer> ngeDeque = new ArrayDeque<>();
        for(int i = 0; i < len; i++){
            while(!ngeDeque.isEmpty() && temperatures[ngeDeque.peekLast()] < temperatures[i]){ // LC thinks equal value is not NGE
                ret[ngeDeque.peekLast()] = i - ngeDeque.peekLast();

                ngeDeque.pollLast();
            }

            ngeDeque.offer(i);
        }

        return ret;
    }
}
