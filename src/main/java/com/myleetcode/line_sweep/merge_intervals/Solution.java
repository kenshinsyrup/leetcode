package com.myleetcode.line_sweep.merge_intervals;

import java.util.*;

class Solution {
    public int[][] merge(int[][] intervals) {
        // return mergeBySort(intervals);
        return mergeByTreeMap(intervals);
    }


    /*
    Tree Map:
    Template solution for Line Sweep problem: My Calendar I(729), II(731), III(732); CarPooling(1094);

    N is total intervals.
    TC: O(NlogN), logN is insert TC of TreeMap.
    SC: O(N)
    */
    private int[][] mergeByTreeMap(int[][] intervals) {
        // Special case.
        if (intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0) {
            return intervals;
        }

        // 1. Store all intervals to TreeMap, start and end time as key, value is accumulation of interval number on such time point.
        TreeMap<Integer, Integer> timeNumTM = new TreeMap<>();
        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];

            timeNumTM.put(start, timeNumTM.getOrDefault(start, 0) + 1);
            timeNumTM.put(end, timeNumTM.getOrDefault(end, 0) - 1);
        }

        // 2. Check and merge.
        List<int[]> retList = new ArrayList<>();
        int start = Integer.MAX_VALUE;
        int end = Integer.MIN_VALUE;
        int overlapNum = 0;
        for (int time : timeNumTM.keySet()) {
            start = Math.min(start, time);
            end = Math.max(end, time);
            overlapNum += timeNumTM.get(time);

            // /关键：overlapNum == 0代表区间闭合了，需要把刚刚闭合的区间添加到结果集中。可以类比字符串中左右括号的情况，遇到左括号+1，右括号-1, 当count==0时，区间的括号是完全匹配的。
            if (overlapNum == 0) {
                retList.add(new int[]{start, end});
                start = Integer.MAX_VALUE;
                end = Integer.MIN_VALUE;
            }
        }

        // 3. Build ret.
        int size = retList.size();
        int[][] ret = new int[size][];
        for (int i = 0; i < size; i++) {
            ret[i] = retList.get(i);
        }

        return ret;
    }

    // 按照start时间排序，然后遍历intervals，对于每一个interval，如果其start时间早于上一个interval的end时间说明overlap，那么更新一下end时间(二者最大值)然后这个interval不需要放入ret
    private int[][] mergeBySort(int[][] intervals) {
        // Special case.
        if (intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0) {
            return intervals;
        }

        // sort
        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] i1, int[] i2) {
                return i1[0] - i2[0];
            }
        });

        // result
        List<int[]> retList = new ArrayList<>();

        // init, add first interval in
        retList.add(intervals[0]);

        // traverse intervals, if i.start <= retLastI.end, then overlap, update retLastI.end(we dont need update retLastI.start because we sorted the intervals) and next loop; if i.start > retLastI.end, then no overlap between these two, then we should add the i to ret and use it as comparision target in next loop
        for (int[] i : intervals) {
            // ret中最后一个interval是比较目标
            int[] retLastI = retList.get(retList.size() - 1);

            // overlap
            if (i[0] <= retLastI[1]) {
                retLastI[1] = Math.max(retLastI[1], i[1]);
            } else {
                retList.add(i);
            }
        }

        // 3. Build ret.
        int size = retList.size();
        int[][] ret = new int[size][];
        for (int i = 0; i < size; i++) {
            ret[i] = retList.get(i);
        }

        return ret;

    }
}