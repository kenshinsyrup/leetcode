package com.myleetcode.array.sum_of_even_numbers_after_queries;

class Solution {
    public int[] sumEvenAfterQueries(int[] A, int[][] queries) {
        //https://leetcode.com/problems/sum-of-even-numbers-after-queries/discuss/231099/Java-11-liner-odd-even-analysis-time-O(n)
        
        // special case
        if(A == null || A.length == 0 || queries == null || queries.length == 0 || queries[0] == null || queries[0].length == 0){
            return null;
        }
        
        int[] res = new int[queries.length];
        
        int evenSum = 0;
        for(int i = 0; i < A.length; i++){
            if(A[i] % 2 == 0){
                evenSum += A[i];
            }
        }
        
        for(int i = 0; i < queries.length; i++){
            int val = queries[i][0];
            int index = queries[i][1];
            if(A[index] % 2 == 0){
                evenSum -= A[index];
            }
            A[index] += val;
            
            if(A[index] % 2 == 0){
                evenSum += A[index];
            }
            
            res[i] = evenSum;
        }
        
        return res;
    }
}