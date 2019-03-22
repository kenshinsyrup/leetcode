package com.myleetcode.binary_search.first_bad_version;

/* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */

//public class Solution extends VersionControl {
public class Solution {
    public int firstBadVersion(int n) {
        return firstBadVersionByBS(n);
    }

    // intuition: binary search
    private int firstBadVersionByBS(int n){
        if(n <= 1){
            return n;
        }

        int start = 1;
        int end = n;
        int mid = 0;

        while(start <= end){
            mid = start + (end - start) / 2;
            if(!isBadVersion(mid)){
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }

        // BS 结束后，start保持的是第一个 不是他所在的条件的值.
        // 所以根据题意，我们要返回first bad，那么就是start，如果要返回 last not bad，就是start - 1
        return start;
    }

    // just for fix the "Cannot resolve the method isBadVersion()" error:
    private boolean isBadVersion(int x){
        return true;
    }
}
