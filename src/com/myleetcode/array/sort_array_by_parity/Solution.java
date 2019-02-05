package com.myleetcode.array.sort_array_by_parity;

class Solution {
    public int[] sortArrayByParity(int[] A) {
        
        // special case
        if(A == null){
            return A;
        }
        
        return sortArrayByParityTwoPointer(A);
    }
    
    // 直观印象是，可以按照partition找pivot分两部分的方式来做。用一个指针cur来遍历，一个指针indicator来指向第一个不确定是否为even的数字，如果cur遇到even，那么swap(A[cur], A[indicator])。直到cur遍历完毕。
    // TC: O(n)
    // SC: O(1)
    private int[] sortArrayByParityTwoPointer(int[] A){
        int indicator = 0;// 指示第一个非even的数
        int temp = 0; // for swap
        for(int i = 0; i < A.length; i++){ // i is cur in comments
            if(A[i] % 2 == 0){
                temp = A[i];
                A[i] = A[indicator];
                A[indicator] = temp;
                indicator++;
            }
        }
        
        return A;
    }
}