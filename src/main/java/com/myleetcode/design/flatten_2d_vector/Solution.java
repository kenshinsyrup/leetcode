package com.myleetcode.design.flatten_2d_vector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Solution {
    class Vector2D {

        // the problem, at first the input is 2-d List, so have this solution: https://leetcode.com/problems/flatten-2d-vector/discuss/67669/Java-Iterator-Solution-Explained
        // now its input is 2-d array, and if we do it use the iterator way, we could also convert the 2-d Array to List then solve it as the above solution

        // intuition: traverse the 2-d Array and put elems to List then convert to Iterator
        // to avoid load too many data to memory, we could just use List store one row in Array v, and use another variable to indicate current row

        private Iterator<Integer> iter;
        private int rowIdx;
        private int[][] numArr;

        public Vector2D(int[][] v) {
            // should not return if v[0].length == 0 because this 2-d array may have empty array innerside
            if(v == null || v.length == 0 || v[0] == null){
                return;
            }

            this.numArr = v;
            this.rowIdx = 0;

            getIter();
        }

        private boolean getIter(){
            boolean hasNexIter = false;
            while(this.rowIdx < this.numArr.length){
                System.out.println(this.rowIdx);
                if(this.numArr[this.rowIdx].length == 0){
                    this.rowIdx++;
                    continue;
                }

                List<Integer> numList = new ArrayList<>();
                for(int val: this.numArr[this.rowIdx]){
                    numList.add(val);
                }
                System.out.println(numList);
                this.iter = numList.iterator();
                this.rowIdx++;
                hasNexIter = true;
                break;
            }

            return hasNexIter;
        }

        public int next() {
            if(hasNext()){
                return this.iter.next();
            }

            return 0;
        }

        public boolean hasNext() {
            // if input v is empty, our iter is null
            if(this.iter == null){
                return false;
            }

            if(this.iter.hasNext()){
                return true;
            }

            if(getIter()){
                return true;
            }

            return false;
        }
    }

/**
 * Your Vector2D object will be instantiated and called as such:
 * Vector2D obj = new Vector2D(v);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */
}

