package com.myleetcode.math.count_primes;

class Solution {
    public int countPrimes(int n) {
        // return countPrimesByNestedLoop(n); // WRONG, TLE
        // return countPrimesByCaculateAll(n); // WRONG, TLE
        return countPrimesBySieveOfEratosthenes(n);
    }

    // Sieve of Eratosthenes
    // 所使用的原理是从2开始，将每个素数的各个倍数，标记成合数. 埃拉托斯特尼筛法是列出所有小素数最有效的方法之一
    // TC: hard to say, but nearly O(N)
    // SC: O(N)
    private int countPrimesBySieveOfEratosthenes(int n){
        if(n <= 1){
            return 0;
        }

        int count = 0;
        boolean[] notPrime = new boolean[n];
        for(int i = 2; i < n; i++){
            if(!notPrime[i]){
                count++;

                for(int j = 2; j * i < n; j++){
                    notPrime[i * j] = true;
                }
            }
        }

        return count;

    }


    // TLE
    // TC: O(N^2)
    // SC: O(N)
    private int countPrimesByCaculateAll(int n){
        if(n <= 1){
            return 0;
        }

        // caculate all not prime numbers
        boolean[] notPrime = new boolean[n];
        for(int i = 2; i < n; i++){
            for(int j = 2; j < n; j++){
                if(i * j < n){
                    notPrime[i * j] = true;
                }
            }
        }

        // count primes
        int count = 0;
        for(int i = 2; i < n; i++){
            if(!notPrime[i]){
                count++;
            }
        }

        return count;
    }

    // TLE
    // TC: O(n^3)
    // SC: O(1)
    // intuition: naive solution is nested for loop to count, but this time complexity is too large, Time Limit Exceeded
    private int countPrimesByNestedLoop(int n){
        if(n <= 1){
            return 0;
        }

        int count = 0;
        for(int i = 2; i < n; i++){
            if(isPrime(i)){
                count++;
            }
        }

        return count;
    }

    private boolean isPrime(int n){
        for(int i = 2; i <= n; i++){
            for(int j = 2; j <= n; j++){
                if(i * j == n){
                    return false;
                }
            }
        }
        return true;
    }
}
