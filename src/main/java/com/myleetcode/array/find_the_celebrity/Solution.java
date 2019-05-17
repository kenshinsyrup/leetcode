package com.myleetcode.array.find_the_celebrity;

/* The knows API is defined in the parent class Relation.
      boolean knows(int a, int b); */

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    public int findCelebrity(int n) {
        // return findCelebrityByStack(n);
        return findCelebrityByArray(n);
    }

    // this problem is hard to understand what it really wants... especially it even gives a input graph in the eg
    // thing is, it give us n person marked 0 to n-1, give us a func knows(a, b) to tell us if a knows b.


    // TC: O(N)
    // SC: O(1)
    // https://leetcode.com/problems/find-the-celebrity/discuss/71227/Java-Solution.-Two-Pass
    // we could optimize the Stack, because the Stack is not necessary, we just need a variable called candidate, at first we traverse the n person to update the candidate. then we traverse again to check if it's really a Celebrity
    private int findCelebrityByArray(int n){
        if(n <= 0){
            return 0;
        }

        int candidate = 0;
        for(int i = 0; i < n; i++){
            if(candidate == i){// skip self
                continue;
            }

            // if candidate knows anyone, means the candidate is not a Celebrity. then we update the candidate with the i
            if(knows(candidate, i)){
                candidate = i;
            }
        }

        // then we check if all person know the candidate and candidate dont know anyone
        for(int i = 0; i < n; i++){
            if(i == candidate){
                continue;
            }

            if(!knows(i, candidate) || knows(candidate, i)){
                return -1;
            }
        }

        return candidate;
    }


    // TC: O(N)
    // SC: O(N)
    // https://leetcode.com/problems/find-the-celebrity/discuss/71240/AC-Java-solution-using-stack
    // the thought with Stack is very easy
    private int findCelebrityByStack(int n){
        if(n <= 0){
            return 0;
        }

        // use Stack
        Deque<Integer> nodeStack = new ArrayDeque<>();

        // push all nodes to Stack
        for(int i = 0; i < n; i++){
            nodeStack.push(i);
        }

        // check all nodes
        // every time we pop two out of Stack, a and b: if a know b then a is not Celebrity, discard it and push b back; if a dont know b then b is not Celebrity, discard it and push a back.
        // arepeat until Stack only remain one node
        while(nodeStack.size() > 1){
            int a = nodeStack.pop();
            int b = nodeStack.pop();

            if(knows(a, b)){
                nodeStack.push(b);
            }else{
                nodeStack.push(a);
            }
        }

        // the only one reamined in Stack, we only know it is known by the second last node. so we have to check if other peron know it and if it know anyone else.
        int candidate = nodeStack.pop();
        for(int i = 0; i < n; i++){
            if(i == candidate){
                continue; // !!! skip self
            }

            // if anyone dont know candidate, or candidate know anyone, the only candidate is not a Celebrity
            if(!knows(i, candidate) || knows(candidate, i)){
                return -1;
            }
        }

        return candidate;
    }

    // for error check
    boolean knows(int a, int b){
        return false;
    };

}
