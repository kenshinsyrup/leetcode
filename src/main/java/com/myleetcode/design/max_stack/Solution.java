package com.myleetcode.design.max_stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.TreeMap;

public class Solution{

    class MaxStack {

        /** initialize your data structure here. */

        // and, there's a two stacks way to solve this: https://leetcode.com/problems/max-stack/discuss/108938/Java-AC-solution
        // also there's a way to reduce the popMax to O(logN) too with DoubleLinkedList and TreeMap in solution.

        // intuition: we could use a normal Stack as the main DS to do the push, pop, top operations. then we use a Map to record the num of every elem and use the elem as the key. for peekMax we first check the max in Map keys, for popMax we also need then reduce its num, then pop this value out of Stack(for eg we could use another Stack to receive the poped elems until the needed max elem, then poped these elems back). This will cost: push O(1), pop O(1), top O(1), peekMax O(N), popMax O(N), SC is O(N)
        // actually, for this type that we need order in the Map(because we are trying to get max key) we could use TreeMap. This way, push O(logN), pop O(logN), top O(1), peekMax O(1), popMax O(N), SC O(N)
        Deque<Integer> valStack;
        TreeMap<Integer, Integer> valNumMap;
        public MaxStack() {
            valStack = new ArrayDeque<>();
            valNumMap = new TreeMap<>();
        }

        public void push(int x) {
            valStack.push(x);
            if(valNumMap.containsKey(x)){
                valNumMap.put(x, valNumMap.get(x) + 1);
            }else{
                valNumMap.put(x, 1);
            }
        }

        public int pop() {
            int val = valStack.pop();

            // reduce num, if 0, remove
            valNumMap.put(val, valNumMap.get(val) - 1);
            if(valNumMap.get(val) == 0){
                valNumMap.remove(val);
            }

            return val;
        }

        public int top() {
            return valStack.peek();
        }

        public int peekMax() {
            return valNumMap.lastKey();
        }

        public int popMax() {
            int val = peekMax();

            // pop out
            Deque<Integer> buffer = new ArrayDeque<>();
            while(!valStack.isEmpty()){
                if(val == valStack.peek()){
                    break;
                }
                buffer.push(valStack.pop());
            }

            valStack.pop();
            while(!buffer.isEmpty()){
                valStack.push(buffer.pop());
            }

            valNumMap.put(val, valNumMap.get(val) - 1);
            if(valNumMap.get(val) == 0){
                valNumMap.remove(val);
            }

            return val;
        }
    }

/**
 * Your MaxStack object will be instantiated and called as such:
 * MaxStack obj = new MaxStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.peekMax();
 * int param_5 = obj.popMax();
 */

}