package com.myleetcode.design.zigzag_iterator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class Solution {
    public class ZigzagIterator {

        // TC: O(1)
        // SC: O(N), N is the # of input
        // https://leetcode.com/problems/zigzag-iterator/discuss/71819/Java-easy-to-understand-solution-using-Queue!
        // intuition: the first thought is maintain two varialbe v1Idx and v2Idx, if v1Idx <= v2Idx, output v1, otherwise output v2; until reach  end.
        // but for followup, if we have k Lists, then how to scale? a solution is, consider the problem, actually we are output List by order, then output elem in List by order, the order of elem is maintained by List itself, so we need a DS to maintain the order of Lists, so we could use linear DS, such as Array/List/Queue
        // but consider one more step, if we reach the end of one List, we'd better remove it from the container DS, this could reduce the TC of traverse in the container, the 3 DS all need O(N) time for remove a "random" elem, but Array is not good at remove, so it's not good, and List is not easy to remove multpile elems when iterate, so also not very good
        // then, we are traversing the container with a global idx when we call next() function, means we need reach elem in the container in any pos, so Queue is not very good
        // considering all of above, we could use Queue to act as container

        // and, here's a problem, about the containerIdx and listIdx that points to the current List and the current Elem should be output respectively. Of course we could caculate them with a global variable nextIdx like this:
    /*
    private Integer nextIdx;
    containerIdx = nextIdx % container.size();
    listIdx = when no Lists removed, listIdx = nextIdx/container.size; when one List in container is removed, listIdx = nextIdx/container.size - its_size.
    */
        // BUT, this is not so necessary, we could just use the Iterator Interface of Java, we first convert input Lists as Iterators, then we do other things. This could significantly reduce the complexity of codes
        private Deque<Iterator<Integer>> container;

        public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
            this.container = new ArrayDeque<>();
            if(!v1.isEmpty()){
                this.container.offer(v1.iterator());
            }
            if(!v2.isEmpty()){
                this.container.offer(v2.iterator());
            }
        }

        // O(1)
        public int next() {
            // check container
            if(hasNext()){
                // poll the first in queue, this is current Iterator
                Iterator<Integer> iter = container.poll();
                // because we always guarantee the Iterator in Queue has next, so we could just get next()
                int val = iter.next();

                // guarantee the Iterator in container Queue always has next
                if(iter.hasNext()){
                    container.offer(iter);
                }

                return val;
            }

            return -1;
        }

        public boolean hasNext() {
            return !container.isEmpty();
        }
    }

/**
 * Your ZigzagIterator object will be instantiated and called as such:
 * ZigzagIterator i = new ZigzagIterator(v1, v2);
 * while (i.hasNext()) v[f()] = i.next();
 */
}
