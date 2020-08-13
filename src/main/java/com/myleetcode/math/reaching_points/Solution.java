package com.myleetcode.math.reaching_points;

public class Solution {
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        // return reachingPointsByRecursion(sx, sy, tx, ty); // Stack Overflow.
        return reachingPointsByRecursionII(sx, sy, tx, ty);
    }

    /*
    Goldman Sachs OA. Disaster Bad Problem.
    https://leetcode.com/problems/reaching-points/discuss/230588/Easy-to-understand-diagram-and-recursive-solution
    */
    private boolean reachingPointsByRecursionII(int sx, int sy, int tx, int ty) {
        if (sx > tx || sy > ty) {
            return false;
        }

        if (tx < ty) {
            return recurse(sx, sy, tx, ty);
        }
        return recurse(sy, sx, ty, tx);
    }

    private boolean recurse(int sx, int sy, int tx, int ty) {
        if (sx > tx) {
            return false;
        } else if (sx == tx) {
            return ((ty - sy) % sx) == 0;
        } else {
            return recurse(sy, sx, ty % tx, tx);
        }
    }

    /*
    Stack Overflow.
    */
    private boolean reachingPointsByRecursion(int sx, int sy, int tx, int ty) {
        if (sx == tx && sy == ty) {
            return true;
        }

        if (sx > tx || sy > ty) {
            return false;
        }

        return reachingPointsByRecursion(sx + sy, sy, tx, ty) || reachingPointsByRecursion(sx, sx + sy, tx, ty);
    }
}
