package com.myleetcode.math.robot_bounded_in_circle;

public class Solution {
    public boolean isRobotBounded(String instructions) {
        return isRobotBoundedByMath(instructions);
    }

    /*
    Goldman Sachs OA. Bad Problem.
    https://leetcode.com/problems/robot-bounded-in-circle/discuss/290859/java-solution-%2B-clear-explanation
    */
    private boolean isRobotBoundedByMath(String instructions) {
        if (instructions == null || instructions.length() == 0) {
            return false;
        }

        int[] cur = new int[]{0, 0};
        int[][] dirs = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        int dir = 0; // 0:north(up), 1: right, 2: down, 3: left

        for (char in : instructions.toCharArray()) {
            if (in == 'G') {
                cur[0] += dirs[dir][0];
                cur[1] += dirs[dir][1];
            } else if (in == 'L') {
                dir = (dir + 3) % 4;
            } else {
                dir = (dir + 1) % 4;
            }
        }

        // ended up at the same place
        if (cur[0] == 0 && cur[1] == 0) {
            return true;
        }
        // if location has changed, and the direction is north
        if (dir == 0 && !(cur[0] == 0 && cur[1] == 0)) {
            return false;
        }
        return true; // it is always true
    }
}
