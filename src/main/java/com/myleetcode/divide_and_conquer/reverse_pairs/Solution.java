package com.myleetcode.divide_and_conquer.reverse_pairs;

class Solution {
    public int reversePairs(int[] nums) {
        return reversePairsByDivideAndConquer(nums);
    }

    // Actually, if we use the Divide and Conquer to solve this like the Counting Iversion problem, we will get a very long RT though the theorical RT is O(N*logN)
    // there's a O(N*logN) RT solution with the BIT, although the theorical RT is same, but it's much efficient to pass the LC: https://leetcode.com/problems/reverse-pairs/discuss/97268/General-principles-behind-problems-similar-to-%22Reverse-Pairs%22

    // intuition: Divide and Conquer problem, similar with Counting Inversion problem
    // TC: O(N*logN)
    // SC: O(N)
    // we have a func called countAndSort, in the func, we assume it at last return the # of the reverse pairs in the nums[start, end], and the nums[start, end] are sorted. then in the func's start, we split the input array to two parts by its middle, then for left and right part we use the countAndSort to recurse, the countAndSort func at last, will traverse and count reverse pairs in current array, and then merge the two sorted parts, then return the count
    private int reversePairsByDivideAndConquer(int[] nums){
        if(nums == null || nums.length <= 1){
            return 0;
        }

        return countAndSort(nums, 0, nums.length - 1);
    }

    private int countAndSort(int[] nums, int start, int end){
        // base case
        if(start >= end){
            return 0;
        }

        // get left and right parts' count
        int mid = start + (end - start) / 2;
        int leftRet = countAndSort(nums, start, mid);
        int rightRet = countAndSort(nums, mid + 1, end);

        // since we have known the reverse pairs # of left part and right part, and now the left part array and right part array are sorted, then we could cross-count the reverse pairs in the nums[start, end]
        int count = leftRet + rightRet;
        for(int i = start; i <= mid; i++){
            int j = mid + 1;
            while(j <= end){
                if((long)nums[i] > 2 * (long)nums[j]){ // long to avoid the overflow error, if 2*nums[j] overflow int, count will be wrong
                    count++;
                    j++;
                }else{
                    break;
                }
            }
        }

        // now we have now the count of nums[start, end], so we merge-sort it and return our ans
        int[] mergeNums = new int[end - start + 1];
        mergeSort(mergeNums, nums, start, mid, mid + 1, end);

        // Stupid LC
        // Thing is, if we write like this, we will get a TLE, though it has the same RT whit the System.arraycopy
        // for(int i = 0; i < mergeNums.length; i++){
        //     nums[start + i] = mergeNums[i];
        // }
        // But, if we write like this, we could complete the LC at about 1400ms
        System.arraycopy(mergeNums, 0, nums, start, mergeNums.length);

        return count;
    }

    private void mergeSort(int[] mergeNums, int[] nums, int leftStart, int leftEnd, int rightStart, int rightEnd){
        int i = 0;
        while(leftStart <= leftEnd && rightStart <= rightEnd){
            if(nums[leftStart] <= nums[rightStart]){
                mergeNums[i] = nums[leftStart];

                leftStart++;
            }else{
                mergeNums[i] = nums[rightStart];

                rightStart++;
            }

            i++;
        }
        while(leftStart <= leftEnd){
            mergeNums[i] = nums[leftStart];

            leftStart++;
            i++;
        }
        while(rightStart<=rightEnd){
            mergeNums[i] = nums[rightStart];

            rightStart++;
            i++;
        }
    }
}
