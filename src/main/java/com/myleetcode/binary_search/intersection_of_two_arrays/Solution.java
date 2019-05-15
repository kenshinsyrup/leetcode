package com.myleetcode.binary_search.intersection_of_two_arrays;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        // return intersectionByTwoSet(nums1, nums2);
        return intersectionByBS(nums1, nums2);
    }

    // intuition: there are two approaches
    // 1st, we could use two Set to solve this, we use one Set1 store all nums1, cost O(N), then we use another Set2 store the num that is in nums2 and also contained by Set1, cost O(M). Set2 is the ans, TC is O(min(N,M))
    // 2nd, we could use a Set as the ret, then we traverse nums1, for num in nums1, we do BS in nums2, if find, add to Set. Since we want to do BS on nums2, so we first sort it cost O(M*logM), so total TC is O(min(N*logM, M*logM))
    // 1st TC is better than 2nd, but to the SC, in normal case, 2nd use less space because we dont nedd add all num in nums1 into a Set at first. The meaning is, 1st solution will cost at least O(N), while 2nd is not

    // TC: O(min(N*logM, M*logM))
    // SC: O(min(N, M))
    private int[] intersectionByBS(int[] nums1, int[] nums2){
        if(nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0){
            return new int[0];
        }

        Set<Integer> retSet = new HashSet<>();

        // sort nums2
        Arrays.sort(nums2);

        for(int num: nums1){
            if(bs(nums2, num)){
                retSet.add(num);
            }
        }

        int[] ret = new int[retSet.size()];
        int i = 0;
        for(int num: retSet){
            ret[i] = num;
            i++;
        }

        return ret;
    }

    private boolean bs(int[] nums, int target){
        int start = 0;
        int end = nums.length -1;
        while(start <= end){
            int mid = start + (end - start) / 2;
            if(nums[mid] == target){
                return true;
            }else if(nums[mid] < target){
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }
        return false;
    }


    // TC: O(min(N,M))
    // SC: O(min(N, M))
    private int[] intersectionByTwoSet(int[] nums1, int[] nums2){
        if(nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0){
            return new int[0];
        }

        Set<Integer> set1 = new HashSet<>();
        for(int num: nums1){
            set1.add(num);
        }

        Set<Integer> set2 = new HashSet<>();

        for(int i = 0; i < nums2.length; i++){
            if(set1.contains(nums2[i])){
                set2.add(nums2[i]);
            }
        }

        int[] ret = new int[set2.size()];
        int i = 0;
        for(int num: set2){
            ret[i] = num;
            i++;
        }

        return ret;
    }
}
