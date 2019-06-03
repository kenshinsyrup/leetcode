package com.myleetcode.array.longest_consecutive_sequence;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public int longestConsecutive(int[] nums) {
        return longestConsecutiveByMap(nums);
    }

    // https://leetcode.com/problems/longest-consecutive-sequence/discuss/41055/My-really-simple-Java-O(n)-solution-Accepted/39096
    // we use a Map to contains the num and the range it belongs to, like if we have 1,2,3 in the array, then we have a map like {1:3, 2:3, 3:3}
    // then, when we traverse the array, if we find a new num, we check if we have its front and back num or not, for eg, we find a 4 now, then we check if we have met 3, here we have, so we get the len int the front of 4 is 3; we check if we have met 5, here we have not, then the len at the back of 4 is 0, so we know 4 belongs to a range 3+0+1 is 4.
    // so, we could update all the key:value pairs in front of 4 and back of 4. since now back of 4 we have no num, so we ignore it. but in front of 4 we have 3 k:v pairs, we could update them all to the new range len 4.
    // !!! BUT, to achieve the O(N) RT, we could not do this, actually since every time we find a new num, we only check its consecutive front and back num, so we know we only need update every range's boundary ie the most left and right k:v.
    // so, here we could just update the key 4-3 ie the key 1 with the new len 4.
    // !!! then, we need to de-duplicate, back to the first step, if we have 1,2,2,3 in array, we have the range map {1:3,2:3,3:3}, not 4, because 2 is duplicates. So we need put every num we have met to the map with its range, then if we find the num again, we just skip it.
    private int longestConsecutiveByMap(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int max = 0;
        Map<Integer, Integer> numRangeMap = new HashMap<>();
        for(int num: nums){
            // de-duplicate, skip the num that has been processed
            if(numRangeMap.containsKey(num)){
                continue;
            }

            // get the range of consecutive front and back nums
            int leftRange = numRangeMap.getOrDefault(num - 1, 0);
            int rightRange = numRangeMap.getOrDefault(num + 1, 0);

            // caculate the range the current num belongs to
            int range = leftRange + rightRange + 1;

            // max
            max = Math.max(max, range);

            // !!!update left and right boundary k:v only to reach O(1) operations, then totally get a O(N) RT
            // here, if leftRange of rightRange is 0, means the boundary is cur num itself
            numRangeMap.put(num - leftRange, range);
            numRangeMap.put(num + rightRange, range);

            // !!!no matter whether cur num is the boundary or not, we should put it into map
            // de-duplicate, put the cur num to Map as a mark of has been processed
            numRangeMap.put(num, range);
        }

        return max;
    }
}
