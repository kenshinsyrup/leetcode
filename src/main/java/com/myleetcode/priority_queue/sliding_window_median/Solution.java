package com.myleetcode.priority_queue.sliding_window_median;

import java.util.Comparator;
import java.util.PriorityQueue;

class Solution {
    public double[] medianSlidingWindow(int[] nums, int k) {
        return medianSlidingWindowByTwoHeaps(nums, k);
    }

    /*
    出错点:
    1 左右两个部分的PQ，左边要取max，右边要取min，所以左边是leftMaxHeap, 右边是rightMinHeap. Java中默认的PQ是minHeap
    2 在comparator中，使用compareTo而不是num2-num1来防止越界导致比较失败，如当num2为Integer.MAX_VALUE且num1为Integer.MIN_VALUE时
    3 getMedian中，使用long来避免越界问题导致计算结果不正确
    */

    // naive solution is caculate evey window's median seperately. Get a median in a window cost O(KlgoK), so totaly cost is O(NKlogK)

    // same problem: 295. Find Median from Data Stream
    // if window len is odd then median is the mid num; if window len is even then median is the average of last elem of first part and the first elem of second part(here we mean the windwo is sorted)
    // but sort a window cost O(KlogK). We know every step when the window move, we only get one new elem and need only get rid of one old elem, and, we only need one mid elem(odd len) or two mid elems(even len) to get the median. So we could use Two Heaps to help us do this. If we could have a leftMaxHep and a rightMinHeap, we could get the max elem in left part and the min elem in right part within O(1) time, and when window moves, we only cost O(logK) time to offer a new elem into Heap. By this way, we could reach O(NlogK) TC
    // TC: O(N * logK)
    // SC: O(K)
    private double[] medianSlidingWindowByTwoHeaps(int[] nums, int k){
        if(nums == null || nums.length == 0 || k <= 0){
            return new double[0];
        }

        // 0 get the num of windows, if <=0 then not valid input
        int len = nums.length;
        int numOfWindow = len - k + 1;
        if(numOfWindow <= 0){
            return new double[0];
        }
        double[] ret = new double[numOfWindow];

        // 1 leftMaxHeap and rightMinHeap
        PriorityQueue<Integer> leftMaxHeap = new PriorityQueue<>(new Comparator<Integer>(){
            public int compare(Integer num1, Integer num2){
                return num2.compareTo(num1);
            }
        });
        PriorityQueue<Integer> rightMinHeap = new PriorityQueue<>();

        // 2 get median
        for(int i = 0; i < len; i++){
            // add cur num into correct pos
            addNum(leftMaxHeap, rightMinHeap, nums[i]);

            // if we have total k elems in window, get the median and then shrink one step
            if(leftMaxHeap.size() + rightMinHeap.size() == k){
                int leftP = i + 1 - k;
                ret[leftP] = getMedian(leftMaxHeap, rightMinHeap, k);

                removeNum(leftMaxHeap, rightMinHeap, nums[leftP], k);
            }
        }

        return ret;

    }

    // try to remove num from left part, if not exist, remove from right
    // O(logK)
    private void removeNum(PriorityQueue<Integer> leftMaxHeap, PriorityQueue<Integer> rightMinHeap, int num, int k){
        if(leftMaxHeap.remove(num)){
            return;
        }
        rightMinHeap.remove(num);
    }

    // add to the corresponding part
    // O(logK)
    private void addNum(PriorityQueue<Integer> leftMaxHeap, PriorityQueue<Integer> rightMinHeap, int num){
        if(leftMaxHeap.size() <= rightMinHeap.size()){
            rightMinHeap.offer(num);
            leftMaxHeap.offer(rightMinHeap.poll());
        }else{
            leftMaxHeap.offer(num);
            rightMinHeap.offer(leftMaxHeap.poll());
        }
    }

    // median is the average of two mid elems or the mid elem
    // O(1)
    private double getMedian(PriorityQueue<Integer> leftMaxHeap, PriorityQueue<Integer> rightMinHeap, int k){
        if(k % 2 == 0){
            return ((long)leftMaxHeap.peek() + (long)rightMinHeap.peek()) / 2.0;
        }

        return (long)leftMaxHeap.peek() * 1.0;
    }
}
