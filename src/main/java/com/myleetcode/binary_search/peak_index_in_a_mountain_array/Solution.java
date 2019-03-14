package com.myleetcode.binary_search.peak_index_in_a_mountain_array;

class Solution {
    public int peakIndexInMountainArray(int[] A) {

        // special case
        if(A == null || A.length < 3){
            return -1;
        }

        // return peakIndexInMountainArrayByBinarySearch(A);
        // return peakIndexInMountainArrayByBinarySearchOpt(A);

        // return peakIndexInMountainArrayByBinarySearchOptII(A);//dont use this

        return findPeakElementByBinarySearch(A);
    }

    // 根据162. Find Peak Element，我们可以总结出一套更稳健的写法，虽然可能看起来啰嗦，但是比较南出错
    private int findPeakElementByBinarySearch(int[] nums){
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

    // 标准的binary search写法
    // Binary Search, 同样的，找第一个A[i]>A[i+1]
    // 这样的写法下，当while退出前最后一个循环是start==end==mid，所以while退出后，有两种情况，要么start加了1，要么end减了1，最后一个被检查的元素的位置是mid指向的，而最后一个不符合要求的是start或者end指向的，至于哪个是需要的看逻辑.
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

    // 注意，这样写很可能是有问题的，在while循环的最后一次是start==end，此时mid==start==end，那么因为没有+1-1的前进操作，所以在不满足返回mid的条件的时候，其他的条件并不会让start和end差生变化，所以会死循环产生。不要这样写，要写成start<=end, 内部start = mid + 1; end = end - 1这样的类似情况。
    // 基本与peakIndexInMountainArrayByBinarySearchOpt相同，但是这种写法更清晰易懂，比较喜欢，不用特意去让mid+1-1
    private int peakIndexInMountainArrayByBinarySearchOptII(int[] A){
        int start = 0;
        int end = A.length - 1;
        int mid = 0;
        while(start <= end){
            mid = (end - start ) / 2 + start;
            if(A[mid] > A[mid - 1] && A[mid] < A[mid + 1]){ // 上坡
                start = mid;
            }else if(A[mid] < A[mid - 1] && A[mid] > A[mid + 1]){ // 下坡
                end = mid; // ignore right half.
            }else if(A[mid] > A[mid - 1] && A[mid] > A[mid + 1]){ // peak
                return mid;
            }
        }

        return start;
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