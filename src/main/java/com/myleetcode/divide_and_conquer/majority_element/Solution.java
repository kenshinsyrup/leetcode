package com.myleetcode.divide_and_conquer.majority_element;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public int majorityElement(int[] nums) {
        // return majorityElementByTraverseAndMap(nums);
        return majorityElementByDivideAndConquer(nums);
    }

    // the Runtime is not so precise, the D&D has theorical RT O(N*logN) but Runtime in LC is 1ms, but the traverse with Map has theorical RT O(N) but Runtime in LC is 20+ms

    // intuition:
    // 1 num should be looked as the unique elem and we need to count the appearance times of it, so we could sue a Map, key is the num and value is the times. then whenever we get a time that is larger than n/2 we return the num. This cost TC O(N) and SC O(N)
    // 2 if we thought this problem with the D&D, we could know, if we divide an array to two parts, then: if left part and right part has the same Majority then we know the array has this Majority; if not, then we could count the frequency of the left Majority and the right Majority in  the array, choose the Majority has more frequency to be the array's Majority. This is the basic idea and at last we get the global Majority. TC is O(N*logN) because the count frequency cost O(N) and recursion height is logN, SC is O(logN)

    // 1 traverse with Map
    // TC: O(N)
    // SC: (N)
    private int majorityElementByTraverseAndMap(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        Map<Integer, Integer> numFreqMap = new HashMap<>();
        for(int num: nums){
            // make the key num exist
            numFreqMap.putIfAbsent(num, 0);

            // add
            numFreqMap.put(num, numFreqMap.get(num) + 1);

            // check
            if(numFreqMap.get(num) > (len / 2)){
                return num;
            }
        }

        return 0;
    }

    // 2 D&D
    // TC: O(N*logN)
    // SC: O(logN)
    private int majorityElementByDivideAndConquer(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }

        return searchMajority(nums, 0, nums.length - 1);
    }

    private int searchMajority(int[] nums, int start, int end){
        // base case
        if(start >= end){
            return nums[start];
        }

        int mid = start + (end - start) / 2;

        // get left Majority and rightMajority
        int leftM = searchMajority(nums, start, mid);
        int rightM = searchMajority(nums, mid + 1, end);

        // if leftM equals to rightM, then the whole range M is the same
        if(leftM == rightM){
            return leftM;
        }

        // otherwise, we should choose the one has more frequency
        int i = start;
        int leftMFreq = 0;
        int rightMFreq = 0;
        while(i <= end){
            if(nums[i] == leftM){
                leftMFreq++;
            }else if(nums[i] == rightM){
                rightMFreq++;
            }

            i++;
        }
        if(leftMFreq > rightMFreq){
            return leftM;
        }
        return rightM;
    }

}
