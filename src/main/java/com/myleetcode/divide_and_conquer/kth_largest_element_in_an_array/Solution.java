package com.myleetcode.divide_and_conquer.kth_largest_element_in_an_array;

class Solution {
    public int findKthLargest(int[] nums, int k) {
        // 和K-closest应该是同样的思路
        // TODO: 理论上来说，973. K Closest Points to Origin，这个题目和这个很像，应该可以使用同样的代码。现在二者使用的还是不同的。
        // 不是，因为要找的是严格的第K个大值有顺序要求，而不是前K个大值的乱序。但是，还是可以用同样的思路的。

        // 注意，这道题用quick sort的方法做属于follow up。
        // 最快的解决方式应该是： 方法1 排序数组，取n-k； 方法2 priority queue。具体见：https://leetcode.com/problems/kth-largest-element-in-an-array/discuss/60294/Solution-explained 
// 另外，还有非递归的写法https://leetcode.com/problems/kth-largest-element-in-an-array/discuss/60300/Java-Quick-Select

//         找第K个最大值，这个题目，我们只要保证[0, k]这个范围内是排序好的就行。至于[k+1, ...]之中的全部小于[0,k]这个范围值的内容，我们不关心顺序。这样就可以减少排序的操作量。
//         总结就是：使用quick sort的思路，使用partition方法，但是我们只排序最大的k个数字而不是全部。
        
        // special case
        if(nums == null || nums.length == 0){
            return 0;
        }
        
         findFirstKLargest(nums, 0, nums.length - 1, k - 1);
        
        return nums[k-1];
        
    }
    
    private void findFirstKLargest(int[] nums, int start, int end, int k){
        // // exit, base case
        if(start >= end){
            return;
        }
        
        // 以下开始是quick sort， partition实现，的基础操作。
        // choose a pivot， 使用中位数方法来避免纯随机或者纯固定方式导致的潜在的性能问题，从而尽大可能的避免quick sort最差的情况
        int mdeianIndex = findMedianIndex(nums, start,start + (end - start)/2, end);
        
        // 让pivot去end
        swap(nums, mdeianIndex, end);
        
        // partition, left tracks the first value that we dont know if it's bigger than the pivot. Values in the left of left are all bigger than pivot.
        int left = start;
        // pivot
        int pivot = nums[end];
        // 遍历数组，所有比pivot大的放到left左边
        for(int i = start; i < end; i++){
            if(nums[i] > pivot){
                swap(nums, left, i);
                left++;
            }
        }   
        // 遍历完成。left这个位置左边全都是比pivot大的，left自己一直到end都是小于等于pivot的，把end那里的pivot和left交换，正好达成了left左边都是大于pivot的，left自己是pivot，left右边全是小于pivot的或者小于等于pivot的
        swap(nums, left, end);
        
        // 注意，我们这里用的k，是在主函数里-1的，也就是现在的k是第k个值的index。
        // 检查：1 k和left相同，那么完成了。2 k <left, 那么我们抛弃left右边的内容，继续排序[0, left - 1]。3 k>left,那么我们知道[0,left]都是数组中最大的几个值，不过他们的顺序不重要，因为第k个最大值现在处在[left + 1, end]中，所以我们继续排序右边的内容。
        if(k == left){
            return ;
        }else if(k < left){
             findFirstKLargest(nums, start, left - 1, k);
        }else{
             // 注意，这里千万不要只排序[left + 1, k], 因为left左边和右边其实现在完全是乱序的，虽然右边的都比pivot小，但是并不知道具体的顺序，也就是说如果只排序[left+1, k]，有可能真正的第k个遗漏掉了
             findFirstKLargest(nums, left + 1, end, k);
        }
        
    }
    
    private void swap(int[] A, int b, int c){
        int temp = 0;
        temp = A[b];
        A[b] = A[c];
        A[c] = temp;
    }
    
    private int findMedianIndex(int[] A, int a, int b, int c){
        int temp = 0;
        if(A[a] > A[b]){
            temp = A[a];
            A[a] = A[b];
            A[b] = temp;
        }
        if(A[a] > A[c]){
            temp = A[a];
            A[a] = A[c];
            A[c] = temp;
        }
        if(A[b] > c){
            temp = A[b];
            A[b] = A[c];
            A[c] = temp;
        }
        return b;
    }
}