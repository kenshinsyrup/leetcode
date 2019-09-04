package com.myleetcode.greedy.minimum_domino_rotations_for_equal_row;

class Solution {
    public int minDominoRotations(int[] A, int[] B) {
        return minDominoRotationsByGreedy(A, B);
    }

    // TC: O(N)
    // SC: O(1)
    // https://leetcode.com/problems/minimum-domino-rotations-for-equal-row/discuss/252802/Thinking-Process
    private int minDominoRotationsByGreedy(int[] A, int[] B){
        if(A == null || A.length <= 1 || B == null || B.length <= 1){
            return 0;
        }

        // assume target is A[0]
        int minRotationByA = minRotation(A[0], A, B);
        // assume target is B[0]
        int minRotationByB = minRotation(B[0], A, B);
        if(minRotationByA == -1){
            return minRotationByB;
        }
        if(minRotationByB == -1){
            return minRotationByA;
        }

        return Math.min(minRotationByA, minRotationByB);

    }

    // try to rotate two given Array to a all target array, keep the min rotation num.
    private int minRotation(int target, int[] A, int[] B){
        int aRotation = 0;
        int bRotation = 0;
        int len = A.length;
        for(int i = 0; i < len; i++){
            if(A[i] != target && B[i] != target){
                return -1;
            }else if(A[i] != target){
                aRotation++;
            }else if (B[i] != target){
                bRotation++;
            }
        }

        return Math.min(aRotation, bRotation);
    }
}
