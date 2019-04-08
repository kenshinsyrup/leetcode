package com.myleetcode.stack.next_greater_element_i;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        return nextGreaterElementByStack(nums1, nums2);
    }

    // https://www.geeksforgeeks.org/next-greater-element/
    // intuition: we could use one stack, stackNum stores the nums2, then we loop the nums2, for evey curElem, if curElem is greater than the stackNum.peek() then we pop it out and put the (stackNum.pop(), curElem) pair to map, this way we are keeping update every nums's next greater
    private int[] nextGreaterElementByStack(int[] nums1, int[] nums2){
        // special case
        if(nums1 == null || nums1.length == 0){
            return new int[0];
        }
        if(nums2 == null || nums2.length == 0){
            return new int[0];
        }

        // use stack to get every element's first value that is greater than it.
        // we loop the array, curElem compares to stack's top, if curElem is greater, then we pop, the poped value's right greater value is the curElem, so we keep this pair into map. then we push the curElem into the stack to keep loop.
        // at last when the loop end, everything in the stack has no next greater value in its right
        Deque<Integer> nums2Stack = new ArrayDeque<>();
        Map<Integer, Integer> nextGreaterMap = new HashMap<>();
        int[] ret = new int[nums1.length];
        for(int i = 0; i < nums2.length; i++){
            int num = nums2[i];

            // keep the (num, next greater value in its right) pair into the map
            while(!nums2Stack.isEmpty() && num > nums2Stack.peek()){
                nextGreaterMap.put(nums2Stack.pop(), num);
            }

            nums2Stack.push(num);
        }
        // no next greater value at its right
        while(!nums2Stack.isEmpty()){
            nextGreaterMap.put(nums2Stack.pop(), -1);
        }

        // since we have all (num, next greater value in its right) pair in the map, we loop the nums1 to find all rets
        for(int i = 0; i < nums1.length; i++){
            ret[i] = nextGreaterMap.get(nums1[i]);
        }

        return ret;

    }
}
