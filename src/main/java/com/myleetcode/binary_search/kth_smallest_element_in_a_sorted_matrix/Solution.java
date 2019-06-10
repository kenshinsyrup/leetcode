package com.myleetcode.binary_search.kth_smallest_element_in_a_sorted_matrix;

import java.util.Comparator;
import java.util.PriorityQueue;

class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        // two solutions must be known

        // return kthSmallestByPQ(matrix, k);// MinHeap, easy to understand
        return kthSmallestByBS(matrix, k); // BS, fast, maybe the real intent of the problem
    }

    // here, should understand that there's no words say every row has n column, we just know we have a n*n 2-d array

    // solution 2: with BS
    // TC: O((M+N)*log(Max-Min)), M is thr rowLen, N is the colLen, Max is the max num in matrix and Min is the smallest num in matrix
    // SC: O(1)
    // https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/discuss/85173/Share-my-thoughts-and-Clean-Java-Code/150126
    // intuition: first we know the smallest num is the matrix[0][0] and the largest is the matrix[rows-1][cols-1]
    // since every row is sorted and every col is sorted, we have a range [smallestNum, largestNum], then we could get a midNum every time, and  we assume the num exists in the matrix, then, we check every row, find # of elems that smaller than the midNum, if # larger than k, discard the right part, if smaller than k, discard the left part, if equals, since we are assuming the midNum exists, so we need to return the current max number, so we have to record the max num in every BS
    // at last, we return the most left num in search range, because every time we discard the lfet part if # smaller than k, so the last range, the left most num is the kth smallest
    // the hard part is, we have to be clear, although the whole matrix is not sorted first by col then by row, but we know is sorted in every row and in every col, so we first get the smallest num and largest num to get a search range; then we search every row to count all num that samller than midNum in every col
    private int kthSmallestByBS(int[][] matrix, int k){
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            return 0;
        }
        if(k <= 0){// because kth is 1based
            return 0;
        }

        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        int low = matrix[0][0];
        int high = matrix[rowLen - 1][colLen - 1];

        // standard part 1 of BS
        while(low <= high){
            int mid = low + ((high - low) >> 1);


            // special part:

            // count the # of num smaller than mid
            int count = 0;
            int j = colLen - 1; // col idx
            int realMaxNum = low; // record the real max num, because the mid may not exist in matrix, we set it to low at first

            // traverse every row
            for(int i = 0; i < rowLen; i++){
                // !!! try to find the col idx in this row that matrix[i][j] <= mid; here we dont reset j to colLen-1 inevery for loop, this is a trick to reduce the for-while-nested-loop from rowLen*colLen to rowLen+colLen TC.
                // why it works, first is because we are moving form up to down in the for loop, since every col is sorted, so if matrix[i][j]>mid in row i and col j, then it must also true in row i+1 and col j.
                while(j >= 0 && matrix[i][j] > mid){
                    j--;
                }

                // find the col idx, count
                if(j >= 0){
                    count += (j + 1); // count, every num from this row's start to idx j are samller than mid

                    realMaxNum = Math.max(realMaxNum, matrix[i][j]); // real max num is realMaxNum it self, or the max num current find
                }
            }

            // standard part 2 of BS
            // check the count of this BS
            if(count == k){
                return realMaxNum;
            }else if(count < k){
                // discard left part
                low = mid + 1;
            }else{
                // discard right part
                high = mid - 1;
            }
        }

        // standard part 3 of BS
        // after the BS, if we dont find a count equals k, then we here return the low, ie, the first num that makes (count < k) invalid, ie the first num that sastify (count>=k)
        return low;

    }

    // solution 1: with Heap
    // https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/discuss/85301/Java-PriorityQueue-Solution
    // TC: O(KlogK)
    // SC: O(K)
    // intuition: observe the matrix, if we are at a point, like the matrix[0][0], the matrix is sorted in row and in col(!!! but not sorted in every row and then row by row, look at the eg in maxtrix[1][2] and matrix[2][0]), so we dont know which of the cur point's neighbors is the smaller one, the right point or the bottom point, so we need a Heap to help us.
    // !!! Here, must be aware, only when we are at the first row, we consider offering our right point in Heap, because, if we are not at the first row, means we has previous row, so our "right" points must has been processed by previous row as its "bottom" points.!!!
    // with a MinHeap, we put the start point matrix[0][0] in, then we poll the top point of pq out, and push its right and bottom point to the Heap, and we count by 1. by this way, we count one smallest point in the pq and pushed two candidates in. if we count k-1 times, then the top point in the pq will be the kth smallest point.
    private int kthSmallestByPQ(int[][] matrix, int k){
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0){
            return 0;
        }
        if(k <= 0){// because kth is 1based
            return 0;
        }

        // we use int[] to represent a point
        PriorityQueue<int[]> pointPQ = new PriorityQueue<>(new Comparator<int[]>(){
            public int compare(int[] p1, int[] p2){
                return matrix[p1[0]][p1[1]] - matrix[p2[0]][p2[1]];
            }
        });

        // init, offer matrix[0][0] in
        pointPQ.offer(new int[]{0, 0});

        // since k is always valid, so we loop k-1 times
        for(int i = 1; i < k; i++){
            int[] curP = pointPQ.poll();

            // if has bottom point, offer
            if(curP[0] + 1 < matrix.length){
                pointPQ.offer(new int[]{curP[0] + 1, curP[1]});
            }
            // !!! if we are at the first row and has right point, offer
            if(curP[0] == 0 && curP[1] + 1 < matrix[0].length){
                pointPQ.offer(new int[]{curP[0], curP[1] + 1});
            }
        }

        // we have get k-1 smallest elems, so the current top is the kth smallest elem
        int[] point = pointPQ.poll();
        return matrix[point[0]][point[1]];

    }

}
