package com.myleetcode.math.ugly_number;

class Solution {
    public boolean isUgly(int num) {
        // return isUglyByPrime(num); MLE
        return isUglyByMath(num);
    }

    // https://leetcode.com/problems/ugly-number/discuss/69332/Simple-java-solution-with-explanation
    // the thought behind the problem is not so hard, we dont need to get all primes less than num and then check. we could just check if the num could be divided to 1 only by 2,3,5
    private boolean isUglyByMath(int num){
        if(num <= 0){
            return false;
        }
        if(num == 1){
            return true;
        }

        while(num > 1){
            if(num % 2 == 0){
                num /= 2;
            }else if(num % 3 == 0){
                num /= 3;
            }else if(num % 5 == 0){
                num /= 5;
            }else{
                return false;
            }
        }

        return true;
    }

    // MLE Error at input testcase around Integer.MAX_VALUE because the boolean[] is too large for leetcode
    // TC: O(N)
    // SC: O(N)
    // intuition: first we get all the primes less than the given num with the Sieve of Eratosthenes, then we check if the num could be divided by these primes except 2,3,5
    private boolean isUglyByPrime(int num){
        if(num <= 0){
            return false;
        }
        if(num == 1){
            return true;
        }

        // Sieve of Eratosthenes
        boolean[] isNotPrime = new boolean[num + 1]; // here and next, we need check the num itself, because the leetcode think 7 is not ugly because it has prime factor of 7
        for(int i = 2; i <= num; i++){
            if(!isNotPrime[i]){
                for(int j = 2; j * i <= num; j++){
                    isNotPrime[j * i] = true;
                }
            }
        }

        // check
        for(int i = 2; i <= num; i++){
            if(i != 2 && i != 3 && i != 5 && !isNotPrime[i]){
                if(num % i == 0){
                    return false;
                }
            }
        }

        return true;

    }
}
