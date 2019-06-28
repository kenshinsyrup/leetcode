package com.myleetcode.array.monotonic_array;

class Solution {
    public boolean isMonotonic(int[] A) {
        return isMonotonicByTraverse(A);
        // return isMonotonicByDivideAndConquer(A);
    }

    // the interesting thing is:
    // the D&D version has 7ms Runtime in LeetCode faster than 6.85% and has 49.9MB Memory Usage less than 94.73%
    // the traverse with variable version has 2ms Runtime faster than 78.16% and has 67.9MB Memory Usage less than 15.06%

    // TC: O(logN)
    // SC: O(logN)
    private boolean isMonotonicByDivideAndConquer(int[] arr){
        if(arr == null || arr.length <= 1){
            return true;
        }

        return checkByDivideAndConquer(arr, 0, arr.length - 1) != 3;
    }

    // return 0 means front==back, -1 means front>back, 1 means front<back
    private int checkByDivideAndConquer(int[] arr, int start, int end){
        if(start >= end){
            return 0;
        }

        int mid = start + (end - start) / 2;
        int left = checkByDivideAndConquer(arr, start, mid);
        int right = checkByDivideAndConquer(arr, mid + 1, end);

        // current trend is the adjacent part of left and right
        int cur = 0;
        if((mid + 1 < arr.length) && arr[mid] < arr[mid + 1]){
            cur = 1;
        }else if((mid + 1 < arr.length) && arr[mid] > arr[mid + 1]){
            cur = -1;
        }

        // 0 if already find 3, return 3
        if(left == 3 || right == 3){
            return 3;
        }

        // no 3 find
        // 1 left part and right part and cur part has same trend
        if(left == cur && cur == right){
            return left;
        }

        // 2 if the three not all same
        //      2.1 if one of them is 0 and the other two are same
        if(left == 0 && right == cur){
            return cur;
        }else if(cur == 0 && left == right){
            return left;
        }else if(right == 0 && left == cur){
            return cur;
        }

        //      2.2 if two of them are 0, return the other one
        if(left == 0 && cur == 0){
            return right;
        }else if (left == 0 && right == 0){
            return cur;
        }else if(cur == 0 && right == 0){
            return left;
        }

        // otherwise
        return 3;
    }

    // intuition: use variable flag to represent the array is MI or MD or equal, choose the 0 and len-1 idx num, if equal flag is 0, if nums[0] > nums[len-1] the flag is -1, if nums[0] < nums[len-1] the flag is 1. then traverse the array, if adjacent elems are not comply the flag, false
    // TC: O(N)
    // SC: O(1)
    private boolean isMonotonicByTraverse(int[] arr){
        if(arr == null || arr.length <= 1){
            return true;
        }

        int len = arr.length;
        int flag = 0;
        if(arr[0] > arr[len - 1]){
            flag = -1;
        }else if(arr[0] < arr[len - 1]){
            flag = 1;
        }

        return check(arr, flag);
    }

    private boolean check(int[] arr, int flag){
        int len = arr.length;
        for(int i = 0; i < len - 1; i++){
            if(flag == 0){
                if(arr[i] != arr[i + 1]){
                    return false;
                }
            }else if(flag == -1){
                if(arr[i] < arr[i + 1]){
                    return false;
                }
            }else{
                if(arr[i] > arr[i + 1]){
                    return false;
                }
            }
        }

        return true;
    }
}
