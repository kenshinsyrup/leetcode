package com.myleetcode.design.design_phone_directory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {
    class PhoneDirectory {

        // intuition: we could use a List and Set to store 0-maxNum,
        // then for get we return random pos value in List and remove from List and Set, remove from Set cost O(1) and remove from List cost O(N), but we could swap this pos value with the end elem of List then remove the end elem to reach O(1); actually here didnot require random, so could just return the first
        // for check, check the Set, cost O(1)
        // for release, add to List and Set if it's valid
        // BUT, for init, we need put all nums from 0~maxNumbers to List and Set this cost O(N)
        // so, TC is O(N) and SC is O(N), where N is the input maxNumbers.
        // BUT this is not a good approach because we may use too many memory if the maxNumbers is too large.

        // https://leetcode.com/problems/design-phone-directory/discuss/278566/HashSet-%2B-ArrayList-solution
        // this could be imporved
        // we dont put all nums into the List and Set at init, we use the Set store all nums we used, use the List to store all nums that are released. this way, we could reduce TC and SC in most time because we are increasingly put num to memory instead of decreasingly remove num from memory

        List<Integer> releasedNumList;
        Set<Integer> usedNumSet;
        int curNum;
        int maxNumbers;

        /** Initialize your data structure here
         @param maxNumbers - The maximum numbers that can be stored in the phone directory. */
        public PhoneDirectory(int maxNumbers) {
            this.curNum = 0;
            this.releasedNumList = new ArrayList<>();
            this.usedNumSet = new HashSet<>();
            this.maxNumbers = maxNumbers;
        }

        /** Provide a number which is not assigned to anyone.
         @return - Return an available number. Return -1 if none is available. */
        public int get() {
            int ret = -1;

            // we could get num from released List in high priority so to reduce memory cost
            if(this.releasedNumList.size() != 0){
                ret = this.releasedNumList.remove(this.releasedNumList.size() - 1);// remove form last idx in List cost O(1)
                // put into in use Set
                this.usedNumSet.add(ret);
                return ret;
            }

            //if released List is empty, we return the curNum and update if curNum is not out of range
            if(this.curNum < this.maxNumbers){
                ret = this.curNum;
                this.curNum++;

                this.usedNumSet.add(ret);
                return ret;
            }

            return -1;
        }

        /** Check if a number is available or not. */
        public boolean check(int number) {
            return !this.usedNumSet.contains(number);
        }

        /** Recycle or release a number. */
        public void release(int number) {
            // must be in use
            if(this.usedNumSet.contains(number)){
                this.usedNumSet.remove(number);// remove from in use Set
                this.releasedNumList.add(number);// add to release List
            }
        }
    }

/**
 * Your PhoneDirectory object will be instantiated and called as such:
 * PhoneDirectory obj = new PhoneDirectory(maxNumbers);
 * int param_1 = obj.get();
 * boolean param_2 = obj.check(number);
 * obj.release(number);
 */
}
