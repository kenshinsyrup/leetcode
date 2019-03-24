package com.myleetcode.array.sort_array_by_parity_ii;

class Solution {
    public int[] sortArrayByParityII(int[] A) {
        return sortArrayByParityIIByTwoPointers(A);
    }

    // intuition: two pointers, one pointer oddI traverse odd i and another pointer evenI traverse even i, if: 1 value at oddI is odd and evenI is even, next; 2 value at oddI is even and evenI is odd, exchange value(because we dont care about order); 3 value at oddI is even and evenI is even, evenI next; 4 value at oddI is odd and value at evenI is odd, oddI next. until oddI reach n-1 or evenI reach n-2, because anyone reach its end, the another one no need to check
    // TC: O(N)
    // SC: O(1)
    private int[] sortArrayByParityIIByTwoPointers(int[] A){
        if(A == null || A.length == 0){
            return A;
        }

        int len = A.length;
        int oddI = 1;
        int evenI = 0;
        while(oddI <= len -1 && evenI <= len - 2){
            if(A[oddI] % 2 == 0 && A[evenI] % 2 == 1){
                int temp = A[oddI];
                A[oddI] = A[evenI];
                A[evenI] = temp;

                oddI += 2;
                evenI += 2;
            }else if(A[oddI] % 2 == 0 && A[evenI] % 2 == 0){
                evenI += 2;
            }else if(A[oddI] % 2 == 1 && A[evenI] % 2 == 1){
                oddI += 2;
            }else{
                oddI += 2;
                evenI += 2;
            }
        }

        return A;
    }

}
