package com.myleetcode.binary_search.intersection_of_two_arrays_ii;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {
        return intersectByTwoPointers(nums1, nums2);
    }

    // intuition: similar with 349. Intersection of Two Arrays. This problem says need all intersection nums, so we use a List instead of Set to store intermediat reesult
    // there are 3 approaches(if nums1 length is less than nums2's):
    // 1st, use a Set store nums1, then traverse nums2, if find, store the find num into a List. at last convert the List to Array
    // 2nd, sort nums2, traverse nums1 and do BS in nums2, if find, store to List
    // 3rd, sort nums1 and nums2, use Two Pointers do a Scan on these two Array, if num1 larger, move Pointer2, if num2 larger, move Pointer1, if equals, store to List
    // because of the follow up, we use the 3rd approach

    // https://leetcode.com/problems/intersection-of-two-arrays-ii/discuss/161251/Java-sort-and-scan-(for-follow-ups)
    private int[] intersectByTwoPointers(int[] nums1, int[] nums2){
        if(nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0){
            return new int[0];
        }

        List<Integer> retList = new ArrayList<>();

        // sort first
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        int pointer1 = 0;
        int pointer2 = 0;
        while(pointer1 < nums1.length && pointer2 < nums2.length){
            if(nums1[pointer1] > nums2[pointer2]){
                pointer2++;
                continue;
            }

            if(nums1[pointer1] < nums2[pointer2]){
                pointer1++;
                continue;
            }

            retList.add(nums1[pointer1]);
            pointer1++;
            pointer2++;
        }

        int[] ret = new int[retList.size()];
        int i = 0;
        for(int num: retList){
            ret[i] = num;
            i++;
        }

        return ret;
    }

    // follow up:
    // https://leetcode.com/problems/intersection-of-two-arrays-ii/discuss/161251/Java-sort-and-scan-(for-follow-ups)

    // for the 3rd follow up:https://leetcode.com/problems/intersection-of-two-arrays-ii/discuss/82243/Solution-to-3rd-follow-up-question
    /*
    What if elements of nums2 are stored on disk, and the memory is
limited such that you cannot load all elements into the memory at
once?

If only nums2 cannot fit in memory, put all elements of nums1 into a HashMap, read chunks of array that fit into the memory, and record the intersections.

If both nums1 and nums2 are so huge that neither fit into the memory, sort them individually (external sort), then read 2 elements from each array at a time in memory, record intersections.
    */
}
