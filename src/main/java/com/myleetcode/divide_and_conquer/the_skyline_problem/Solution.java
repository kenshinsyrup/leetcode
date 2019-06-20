package com.myleetcode.divide_and_conquer.the_skyline_problem;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        return getSkylineByDivideAndConquer(buildings);
    }

    // Divide and Conquer
    // solution section: the hardest part is to know how to merge two skyline list
    private List<List<Integer>> getSkylineByDivideAndConquer(int[][] buildings){
        if(buildings == null || buildings.length == 0 || buildings[0] == null || buildings[0].length == 0){
            return new ArrayList<>();
        }

        return divideAndConquer(buildings, 0, buildings.length - 1);
    }

    private List<List<Integer>> divideAndConquer(int[][] buildings, int start, int end){
        List<List<Integer>> output = new ArrayList<List<Integer>>();

        if(start > end){
            return output;
        }

        if(start == end){
            List<Integer> leftList = new ArrayList<>();
            leftList.add(buildings[start][0]);
            leftList.add(buildings[start][2]);
            output.add(leftList);

            List<Integer> rightList = new ArrayList<>();
            rightList.add(buildings[start][1]);
            rightList.add(0);
            output.add(rightList);

            return output;
        }

        int mid = (start + end) / 2;

        List<List<Integer>> leftSkyline = divideAndConquer(buildings, start, mid);
        List<List<Integer>> rightSkyline = divideAndConquer(buildings, mid + 1, end);

        int leftLen = leftSkyline.size();
        int rightLen = rightSkyline.size();
        int leftY = 0;
        int rightY = 0;
        int curY = 0;
        int i = 0; // traverse leftSkyline
        int j = 0; // traverse rightSkyline
        int x = 0;
        int maxY = 0;

        while(i < leftLen && j < rightLen){
            List<Integer> pointL = leftSkyline.get(i);
            List<Integer> pointR = rightSkyline.get(j);

            // pick up the smallest x
            if (pointL.get(0) < pointR.get(0)) {
                x = pointL.get(0);
                leftY = pointL.get(1);

                i++;
            }else {
                x = pointR.get(0);
                rightY = pointR.get(1);

                j++;
            }

            // max height (i.e. y) between both skylines
            maxY = Math.max(leftY, rightY);

            // update output if there is a skyline change
            if (curY != maxY) {
                updateOutput(output, x, maxY);
                curY = maxY;
            }
        }
        while(i < leftLen){
            List<Integer> point = leftSkyline.get(i);
            x = point.get(0);
            int y = point.get(1);

            // update output
            // if there is a skyline change
            if (curY != y) {
                updateOutput(output, x, y);
                curY = y;
            }

            i++;
        }
        while(j < rightLen){
            List<Integer> point = rightSkyline.get(j);
            x = point.get(0);
            int y = point.get(1);

            // update output
            // if there is a skyline change
            if (curY != y) {
                updateOutput(output, x, y);
                curY = y;
            }

            j++;
        }

        return output;
    }

    /**
     * Update the final output with the new element.
     */
    public void updateOutput(List<List<Integer>> output, int x, int y) {
        // if skyline change is not vertical
        // add the new point
        if (output.isEmpty() || output.get(output.size() - 1).get(0) != x)
            output.add(new ArrayList<Integer>() {{add(x); add(y); }});
        else {
            // if skyline change is vertical
            // update the last point
            output.get(output.size() - 1).set(1, y);
        }
    }
}
