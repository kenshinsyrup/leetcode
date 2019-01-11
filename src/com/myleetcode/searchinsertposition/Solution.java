package com.myleetcode.searchinsertposition;

class Solution {
    public int searchInsert(int[] nums, int target) {
        // special case
        if(nums == null){
            return -1;
        }
        
        int len = nums.length;
        
       return binarySearch(nums,0, len - 1, target);
    }
    
    // 这个非递归的二分，找target的时候，找到的位置就是target“所在”的位置：
    // 如果有该target，那么这个位置就是target的位置；
    // 如果没有target，那么如果插入的话，就在这个位置～

   // try to find target, if find target, return its index, which should be mid
    // if not find, then we know when the search is in its last loop: start == end == mid
    // if nums[mid] < target, then should be inserted at mid + 1(插入到mid后面也就是mid+1，刚好在最后一次循环的时候start已经是mid+1), aka, start
    // if nums[mid] > target, then should be inserted at mid(应该是插入到mid前面，但插入后就index还是mid，超过第二名还是第二名的故事), aka, start 
    private int binarySearch(int[] nums, int start,int end, int target){
        while(start <= end){
            int mid = start + (end - start)/2;
            if(nums[mid] == target){
                return mid;
            }else if(nums[mid] < target){
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }
        
        return start;
    }
            
}