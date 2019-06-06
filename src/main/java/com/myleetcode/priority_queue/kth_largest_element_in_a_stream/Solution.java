package com.myleetcode.priority_queue.kth_largest_element_in_a_stream;

import java.util.Comparator;
import java.util.PriorityQueue;

class KthLargest {

    // TC: O(logN), N is the size of nums
    // SC: O(K)
    // intuition: use MinHeap with size K to store elements. when add is called, we try to insert the val to the MinHeap if it's larger than the first elem of the PQ and return the Max(first elem) in the MinHeap
    // !!! dont use MaxHeap, here we need the kth largest elem, ie the smallest elem in the k elems we stored. we should use O(1) time to get it, so a k size MinHeap is the answer
    PriorityQueue<Integer> numPQ;
    int size;
    public KthLargest(int k, int[] nums) {
        if(k <= 0){
            return;
        }

        this.numPQ = new PriorityQueue<>(new Comparator<Integer>(){
            public int compare(Integer num1, Integer num2){
                return num1 - num2;
            }
        });
        this.size = k;

        for(int num: nums){
            // keep only K elems is enough
            if(this.numPQ.size() < k){
                this.numPQ.offer(num);
            }else{
                if(num > this.numPQ.peek()){
                    this.numPQ.poll();
                    this.numPQ.offer(num);
                }
            }
        }
    }

    // TC: log(K)
    public int add(int val) {
        // special case, if the PQ has not enough elems, should talk with interwiewer about this
        if(this.numPQ.size() < this.size){
            this.numPQ.offer(val);
        }else{
            if(val > this.numPQ.peek()){
                this.numPQ.poll();
                this.numPQ.offer(val);
            }
        }

        return this.numPQ.peek();
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */
