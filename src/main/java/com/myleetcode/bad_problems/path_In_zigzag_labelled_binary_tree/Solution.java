package com.myleetcode.bad_problems.path_In_zigzag_labelled_binary_tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
    public List<Integer> pathInZigZagTree(int label) {
        return pathInZigZagTreeByMath(label);
    }

    /*
    Bad problem.

    https://leetcode.com/problems/path-in-zigzag-labelled-binary-tree/discuss/323312/Simple-solution-in-java-(Using-properties-of-complete-binary-tree)-(O-log-N)
    */
    private List<Integer> pathInZigZagTreeByMath(int label) {
        if (label < 0) {
            return new ArrayList<>();
        }

        List<Integer> ret = new ArrayList<>();

        int parent = label;
        ret.add(parent);
        while (parent != 1) {
            int level = (int) (Math.log(parent) / Math.log(2));
            int offset = (int) (Math.pow(2, level + 1) - 1) - parent;

            parent = (int) (Math.pow(2, level) + offset) / 2;
            ret.add(parent);
        }

        Collections.reverse(ret);

        return ret;

    }

}
