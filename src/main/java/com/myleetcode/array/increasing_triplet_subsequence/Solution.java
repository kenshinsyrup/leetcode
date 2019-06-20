package com.myleetcode.array.increasing_triplet_subsequence;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class Solution {
    public boolean increasingTriplet(int[] nums) {
        return increasingTripletByPatienceSorting(nums);
        // return increasingTripletByClassicPatienceSorting(nums);
    }

    // actually this is a special case of problem 300. Longest Increasing Subsequence. so definitly we could use DP to get the LIS and check if the length larger than 3 or not. BUT, it will be O(N^2) TC which is even slower than Sort and Scan solution.

    // solution: with Patience Sorting
    // TC: O(N)
    // SC: O(1)
    // https://leetcode.com/problems/increasing-triplet-subsequence/discuss/79000/My-accepted-JAVA-solution-for-this-question-only-7-lines-clear-and-concise.
    private boolean increasingTripletByPatienceSorting(int[] nums){
        if(nums == null || nums.length <= 2){
            return false;
        }

        int min = Integer.MAX_VALUE;
        int secondMin = Integer.MAX_VALUE;
        // greedy patience sorting
        for(int num: nums){
            if(num <= min){
                min = num;
            }else if(num <= secondMin){
                secondMin = num;
            }else{
                return true;
            }
        }

        return false;
    }

    // this is the classical form to write the Patience Sort
    // TC: O(N)
    // SC: O(N), worst case, all nums are desc order so they are in the first pile
    private boolean increasingTripletByClassicPatienceSorting(int[] nums){
        List<Deque<Integer>> pileList = new ArrayList<>();

        for(int num: nums){
            // temp pile
            Deque<Integer> tempPile = new ArrayDeque<>();
            tempPile.push(num);

            // 1 put the left most pile that's top num is smaller num than current num
            for(Deque<Integer> pile: pileList){
                if(tempPile.peek() <= pile.peek()){
                    pile.push(tempPile.pop());
                    break;
                }
            }

            // 2 if not find such pile, then this is a new pile, add to list
            if(!tempPile.isEmpty()){
                pileList.add(tempPile);
            }

            // check length of list
            if(pileList.size() >= 3){
                return true;
            }
        }

        return false;
    }
}
