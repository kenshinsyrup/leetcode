package com.myleetcode.map.brick_wall;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    public int leastBricks(List<List<Integer>> wall) {
        // return leastBricksByMap(wall);
        return leastBricksByMapII(wall);
    }

    /*
    We want to cut from the edge of the most common location among all the levels, hence using a map to record the locations and their corresponding occurrence
    https://leetcode.com/problems/brick-wall/discuss/101728/I-DON'T-THINK-THERE-IS-A-BETTER-PERSON-THAN-ME-TO-ANSWER-THIS-QUESTION
    */
    private int leastBricksByMapII(List<List<Integer>> wall) {
        if (wall == null || wall.size() == 0 || wall.get(0) == null || wall.get(0).size() == 0) {
            return 0;
        }

        int count = 0;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (List<Integer> list : wall) {
            int length = 0;
            for (int i = 0; i < list.size() - 1; i++) {
                length += list.get(i);
                map.put(length, map.getOrDefault(length, 0) + 1);

                count = Math.max(count, map.get(length));
            }
        }

        return wall.size() - count;
    }

    private int leastBricksByMap(List<List<Integer>> wall) {
        if (wall == null || wall.size() == 0 || wall.get(0) == null || wall.get(0).size() == 0) {
            return 0;
        }

        Map<Integer, Integer> idxGapMap = new HashMap<>();
        for (List<Integer> row : wall) {
            int idx = 0;
            for (int i = 0; i < row.size() - 1; i++) {
                int len = row.get(i);
                idx += len;
                idxGapMap.put(idx, idxGapMap.getOrDefault(idx, 0) + 1);
            }
        }

        int leastIdx = 0;
        int l = 0;
        for (int idx : idxGapMap.keySet()) {
            if (l < idxGapMap.get(idx)) {
                l = idxGapMap.get(idx);
                leastIdx = idx;
            }
        }

        int count = 0;
        for (List<Integer> row : wall) {
            int len = 0;
            for (int brick : row) {
                len += brick;

                if (len == leastIdx) {
                    count++;
                }
                if (len > leastIdx) {
                    break;
                }
            }
        }

        return wall.size() - count;
    }
}
