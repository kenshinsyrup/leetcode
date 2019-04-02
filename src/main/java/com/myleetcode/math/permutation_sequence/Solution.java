package com.myleetcode.math.permutation_sequence;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public String getPermutation(int n, int k) {
        // return getPermutationByMath(n, k);// WRONG
        return getPermutationByMathII(n, k);
    }

    // TC: O(N^2), remove from list is O(N), for loop is O(N)
    // SC: O(N)
    // The logic is as follows: for n numbers the permutations can be divided to (n-1)! groups, for n-1 numbers can be divided to (n-2)! groups, and so on. Thus k/(n-1)! indicates the index of current number, and k%(n-1)! denotes remaining index k for the remaining n-1 numbers.
// We keep doing this until n reaches 0, then we get n numbers permutations that is kth.
    // here is the correct formula: https://leetcode.com/problems/permutation-sequence/discuss/22508/An-iterative-solution-for-reference
    private String getPermutationByMathII(int n, int k){
        if(k <= 0 || n <= 0){
            return "";
        }

        String ret = "";

        // number list: 1...n
        List<Integer> numsList = new ArrayList<>();
        for(int i = 1; i <= n; i++){
            numsList.add(i);
        }

        // caculates the factorial of each i
        int[] factorials = new int[n+1];
        factorials[0] = 1; // !!!important. 这个必须，避免下面k/factorials[i-1]出现k/0的bug，而数学上factorial of 0是1
        int product = 1;
        for(int i = 1; i <= n; i++){
            product *= i;
            factorials[i] = product;
        }

        k = k -1; // 0 based


        for(int i = n; i >= 1; i--){
            // factorial of i's subset, ie, permutations of i's subset
            int factorial = factorials[i - 1];

            // number idx in number list
            int idx = k / factorial;

            ret += numsList.get(idx);

            // remove the chosed number from list
            numsList.remove(idx);

            // update k
            k = k % factorial;

        }

        return ret;
    }

    // WRONG
    // intuition: math problem. every time we find a n that n!<=k&&(n+1)!>=k, original array: nums is [1,2...n], then first number is nums[k/n], k=%n,nums remove the k/n index number. until nums is empty
    private String getPermutationByMath(int n, int k){
        if(k <= 0 || n <= 0){
            return "";
        }

        String ret = "";
        List<Integer> numsList = new ArrayList<>();
        for(int i = 1; i <= n; i++){
            numsList.add(i);
        }

        permutate(numsList, ret, k, n);

        return ret;
    }

    private void permutate(List<Integer> numsList, String ret, int k, int n){
        if(numsList.size() == 0){
            return;
        }
        if(numsList.size() == 1){
            ret += numsList.get(0);
            return;
        }

        int base = 0;
        boolean find = false;
        for(int i = 1; i <= n; i++){
            int product = 1;
            for(int j = 1; j <= i; j++){
                product *= j;
            }
            if(product > k){
                find = true;
                base = i - 1;
                break;
            }
        }

        if(!find){
            return;
        }

        ret += numsList.get(k / base);
        System.out.println("ret: "+ret);
        numsList.remove(k/base);
        k = k - base;

        permutate(numsList, ret, k, n);
    }
}
