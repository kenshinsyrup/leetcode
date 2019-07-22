package com.myleetcode.priority_queue.find_median_from_data_stream;

import java.util.Comparator;
import java.util.PriorityQueue;

class MedianFinder {

    // solution 2: Two Heaps
    // TC: O(logN)
    // SC: O(N)
    // the thought is: assume we have a Sorted List, then, at the median pos, we could split the List to two parts, less or equals to median part, larger than median part
    // since we want to caculate the new median after add a new num, so we only need to access at most two num from those two parts(one in one part), the largest one in the left part and the smallest one in the right part(attention, we say, at most)
    // then, we could use a MaxHeap for the left part, and MinHeap for the right part
    // add: offer num to coresponding part, if left<=right then left, otherwise right. during this, we need keep the whole in order, so if we want to offer to left, we first offer the num to right then get the min of right and offer it to the left, vise verse.
    // mdeian: according to the total # in left and right part. if odd, return the top of Left MaxHeap; if even, return the average of the top of Left MaxHeap and the top of Right MinHeap

    PriorityQueue<Integer> leftPQ;
    PriorityQueue<Integer> rightPQ;

    /** initialize your data structure here. */
    public MedianFinder() {
        // MaxHeap
        leftPQ = new PriorityQueue<>(new Comparator<Integer>(){
            public int compare(Integer n1, Integer n2){
                return n2 - n1;
            }
        });

        // MinHeap is default
        rightPQ = new PriorityQueue<>();
    }

    public void addNum(int num) {
        if(leftPQ.size() <= rightPQ.size()){
            rightPQ.offer(num);
            leftPQ.offer(rightPQ.poll());
        }else{
            leftPQ.offer(num);
            rightPQ.offer(leftPQ.poll());
        }
    }

    // TC: O(1)
    // SC: O(1)
    public double findMedian() {
        int len = leftPQ.size() + rightPQ.size();

        if(len % 2 == 0){
            return (leftPQ.peek() + rightPQ.peek()) / 2.0;
        }
        return leftPQ.peek() * 1.0;
    }


//     // solution 1: Insertion Sort
//     // TC: O(N)
//     // SC: O(N)
//     // add: use BS to find the pos to put the new num, then shift all num back by 1 in original list, then put the new num in
//     // median: odd is the pos at len/2, even is the average of len/2 and len/2-1

//     List<Integer> numList;

//     /** initialize your data structure here. */
//     public MedianFinder() {
//         this.numList = new ArrayList<>();
//     }

//     // TC: O(N)
//     // SC: O(N)
//     public void addNum(int num) {
//         int len = this.numList.size();

//         int start = 0;
//         int end = len - 1;
//         while(start <= end){
//             int mid = start + ((end - start) >> 1);

//             // we need the first No Less Than num pos
//             if(this.numList.get(mid) < num){
//                 start = mid + 1;
//             }else{
//                 end = mid - 1;
//             }
//         }

//         // add the num to the First No Less Than num Pos, the nums in original List will be shifted back by 1, cost O(N)
//         this.numList.add(start, num);
//     }

//     // TC: O(1)
//     // SC: O(1)
//     public double findMedian() {
//         int len = this.numList.size();

//         if(len % 2 == 0){
//             return (this.numList.get(len / 2) + this.numList.get(len / 2 - 1)) / 2.0;
//         }
//         return this.numList.get(len / 2) * 1.0;
//     }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
