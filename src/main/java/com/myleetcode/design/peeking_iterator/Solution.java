package com.myleetcode.design.peeking_iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Solution {
    // Java Iterator interface reference:
// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html

    class PeekingIterator implements Iterator<Integer> {

        // for the followup: generic
        // https://leetcode.com/problems/peeking-iterator/discuss/226430/Java-Solution-for-the-follow-up-question

        // check this good solution: https://leetcode.com/problems/peeking-iterator/discuss/72558/Concise-Java-Solution
        Integer nextVal;
        boolean noSuchElem;
        Iterator<Integer> iterator;

        // TC: O(1)
        // SC: O(1)
        public PeekingIterator(Iterator<Integer> iterator) {
            this.iterator = iterator;

            if(this.iterator.hasNext()){
                this.nextVal = this.iterator.next();
            }else{
                this.noSuchElem = true;
            }
        }

        // TC: O(1)
        // SC: O(1)
        // Returns the next element in the iteration without advancing the iterator.
        public Integer peek() {
            // you should confirm with interviewer what to return/throw
            // if there are no more values
            if(this.noSuchElem){
                throw new NoSuchElementException(); // import java.util.NoSuchElementException;
            }

            return nextVal;
        }

        // TC: O(1)
        // SC: O(1)
        // hasNext() and next() should behave the same as in the Iterator interface.
        // Override them if needed.
        @Override
        public Integer next() {
            if(this.noSuchElem){
                throw new NoSuchElementException();
            }

            // ret is the return value
            int ret = nextVal;

            // get the next iterator value or mark as noSuchElem
            if(this.iterator.hasNext()){
                nextVal = this.iterator.next();
            }else{
                this.noSuchElem = true;
            }

            return ret;
        }


        @Override
        public boolean hasNext() {
            return !this.noSuchElem;
        }

        // BUT, this is not good, although it passes the leetcode OJ. Consider this, why we use Iterator in our code or in practice? yeah, too many elems. If we read too many elems into memory that will be horrible, so we have the concept Iterator, so now we knwo this implementation is not good because we use the List ie the memory to store all elems.
        // intuition: first thought is we could convert the iterator to List, then maintain a global varialbe called idx to indicate next pos in List. every time we call next() we add the idx with 1.
//     List<Integer> numList;
//     Integer idx;
//     Iterator<Integer> iterator;

//     // TC: O(N), N is the input iterator length
//     // SC: O(N)
// 	public PeekingIterator(Iterator<Integer> iterator) {
// 	    // initialize any member here.
//         this.idx = 0;

// 	    this.numList = new ArrayList<>();
//         while(iterator.hasNext()){
//             this.numList.add(iterator.next());
//         }

//         this.iterator = this.numList.iterator();
// 	}

//     // TC: O(1)
//     // SC: O(1)
//     // Returns the next element in the iteration without advancing the iterator.
// 	public Integer peek() {
//         if(this.iterator.hasNext()){
//             return this.numList.get(idx);
//         }

//         return 0;
// 	}

//     // TC: O(1)
//     // SC: O(1)
// 	// hasNext() and next() should behave the same as in the Iterator interface.
// 	// Override them if needed.
// 	@Override
// 	public Integer next() {
//         if(this.iterator.hasNext()){
//             idx++;

//             return this.iterator.next();
// 	    }

//         return 0;
//     }


// 	@Override
// 	public boolean hasNext() {
// 	    return this.iterator.hasNext();
// 	}
    }
}
