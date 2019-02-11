package com.myleetcode.search_in_rotated_sorted_array;

class Solution {
    public int search(int[] nums, int target) {
        // special case
        if(nums == null || nums.length == 0){
            return -1;
        }
        
        if(nums.length == 1){
            if(nums[nums.length - 1] == target){
                return 0;
            }else{
                return -1;
            }
        }
        
        // 找到Sorted Array中的最小值就是pivot， 得到其index
        int pivotIndex = findSmallestElement(nums, 0, nums.length - 1);
      
//         如果该值与target相同则是该index
        if(nums[pivotIndex] == target){
            return pivotIndex;
        }
        
//         如果pivot与target不同，则分情况讨论：
//         情况1 是pivot是第一个值，也就是是一个正常的sorted array
        if(pivotIndex == 0){
            return findTarget(nums, 0, nums.length - 1, target);
        }
        
//        情况2 是target比第一个值小，说明target在pivot右边（包括pivot自己因为pivot是整个数组最小值所以是右边子数组的起点）。因为pivot左右两边的子array都是sorted的，这里是升序，所以target如果小于整个数组第一个值，那么肯定小于pivot左边子数组的所有值。
        if(target < nums[0]){
            return findTarget(nums, pivotIndex, nums.length - 1, target);
        }
        
//         情况3 target在pivot的左边
        return findTarget(nums, 0, pivotIndex - 1, target);
    }
    
//     查找target
    private int findTarget(int[] nums, int start, int end, int target){
//         正常的二分查找代码
        while(start <= end){
            int mid = start + (end - start) / 2;
            if(nums[mid] == target){
                return mid;
            }else if(nums[mid] < target){
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }
        
        return -1;
    }
    
    // find the smallest element
    private int findSmallestElement(int[] nums, int start, int end){
        // 要考虑这种特殊情况，整个sorted array并没有rotate。如果不考虑这个情况直接进入while循环会出错
        if(nums[start] < nums[end]){
            return 0;
        }
        
//         确定sorted array存在rotate，所以一定存在一个最小值在整个数组的非首位，我们找到他
        while(start <= end){
            int mid = start + (end - start) / 2;
            if(nums[mid] > nums[mid + 1]){
                return mid + 1;
            }else {
                if(nums[mid] < nums[start]){
                    end = mid - 1;
                }else{
                    start = mid + 1;
                }
            }
        }
        
        return 0;
    }
    
    /*
    这是最初的想法，分析双线段是没问题的，在具体实现上不应直接这么简单的分情况
    private int binarySearch(int[] nums, int start, int end, int target){
        // 双线段，a部分线段为升序，b部分线段为升序，但是a部分最小的值也大于b部分最大的值
        // 所以mid和target，配合start，end，有四种情况：
        while(start < end){
            int mid = start + (end - start) / 2;
            if(nums[mid] == target){
                return mid;
            }else if(nums[mid] > target && nums[start] < target){
                // start -Ascend-> mid, include target
                end = mid -1;
            }else if(nums[mid] < target && nums[end] > target){
                // mid -Descend-> end, includes target
                start = mid + 1;
            }else if(nums[mid] < target && nums[start] > target){
                // start -> mid , no target
                start = mid + 1;
            }else if(nums[mid] > target && nums[end] < target){
                // mid -> end, no target
                end = mid -1;
            }else{
                  if(nums[start] == target){
            return start;
        }
        if(nums[end] == target){
            return end;
        }
            }
        }
        
      
        
        return -1;
    }
    */
}