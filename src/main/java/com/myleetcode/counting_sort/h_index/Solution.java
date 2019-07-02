package com.myleetcode.counting_sort.h_index;

import java.util.Arrays;

class Solution {
    public int hIndex(int[] citations) {
        return hIndexByBS(citations); // the first sol in interview
        // return hIndexByCountingSort(citations); // the final optimization in interview
    }

    // sol 1: sort first, then become the 275. H-Index II
    // 题意 ：
    // say the total num of papers in citations is N, say we have a index idx, then citations[idx] means the idx-paper is cited citations[idx] times, and (N-1)-idx+1 is the num of papers that is cited at least citations[idx] times.
    // then, our objective is to find the first idx that citations[idx]>=((N-1)-idx+1)
    // this is the H-Index means
    // TC: O(NlogN)
    // SC: O(1)
    private int hIndexByBS(int[] citations){
        if(citations == null || citations.length == 0){
            return 0;
        }

        // sort, NlogN
        Arrays.sort(citations);

        // bs, logN
        int idx = binarySearch(citations);
        if(idx == -1){
            return 0;
        }

        // caculate num of papers
        return (citations.length - 1) - idx + 1;
    }

    private int binarySearch(int[] citations){
        int len = citations.length;
        int start = 0;
        int end = len - 1;
        while(start <= end){
            int mid = start + (end - start) / 2;

            if(citations[mid] < ((len - 1) - mid + 1)){
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }

        // if dont find, return -1
        if(start == len){
            return -1;
        }

        return start;
    }

    // sol 2, counting sort
    // https://leetcode.com/problems/h-index/discuss/70818/Java-O(n)-time-with-easy-explanation.
    // we could count all citations to a new array, in this array, index means citation num, and value means the num of papers that has such citation num.
    // we need a little change: according to the definition, the h is must in the range of [0, (len-1)/2], so we could say the new array could has a length equals  to citations array's length plus 1(we count citation num from 0 to N), in this way, we count all papers that have more than N citations to the N-index of the new array, where N is the length of citations
    // why we do this? because: 1 according to the definition, we could do this, and if we do so, we could save space, so why not; 2 we have to, because citation num maybe too large that out of the memory limit, so we use the N-idx to store citation num >= N
    // TC: O(N)
    // SC: O(N)
    private int  hIndexByCountingSort(int[] citations){
        if(citations == null || citations.length == 0){
            return 0;
        }

        int len = citations.length;

        // count sort
        int[] countArr = new int[len + 1];
        for(int ci: citations){
            if(ci >= len){
                countArr[len]++;
            }else{
                countArr[ci]++;
            }
        }

        // now we have sorted the array, countArr is sorted by citations num
        // find the h
        int papers = 0;
        for(int i = len; i>= 0; i--){
            papers += countArr[i];

            // first i that total number of papers with more than i citations >= i
            if(papers >= i){
                return i;
            }
        }

        return 0;
    }
}
