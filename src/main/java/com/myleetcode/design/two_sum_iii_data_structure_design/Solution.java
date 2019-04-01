package com.myleetcode.design.two_sum_iii_data_structure_design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    class TwoSum {

        // intuition: internal data structure is sorted list, then find is to find twosum
//https://leetcode.com/problems/two-sum-iii-data-structure-design/discuss/52015/Beats-100-Java-Code
        List<Integer> numList;
        Map<Integer, Integer> numNumMap;
        /** Initialize your data structure here. */
        public TwoSum() {
            numList = new ArrayList<>();
            numNumMap = new HashMap<>();
        }

        // TC: O(1)
        /** Add the number to an internal data structure.. */
        public void add(int number) {
            if(!numNumMap.containsKey(number)){
                numList.add(number);
                numNumMap.put(number, 1);
            }else{
                numNumMap.put(number, numNumMap.get(number) + 1);
            }
        }

        // TC: O(N), N is the length of numList
        /** Find if there exists any pair of numbers which sum is equal to the value. */
        public boolean find(int value) {
            // special case
            if(numList == null || numList.size() == 0){
                return false;
            }

            for(int i = 0; i < numList.size(); i++){
                int num = numList.get(i);
                int target = value - num;
                if(num == target && (numNumMap.get(target) > 1)){
                    return true;
                }
                if(num != target && numNumMap.containsKey(target)){
                    return true;
                }
            }

            return false;
        }
    }

/**
 * Your TwoSum object will be instantiated and called as such:
 * TwoSum obj = new TwoSum();
 * obj.add(number);
 * boolean param_2 = obj.find(value);
 */
}
