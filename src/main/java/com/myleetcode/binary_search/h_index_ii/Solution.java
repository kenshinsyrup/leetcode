package com.myleetcode.binary_search.h_index_ii;

class Solution {
    public int hIndex(int[] citations) {
        return hIndexByBS(citations);
    }

    /*
    出错点:
    1 citations可能不存在合法h，返回0. 如citations为[0, 0, 0]...
    2 智障: output需要的是，papers的个数，错误的返回了citations[idx], 应该是(N-1)-idx+1
    */

    // check the 274. H-Index, it's not sorted

    // intuition: citations array is in ascending order, so we could consider the BS sol
    // 题意 ：
    // say the total num of papers in citations is N, say we have a index idx, then citations[idx] means the idx-paper is cited citations[idx] times, and (N-1)-idx+1 is the num of papers that is cited at least citations[idx] times.
    // then, our objective is to find the first idx that citations[idx]>=((N-1)-idx+1)
    // this is the H-Index means
    // TC: O(logN)
    // SC: O(1)
    private int hIndexByBS(int[] citations){
        if(citations == null || citations.length == 0){
            return 0;
        }

        int idx = binarySearch(citations);
        if(idx == -1){
            return 0;
        }

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
}
