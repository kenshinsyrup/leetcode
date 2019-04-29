package com.myleetcode.design.min_stack;

class MinStack {

    /** initialize your data structure here. */

    // only use Stack: https://leetcode.com/problems/min-stack/discuss/49014/Java-accepted-solution-using-one-stack
    // the most complicated part is the retrieve min operation.
    // we could solve this by use another variable min. at first we initialize the min with Integer.MIN_VALUE.
    // for push, then, every time we push x, we compare the min with the x, if x <= min means we should update the min, then we first push min to the stack, update the min, then push the x, by this way, we have remembered the old-min value(just on below the new-min value in the stack).
    // for pop, every time we want to pop, we first check if current min is equals to the top of the stack, if equals, means the old-min of this min is just below the value in the stack, so we pop the min out, then pop the old-min out to the min variable
    // by this way, we use extra int space in stack to store the coresponding old-min of every min, so we dont need another DS

    Deque<Integer> numStack;
    int curMin;

    public MinStack() {
        this.numStack = new ArrayDeque<>();

        this.curMin = Integer.MAX_VALUE;
    }

    public void push(int x) {
        if(x <= curMin){
            // if curMin need update, then we first push the old-curMin to the stack, then update the curMin, then push x, this way, we have remembered the old-curMin for the new curMin
            this.numStack.push(curMin);

            curMin = x;
        }

        // push to stack
        this.numStack.push(x);
    }

    public void pop() {
        if(!this.numStack.isEmpty()){
            // check if the top elem in stack is equal to the curMin, if it is, we know the old-min of current min is just below it, we should update the curMin with it because we poped out the curMin
            if(this.curMin == this.numStack.peek()){
                // pop out the top
                this.numStack.pop();

                // update the curMin
                this.curMin = this.numStack.pop();
            }else{
                // otherwise, just pop
                this.numStack.pop();
            }
        }
    }

    public int top() {
        // check
        if(!this.numStack.isEmpty()){
            return this.numStack.peek();
        }
        return 0;
    }

    public int getMin() {
        return this.curMin;
    }
}



// BUT this is not correct. Because the problem said: Design a stack so we could not use other DS maybe
// intuition:
// for the push, pop and top operation we could use the Stack DS, that's easy.
// for the retrieve min operation, the naive option is to build a list for the Stack elems and sort the List, that's O(N*log(N)) for every get. if we use MinHeap, we could do it in O(1) time, and when we push new elems to the Stack or remove it from Stack, we need O(log(N)) time to heapify the MinHeap
// so we use two DS to store the inputs, push, pop is O(1) in Stack, O(log(N)) in MinHeap, top is O(1) in Stack and dont affects the MinHeap, getMin is O(1) in the MinHeap
// BTW, MinHeap or MaxHeap in Java could be implemented by PQ
/*
    Deque<Integer> numStack;
    PriorityQueue<Integer> numMinHeap;

    public MinStack() {
        // stack for push, pop, top
        this.numStack = new ArrayDeque<>();
        // minHeap for retrieve min, we need the elems sorted ascending
        this.numMinHeap = new PriorityQueue<>();


        // here, plz dont use this custom comparator
        // (numFir, numSec) -> {
        //     return numFir - numSec;
        // }
        // for the PQ, use this
        // (numFir, numSec) -> {
        //     return Integer.compare(numFir, numSec);
        // }
        // or keep default(pq is ascending by default)to avoid the overflow problem.
    }

    public void push(int x) {
        // push to stack
        this.numStack.push(x);

        // offer elem and heapify the minHeap
        this.numMinHeap.offer(x);
    }

    public void pop() {
        // check
        if(!this.numStack.isEmpty()){
            // pop
            int num = this.numStack.pop();

            // remove from minHeap
            this.numMinHeap.remove(num);
        }
    }

    public int top() {
        // check
        if(!this.numStack.isEmpty()){
            return this.numStack.peek();
        }
        return 0;
    }

    public int getMin() {
        if(!this.numStack.isEmpty()){
            return this.numMinHeap.peek();
        }
        return 0;
    }
}
*/
/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
