package com.myleetcode.map.k_diff_pairs_in_an_array;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Solution {
    public int findPairs(int[] nums, int k) {
        // return findPairsByMap(nums, k);
        return findPairsByMapII(nums, k);
    }

    /*
    The improvment is, it using the map.containsKey(num) checking to make sure we only count unique pairs:
        if contains, then we only count one pair when k is 0 and we first time meet another same num.
        if not contains, then we add 1 for each existing num+k and num-k, since not contains num, then this must be unique pair.
    TC: O(N)
    SC: O(N)
    */
    private int findPairsByMapII(int[] nums, int k) {
        if (k < 0) {
            return 0;
        }

        Map<Integer, Integer> map = new HashMap<>();
        int result = 0;
        for (int num : nums) {
            if (map.containsKey(num)) {
                // If first time find a same number appeared already, count 1.
                if (k == 0 && map.get(num) == 1) {
                    result++;
                }
                map.put(num, map.get(num) + 1);
            } else {
                if (map.containsKey(num - k)) {
                    result++;
                }
                if (map.containsKey(num + k)) {
                    result++;
                }
                map.put(num, 1);
            }
        }
        return result;
    }

    /*
    N is length of nums.
    TC: O(N^2), the checking unique part is time consuming, should be improved. We could define a Pair class and override hashCode and equals method, and for each pari we store them into a Set.
    SC: O(N)
    */
    private int findPairsByMap(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // <num, appear times>
        Map<Integer, Integer> numNumMap = new HashMap<>();
        Set<int[]> pairSet = new HashSet<>();
        int ret = 0;
        for (int i = 0; i < nums.length; i++) {
            int curNum = nums[i];
            int checkVal1 = curNum - target; // if acurNum - target == ppearedNum
            int checkVal2 = curNum + target; // if curNum + target == appearedNum

            if (numNumMap.containsKey(checkVal1)) {
                boolean isUnique = true;
                for (int[] pair : pairSet) {
                    if (check(pair, checkVal1, curNum)) {
                        isUnique = false;
                        break;
                    }
                }
                if (isUnique) {
                    ret++;
                    pairSet.add(new int[]{checkVal1, curNum});
                }
            }
            if (numNumMap.containsKey(checkVal2)) {
                boolean isUnique = true;
                for (int[] pair : pairSet) {
                    if (check(pair, checkVal2, curNum)) {
                        isUnique = false;
                        break;
                    }
                }
                if (isUnique) {
                    ret++;
                    pairSet.add(new int[]{checkVal2, curNum});
                }
            }

            numNumMap.put(curNum, numNumMap.getOrDefault(curNum, 0) + 1);
        }

        return ret;
    }

    private boolean check(int[] pair, int num1, int num2) {
        return pair[0] == num1 && pair[1] == num2 || pair[0] == num2 && pair[1] == num1;
    }
}
