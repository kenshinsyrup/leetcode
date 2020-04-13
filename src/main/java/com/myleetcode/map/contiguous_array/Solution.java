package com.myleetcode.map.contiguous_array;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int findMaxLength(int[] nums) {
        return findMaxLengthByPresumAndMap(nums);
    }

    private int findMaxLengthByPresumAndMap(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int len = nums.length;
        int[] presumArr = new int[len + 1];
        for (int i = 0; i < len; i++) {
            if (nums[i] == 0) {
                presumArr[i + 1] = presumArr[i] - 1;
            } else {
                presumArr[i + 1] = presumArr[i] + 1;
            }
        }

        int ret = 0;
        Map<Integer, Integer> presumIdxMap = new HashMap<>();
        for (int i = 1; i < presumArr.length; i++) {
            int presum = presumArr[i];

            if (presum == 0) {
                ret = Math.max(ret, (i - 1) - 0 + 1); // if presum is 0 means equal number 0 and 1s in nums[0:i-1]
            } else {
                if (!presumIdxMap.containsKey(presum)) {
                    presumIdxMap.put(presum, i);
                } else {
                    ret = Math.max(ret, (i - 1) - presumIdxMap.get(presum) + 1); // presumArr[a] has same value with presumArr[b] where b>=a, means nums[a:b-1] sum is 0.
                }
            }
        }

        return ret;
    }

}
