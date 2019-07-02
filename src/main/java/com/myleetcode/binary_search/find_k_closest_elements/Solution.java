package com.myleetcode.binary_search.find_k_closest_elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        return findClosestElementsByBinarySearch(arr, k, x);
        // return findClosestElementsBySort(arr, k, x); // just for reference, not good
    }

    /*
    出错点：
    1 审题: 忘记output需要保持顺序
    2 思路: 最初设想BS得到不小于x的elem的idx之后，需要和idx-1进行比较确认真正的closest idx
    3 思路: 最初，双指针扩展范围同时将选中的内容add到ret，最后sort ret；这样的话复杂度高，而且如果要sort那么可以不用BS，直接按照abs dis来sort然后取前k个值. 正确思路应该是用双指针来记录sublist的范围，最后直接将(leftP,rightP)的内容顺序加入ret即可
    3 思路: 在leftP和rightP扩展的过程中, 从real center idx开始，令leftP为idx, rightP为leftP+1，如果leftP elem dist to x <= rightP则减小leftP，反之增加rightP，如此leftP和rightP之间的内容即为所求
    */

    // intuition:
    // first we could try to find the elem that is closest to x, to do this, we could use BS. since if has a tie, the smaller elem is prefered, so we actually need to search the first elem that is not smaller than x
    // use the idx as central point, we use two mor pointers named firstP and lastP, we loop k times, every time we compare firstP elem and lastP elem, choose the closer one to the ret and update the corresponding pointer
    // TC: O(min(logN + K)), N is the length of arr, K is the k
    // SC: O(1)
    private List<Integer> findClosestElementsByBinarySearch(int[] arr, int k, int x){
        List<Integer> ret = new ArrayList<>();

        if(arr == null || arr.length == 0){
            return ret;
        }

        int idx = binarySearch(arr, x);
        // get the real closest elem idx
        if(idx > 0){
            if(Math.abs(arr[idx - 1] - x) < Math.abs(arr[idx] - x)){
                idx = idx - 1;
            }
        }
        int leftP = idx;
        int rightP = leftP + 1;
        for(int i = 0; i < k; i++){
            // since k is always valid, so we dont consider the situation that both leftP and rightP are invalid
            // if leftP outofboundary
            if(leftP < 0){
                rightP++;
                continue;
            }
            // if rightP outofboundary
            if(rightP >= arr.length){
                leftP--;
                continue;
            }

            // if no one is outof boundary, choose the closer one
            if(Math.abs(arr[leftP] - x) <= Math.abs(arr[rightP] - x)){// prefer left elem
                leftP--;
            }else{
                rightP++;
            }
        }

        for(int i = leftP + 1; i <= rightP - 1; i++){
            ret.add(arr[i]);
        }

        return ret;
    }

    // remember the smaller elements are prefered if get a tie, so we actually to search the first elem that is not smaller than target
    private int binarySearch(int[] arr, int target){
        int start = 0;
        int end = arr.length - 1;
        while(start <= end){
            int mid = start + (end - start) / 2;

            if(arr[mid] < target){
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }

        return start;
    }

    // for reference: the sol of sort
    // TC: O(min(NlogN, KlogK)) => O(NlogN)
    // SC: O(1)
    private List<Integer> findClosestElementsBySort(int[] arr, int k, int x) {
        List<Integer> list = new ArrayList<>();
        for(int num: arr){
            list.add(num);
        }

        Collections.sort(list, (a, b) -> a == b ? a - b : Math.abs(a-x) - Math.abs(b-x));

        list = list.subList(0, k);

        Collections.sort(list);

        return list;
    }
}
