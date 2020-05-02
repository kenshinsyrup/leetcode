package com.myleetcode.array.kids_with_the_greatest_number_of_candies;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        return kidsWithCandiesByMap(candies, extraCandies);
    }

    private List<Boolean> kidsWithCandiesByMap(int[] candies, int extraCandies) {
        if (candies == null || candies.length == 0) {
            return new ArrayList<>();
        }

        int greastest = candies[0];
        for (int candy : candies) {
            greastest = Math.max(greastest, candy);
        }

        List<Boolean> res = new ArrayList<>();
        for (int candy : candies) {
            if (candy + extraCandies >= greastest) {
                res.add(true);
            } else {
                res.add(false);
            }
        }

        return res;
    }
}