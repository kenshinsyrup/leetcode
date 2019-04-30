package com.myleetcode.design.implement_stack_using_queues;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    class MyStack {

        /** Initialize your data structure here. */

        // and we also could use two queues to implement a stack but the pop is O(1) and push is O(N), the key is to use the two queue to reverse the push order to make it the same as a stack. solution approach 2
        // and we could use only one queue to optimize this approach.

        Deque<Integer> queueStack;

        public MyStack() {
            queueStack = new ArrayDeque<>();
        }

        // Push element x onto stack.
        public void push(int x) {
            // everytime we want to push elem to stack, we push it to the queueStack and then reverse the order of the queueStack to make it a stack
            int originalSize = queueStack.size();
            queueStack.offer(x);
            // put all original elems to the back of the new elem
            while(originalSize != 0){
                queueStack.offer(queueStack.poll());

                originalSize--;
            }
        }

        public int pop() {
            return queueStack.poll();
        }

        // Get the top element.
        public int top() {
            return queueStack.peek();
        }

        // Returns whether the stack is empty.
        public boolean empty() {
            return queueStack.isEmpty();
        }



 /*
    // pop O(N), push O(1)
    // we could use two queue implement a stack, use two stack implement a queue
    Deque<Integer> queueStack;
    Deque<Integer> reverseStack;
    int topNum;

    public MyStack() {
        queueStack = new ArrayDeque<>();
        reverseStack = new ArrayDeque<>();
    }

    // Push element x onto stack.
    public void push(int x) {
        // push all elems to the reverseStack
        reverseStack.offer(x);

        topNum = x;
    }

    // Removes the element on top of the stack and returns that element.
    public int pop() {
        // poll all elems from reverseStack to queueStack except the last elem, we pop this elem out
        while(reverseStack.size() > 1){
            topNum = reverseStack.poll();
            queueStack.offer(topNum);
        }

        int ret = reverseStack.poll();

        Deque<Integer> temp = reverseStack;
        reverseStack = queueStack;
        queueStack = temp;

        return ret;
    }

    // Get the top element.
    public int top() {
        return topNum;
    }

     // Returns whether the stack is empty.
    public boolean empty() {
        return reverseStack.isEmpty();
    }*/
    }

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
}
