package com.myleetcode.binary_search.peak_index_in_a_mountain_array;

class Solution {
    public int peakIndexInMountainArray(int[] A) {

        // special case
        if(A == null){
            return -1;
        }

        // return peakIndexInMountainArrayByBinarySearch(A);
        return peakIndexInMountainArrayByBinarySearchOpt(A);
    }

    // optimization: A is definitely a mountain, then A must be like A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1]. Means we only need to find the index where A[idx] > A[idx + 1]

    // linear scan
    // TC: O(N)
    // SC: O(1)
    private int peakIndexInMountainArrayByLinear(int[] A){
        int i = 0;
        while(A[i] < A[i + 1]){
            i++;
        }
        return i;
    }

    // Binary Search, 同样的，找第一个A[i]>A[i+1]
    private int peakIndexInMountainArrayByBinarySearchOpt(int[] A){
        int start = 0;
        int end = A.length - 1;
        int mid = 0;
        while(start <= end){
            mid = (end - start ) / 2 + start;
            if(A[mid] < A[mid + 1]){
                start = mid + 1; // ignore left half. 必须跳过mid这个不成立的点，否则会导致死循环，考虑[0,1,0],如果start = mid; 那么死循环于start==0,mid ==0,end==1
            }else{
                end = mid - 1; // ignore right half.
            }
        }
        // while 退出后,根据mid的以及if-else条件来找到需要的index。比如这里，根据if-else条件，mid指向的是最后一个满足if条件的数字，那么start指向的就是不满足if的第一个数字，就是我们需要的，所以返回start
        // 如[0,1,0]
        //***start: 0 end: 2 mid: 1
        //after if-else start: 0 end: 0 mid: 1
        // ***start: 0 end: 0 mid: 0
        // after if-else start: 1 end: 0 mid: 0
        return start;
    }

    // intuition:
    // since array is a mountain, then must exist such i that A[i] > A[i-1] && A[i] > A[i + 1]
    // binary search
    private int peakIndexInMountainArrayByBinarySearch(int[] A){
        // special case
        if(A.length < 3){
            return -1;
        }

        int start = 0;
        int end = A.length - 1;
        while(start < end){
            int mid = (end - start ) / 2 + start;
            if(A[mid] > A[mid - 1] && A[mid] > A[mid + 1]){
                return mid;
            }else if(A[mid] > A[start] && A[mid] > A[mid - 1]){
                start = mid;
            }else if(A[mid] > A[end] && A[mid] > A[mid + 1]){
                end = mid;
            }
        }

        return -1;
    }
}
