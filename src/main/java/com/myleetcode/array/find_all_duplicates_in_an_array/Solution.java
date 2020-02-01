package com.myleetcode.array.find_all_duplicates_in_an_array;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        return findDuplicatesByMappingValToIdx(nums);
    }

    /*
    Every time we flip the sign of the number in mappingIdx, so if a num appear twice, then there must be a corresponding mappingIdx appear twice, so when visit in the second time, the element is negative, so we could record it.
    https://leetcode.com/problems/find-all-duplicates-in-an-array/discuss/92387/Java-Simple-Solution

Input:
arr:[4,3,2,7,8,2,3,1]
// i:0,1,2,3,4,5,6,7

0 mappingIdx: 3 7
1 mappingIdx: 2 2
2 mappingIdx: 1 3
3 mappingIdx: 6 3
4 mappingIdx: 7 1
5 mappingIdx: 1 -3 // 1 visited twice
6 mappingIdx: 2 -2 // 2 visited twice
7 mappingIdx: 0 4


Output:
[2,3]

    */
    private List<Integer> findDuplicatesByMappingValToIdx(int[] nums) {
        List<Integer> ret = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return ret;
        }


        int len = nums.length;
        for (int i = 0; i < len; i++) {
            int mappingIdx = Math.abs(nums[i]) - 1;


            if (nums[mappingIdx] > 0) {
                nums[mappingIdx] = -nums[mappingIdx];
            } else {
                ret.add(Math.abs(nums[i]));
            }
        }

        return ret;
    }
}
