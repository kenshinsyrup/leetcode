package com.myleetcode.bad_problems.find_positive_integer_solution_for_a_given_equation;

/*
 * // This is the custom function interface.
 * // You should not implement it, or speculate about its implementation
 * class CustomFunction {
 *     // Returns f(x, y) for any given positive integers x and y.
 *     // Note that f(x, y) is increasing with respect to both x and y.
 *     // i.e. f(x, y) < f(x + 1, y), f(x, y) < f(x, y + 1)
 *     public int f(int x, int y);
 * };
 */

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> findSolution(CustomFunction customfunction, int z) {
        return findSolutionByBS(customfunction, z);
    }

    /*
    Explaination:
    Rephrase the problem:
    Given a matrix, each row and each column is increasing.
    Find all coordinates (i,j) that A[i][j] == z
    https://leetcode.com/problems/find-positive-integer-solution-for-a-given-equation/discuss/414249/JavaC%2B%2BPython-O(X%2BY)

    */
    private List<List<Integer>> findSolutionByBS(CustomFunction customfunction, int z) {
        List<List<Integer>> res = new ArrayList<>();
        int x = 1;
        int y = 1000;
        while (x <= 1000 && y >= 1) {
            int v = customfunction.f(x, y);

            if (v == z) {
                List<Integer> innerList = new ArrayList();
                innerList.add(x);
                innerList.add(y);
                res.add(innerList);

                x++;
            } else if (v > z) {
                y--;
            } else {
                x++;
            }
        }

        return res;
    }

    // For compile.
    class CustomFunction {
        public int f(int x, int y) {
            return 0;
        }
    }
}
