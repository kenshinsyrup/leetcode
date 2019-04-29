package com.myleetcode.math.happy_number;

import java.util.HashSet;
import java.util.Set;

class Solution {
    public boolean isHappy(int n) {
        // return isHappyByCaculate(n);
        return isHappyByFloydCycleDetection(n);
    }

    // Reduce SC to O(1)
    /*
    according to this description: repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.
    actually if we get 1, then we also get an endless cycle.
    so, this must give us an endless cycle at somewhere, let's say its numIntersection, so we if we could find this entrence of the cycle, ie the numIntersecion, we could just chekc if it's 1.
    https://leetcode.com/problems/happy-number/discuss/56917/My-solution-in-C(-O(1)-space-and-no-magic-math-property-involved-)
    https://leetcode.com/problems/happy-number/discuss/56917/My-solution-in-C(-O(1)-space-and-no-magic-math-property-involved-)/263308
    */
    private boolean isHappyByFloydCycleDetection(int n){
        if(n <= 0){
            return false;
        }
        if(n == 1){
            return true;
        }

        // slow moves one step
        int slow = getDigitSquareSum(n);

        // fast move two steps
        int fast = getDigitSquareSum(n);
        fast = getDigitSquareSum(fast);

        while(slow != fast){
            slow = getDigitSquareSum(slow);

            fast = getDigitSquareSum(fast);
            fast = getDigitSquareSum(fast);
        }

        if(slow == 1){
            return true;
        }
        return false;

    }

    private int getDigitSquareSum(int n){
        int sum = 0;
        while(n != 0){
            int lastDigit = n % 10;
            sum += lastDigit * lastDigit;

            n /= 10;
        }
        return sum;
    }

    // TC: O(N)
    // SC: O(N)
    // intuition: translate it. https://leetcode.com/problems/happy-number/discuss/56913/Beat-90-Fast-Easy-Understand-Java-Solution-with-Brief-Explanation
    // use a HashSet to avoid infinite loop
    private boolean isHappyByCaculate(int n){
        if(n <= 0){
            return false;
        }
        if(n == 1){
            return true;
        }

        Set<Integer> visitedSet = new HashSet<>();
        while(!visitedSet.contains(n)){
            visitedSet.add(n);

            int sum = 0;
            while(n != 0){
                int lastDigit = n % 10;
                sum += lastDigit * lastDigit;

                n /= 10;
            }

            if(sum == 1){
                return true;
            }

            n = sum;
        }

        return false;
    }
}
