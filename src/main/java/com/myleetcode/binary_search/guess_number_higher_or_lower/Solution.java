package com.myleetcode.binary_search.guess_number_higher_or_lower;

/* The guess API is defined in the parent class GuessGame.
   @param num, your guess
   @return -1 if my number is lower, 1 if my number is higher, otherwise return 0
      int guess(int num); */


// intuition: the problem description is ambiguous, but seems like it wants us use the BS to find a num in 1:n and return this num
//public class Solution extends GuessGame {
public class Solution{
    public int guessNumber(int n) {
        int start = 1;
        int end = n;
        while(start <= end){
            int mid = start + (end - start) / 2;
            if(guess(mid) == 0){
                return mid;
            }else if(guess(mid) > 0){//!!! here is the trap, guess return 1 means "my num is higher" means our mid is low...
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }

        return start;
    }

    // for IDEA error detect
    private int guess(int num){
        return 0;
    }
}


