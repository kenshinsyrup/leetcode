package com.myleetcode.math.max_points_on_a_line;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxPoints(int[][] points) {
        return maxPointsByMap(points);
    }

    // https://leetcode.com/problems/max-points-on-a-line/discuss/221044/
    // intuition: we could caculate the slope of every two elems, use a Map to count the # of each slope
    // TC: O(N^2)
    // SC: O(N^2)
    private int maxPointsByMap(int[][] points){
        if(points == null || points.length == 0 || points[0] == null || points[0].length == 0){
            return 0;
        }

        int maxNum = 0;
        for(int i = 0; i < points.length; i++){
            Map<String, Integer> slopeNumMap = new HashMap<>();
            int samePointNum = 0; // !!! there are duplicates points in input, have to count them seperately for every points[i]. at first this is 1 represents itself.

            for(int j = 0; j < points.length; j++){
                // duplicate points
                if(points[j][0] == points[i][0] && points[j][1] == points[i][1]){
                    samePointNum++;
                    continue;
                }

                if(points[j][0] == points[i][0]){// vertical line, use the MAX to represent
                    slopeNumMap.put("Vertical", slopeNumMap.getOrDefault("Vertical", 0) + 1);
                }else{
                    // because of input like: [[0,0],[94911151,94911150],[94911152,94911151]]
                    // !!!除法会有精度问题，我们现在就要去避免这个精度问题，我们可以用最大公约数来避免除法，把除数和被除数除以他俩的最大公约数，就是说避免查询double,而去查询两个Int来避免除法. 这样我们得到了一个分数的分子和分母，那么就是一个唯一的斜率了，然后我们用String来表示他存入Map，从而避开了Double除法的精度和Hash问题
                    int numerator = points[j][1] - points[i][1];
                    int denominator = points[j][0] - points[i][0];

                    int gcd = getGcd(numerator, denominator);
                    if(gcd != 0){
                        numerator /= gcd;
                        denominator /= gcd;
                    }

                    // for precision consideration, we use String to Hash, not Double
                    String slopeStr = numerator + "_" + denominator;
                    slopeNumMap.put(slopeStr, slopeNumMap.getOrDefault(slopeStr, 0) + 1);
                }
            }

            // caculate the points num one this slope line
            int localMax = 0;
            for(int val: slopeNumMap.values()){
                // update maxNum
                localMax = Math.max(localMax, val);
            }
            maxNum = Math.max(maxNum, localMax + samePointNum);
        }

        return maxNum;
    }

    // 辗转相除法(Euclidean algorithm)求最大公约数, Greatest Common Divisor
    private int getGcd(int a, int b) {
        if (b == 0){
            return a;
        }

        return getGcd(b, a % b);
    }
}
