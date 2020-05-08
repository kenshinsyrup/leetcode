package com.myleetcode.array.pancake_sorting;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<Integer> pancakeSort(int[] A) {
        return pancakeSortByFlipToSort(A);
    }

    /*
    Explanation:
    Find the index ith of the next maximum number x.
    Reverse first (i + 1) numbers, so that the x will be at A[0]. Record this flip.
    Reverse first x numbers, so that x will be at A[x - 1] which is the position it should be in after sorted. Record this flip.
    Repeat this process N times.

    TC: O(N^2)
    SC: O(1)
    */
    public List<Integer> pancakeSortByFlipToSort(int[] A) {
        List<Integer> list = new ArrayList<>();

        int len = A.length;
        int maxNeedFlip = len;
        for (int i = 0; i < len; i++) {
            int index = find(A, maxNeedFlip);

            flip(A, index);
            list.add(index + 1);

            flip(A, maxNeedFlip - 1);
            list.add(maxNeedFlip);

            maxNeedFlip--;
        }

        return list;

    }

    private int find(int[] A, int target) {
        for (int i = 0; i < A.length; i++) {
            if (A[i] == target) {
                return i;
            }
        }

        return -1;
    }

    private void flip(int[] A, int end) {
        int start = 0;
        while (start <= end) {
            int temp = A[start];
            A[start] = A[end];
            A[end] = temp;

            start++;
            end--;
        }
    }
}