package com.myleetcode.binary_search.find_peak_element;

class Solution {
    public int findPeakElement(int[] nums) {
        // https://leetcode.com/problems/find-peak-element/discuss/50239/Java-solution-and-explanation-using-invariants
        // return findPeakElementByBinarySearch(nums);

        return findPeakByBS(nums);
    }

    // TC: O(N)
    // SC: O(1)
    // since we need only find any peak and there must exist at least one, we could just do a normal BS
    private int findPeakByBS(int[] nums){
        // special case
        if(nums == null || nums.length == 0){
            return -1;
        }

        int start = 0;
        int end = nums.length - 1;
        while(start <= end){
            int mid = (end - start ) / 2 + start;
            // check mid < nums.length-1 avoid out of boundary
            if(mid < nums.length - 1 && nums[mid] < nums[mid + 1]){
                start = mid + 1;
            }else{
                end = mid - 1; // ignore right half.
            }
        }

        return start;
    }

    // should be in logarithmic complexity, so it's binary search
    // we dont need to find all the peaks, just need find any one of peaks
    // if we use the conditions like this block, must guarantee the nums has at least 3 elements, otherwise will get outOfBoundary exception.
    private int findPeakElementByBinarySearch(int[] nums){
        // https://leetcode.com/problems/find-peak-element/discuss/50236/O(logN)-Solution-JavaCode
        int start = 0;
        int end = nums.length - 1;
        int mid = 0;
        while(start <= end){
            // 重要!!!特殊的两个情况可以单独处理，我们认为没有mid的情况（mid和start或end会重合）.特殊处理这两种情况可以避免mid+1-1越界的问题
            if(start == end){
                return start;
            }
            if(start == end - 1){
                // start源自上坡，所以如果比右边的大，那么就是个peak
                if(nums[start] > nums[end]){
                    return start;
                }else{// 同理，end源自下坡，如果比左边的大，说明是peak
                    return end;
                }
            }

            // 正常mid
            mid = (end - start) / 2 + start;

            // 折线上有四个情况，既然nums的0前面和length-1的后面是负无穷，那么肯定nums从一开始(至少第一个元素)是上坡，在最后(至少最后一个元素)是下坡。
            if(nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1]){//peak
                return mid; // remeber to minus 1, because we extend the length of nums.
            }else if(nums[mid] > nums[mid - 1] && nums[mid] < nums[mid + 1]){// 上坡，peak在该点右边
                start = mid + 1;
            }else if(nums[mid] < nums[mid - 1] && nums[mid] > nums[mid + 1]){ // 下坡，peak在改点左边
                end = mid - 1;
            }else { // valley波谷, nums[mid] < nums[mid - 1] && nums[mid] < nums[mid + 1]
                start = mid + 1; // 既然在波谷，那么向前还是向后移动都可以找到一个peak
            }
        }

        return start;
    }
}
