package com.myleetcode.design.design_a_stack_with_increment_operation;

public class Solution {

    class CustomStack {

        int[] storeArr;
        int capacity; // Capacity.
        int size; // Real size.
        int lastIdx; // Last real element idx.

        public CustomStack(int maxSize) {
            this.capacity = maxSize;
            this.storeArr = new int[maxSize];
            this.size = 0;
            this.lastIdx = -1;
        }

        public void push(int x) {
            if (this.size < this.capacity) {
                // Store new element.
                this.storeArr[this.lastIdx + 1] = x;

                // Update real size.
                this.size++;

                this.lastIdx++;
                if (this.lastIdx == this.capacity) {
                    this.lastIdx -= 1;
                }

                return;
            }
        }

        public int pop() {
            if (this.size == 0) {
                return -1;
            }

            int topElem = this.storeArr[this.lastIdx];

            this.size--;

            this.lastIdx--;

            return topElem;
        }

        public void increment(int k, int val) {
            int idx = 0;
            while (k > 0 && idx < this.size) {
                this.storeArr[idx] += val;

                k--;
                idx++;
            }
        }
    }

/**
 * Your CustomStack object will be instantiated and called as such:
 * CustomStack obj = new CustomStack(maxSize);
 * obj.push(x);
 * int param_2 = obj.pop();
 * obj.increment(k,val);
 */
}
