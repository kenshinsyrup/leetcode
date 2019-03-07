package com.myleetcode.array.merge_sorted_array;

class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {

        // special case
        if(nums1 == null || nums2 == null || m  < 0 || n < 0){
            return;
        }


        // mergeByMergeSort(nums1, m, nums2, n);

        mergeByMergeSortII(nums1, m, nums2, n);
    }

    // 基于I，我们可以尝试不使用额外的空间。题目给了清晰的m，所以我们可以直接从nums1的末尾开始对nums1中的无效信息进行覆盖，也就是把mergesort中的ascending改成descending
    // TC: O(n + m)
    // SC: O(1)
    private void mergeByMergeSortII(int[] nums1, int m, int[] nums2, int n){
        int i = m - 1;
        int j = n - 1;
        int k = nums1.length - 1;

        int num1 = 0;
        int num2 = 0;
        while(i >=0 && j >= 0){
            num1 = nums1[i];
            num2 = nums2[j];
            if(num1 > num2){
                nums1[k] = num1;
                i--;
            }else{
                nums1[k] = num2;
                j--;
            }
            k--;
        }

        // 剩余信息
        while(i >= 0){
            nums1[k] = nums1[i];
            i--;
            k--;
        }
        while(j >= 0){
            nums1[k] = nums2[j];
            j--;
            k--;
        }

    }

    // intuition: brute force two nested for loop, outer loop for nums2, inner loop for nums1, insert num in nums2 to nums1 after compare.
    // binary-search nums1.
    // but this seems not right, because when consider insert at i, we must move all elements in nums1 after i afterward by 1, this is costy.
    //
    // then, we find this is acually very similiar with the merge stage or merge-sort algorithm, we need additional space but we could merge faster
    // TC: O(n + m)
    // SC: O(n + m)
    private void mergeByMergeSort(int[] nums1, int m, int[] nums2, int n){
        int i = 0;
        int j = 0;
        int num1 = 0;
        int num2 = 0;
        int[] ret = new int[nums1.length];
        int k = 0;
        while(i < m && j < n){
            num1 = nums1[i];
            num2 = nums2[j];
            if(num1 < num2){
                i++;
                ret[k] = num1;
            }else{
                j++;
                ret[k] = num2;
            }
            k++;
        }

        // check 剩余的内容
        while(i < m){
            ret[k] = nums1[i];
            i++;
            k++;
        }
        while(j < n){
            ret[k] = nums2[j];
            j++;
            k++;
        }

        // move ret to nums1
        // nums1 = ret; this is not correct!!!
        for(i = 0; i < ret.length; i++){
            nums1[i] = ret[i];
        }
    }

}
