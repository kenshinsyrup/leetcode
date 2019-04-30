package com.myleetcode.design.implement_queue_susing_stacks;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    class MyQueue {

        /** Initialize your data structure here. */

        /** use two stacks to implement a queue, every time push, we push all elems from stackFir to stackSec then push new elem then push all elems back, so the stackFir will have the same order as a queue*/

        Deque<Integer> stackQueue;
        Deque<Integer> stackHelper;

        public MyQueue() {
            stackQueue = new ArrayDeque<>();
            stackHelper = new ArrayDeque<>();
        }

        /** Push element x to the back of queue. */
        public void push(int x) {
            while(!stackQueue.isEmpty()){
                stackHelper.push(stackQueue.pop());
            }

            stackHelper.push(x);

            while(!stackHelper.isEmpty()){
                stackQueue.push(stackHelper.pop());
            }
        }

        /** Removes the element from in front of queue and returns that element. */
        public int pop() {
            return stackQueue.pop();
        }

        /** Get the front element. */
        public int peek() {
            return stackQueue.peek();
        }

        /** Returns whether the queue is empty. */
        public boolean empty() {
            return stackQueue.isEmpty();
        }
    }

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
}
