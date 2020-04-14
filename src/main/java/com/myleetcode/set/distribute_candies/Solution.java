package com.myleetcode.set.distribute_candies;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    public int distributeCandies(int[] candies) {
        return distributeCandiesBySet(candies);
    }

    private int distributeCandiesBySet(int[] candies) {
        if (candies == null || candies.length == 0) {
            return 0;
        }

        Set<Integer> candySet = new HashSet<>();
        for (int candy : candies) {
            candySet.add(candy);
        }

        return candySet.size() > candies.length / 2 ? candies.length / 2 : candySet.size();
    }
}