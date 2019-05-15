package com.myleetcode.binary_search.valid_perfect_square;

class Solution {
    public boolean isPerfectSquare(int num) {
        return isPerfectSquareByBS(num);
    }

    /*
    Perfect Square:
A number made by squaring a whole number.

16 is a perfect square because 42 = 16
25 is also a perfect square because 52 = 25
etc...
    */

    /**
     In an interview, how do we justify using binary search when solving this problem?

     Thats the best approach you can take, instead of using the linear approach which is o(n), basically you are starting from 1,2,3,.....n which means sorted numbers,
     whenever you see a sorted array and you have to find the specific element, Binary search is the best approach so far as it take o(log(n)).
     Lets say the numbers are not sorted, then make it sort which is o(nlog(n)) or use divide and conquerer strategy to find the number
     */

    // TC: O(logN)
    // SC: O(1)
    // naive solution is from 1 to num caculate the square of each int, find if we could got the num before we get a num larger than num, cost O(sqrt(num))
    // we could do better by use BS because we are looking for a val in a sorted array
    private boolean isPerfectSquareByBS(int num){
        if(num <= 0){
            return false;
        }

        // use long for start, end, mid, because we wan to caculate the mid*mid and compare it with num, use long to avoid overflow int
        long start = 1;
        long end = num;
        while(start <= end){
            long mid = start + (end - start) / 2;
            if(mid * mid == num){
                return true;
            }else if(mid * mid < num){
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }

        return false;
    }

}
