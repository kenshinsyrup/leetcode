package com.myleetcode.sqrtx;

class Solution {
    public int mySqrt(int x) {
//         special case
        if(x == 0){
            return 0;
        }
        
//         start must from 1, not 0!
        return binarySearch(1, x, x);
    }

    // 这个是很模板化的二分代码了

    // 这个问题和search insert position问题极其相似，只不过那个问题要找到的是最后一个合法的小值，该问题是找到最后一个合法的大值
    // 这个非递归的二分，找target的时候，我们找的是mid这个值，找到的mid值就是如果有的话，就应该是这个值：
    // 如果mid == target/mid找到，那么返回mid
    // 而如果没有该mid，那么在最后一次loop的时候，start == end == mid
    // 如果mid < target / mid，那么这个mid值就是刚好让mid^2 < target的最大的mid, aka, end
    // 如果mid > target/ mid，那么这个mid值就是刚好让mid^2 > targetd的最小的mid，那么让mid - 1就肯定是刚好的，aka，end
    
    private int binarySearch(int start, int end, int target){
        while(start <= end){
            int mid = start + (end - start)/2;
            if(mid == target / mid){
                return mid;
            }else if(mid  < target / mid){
                start = mid +1;
            }else{
                end = mid -1;
            }
        }
        
        return end;
    }
}