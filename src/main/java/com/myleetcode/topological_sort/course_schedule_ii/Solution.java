package com.myleetcode.topological_sort.course_schedule_ii;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        return findOrderByTopologySortWithDAGVerify(numCourses, prerequisites);
    }

    /*
    This is a followup of 207.Course Schedule

    intuition: Topological Sort with DAG Verify
        1. Need check whether given graph is DAG, if not, could not do Topological Sort.
        2. Use recursive finish time method to get the Topological Sort.
    But 1 and 2 could do simutaneously, when we check DAG with 3-color thought, we could keep record the finish order of each course.

    TC: O(M + N)
    SC: O(N)
    */
    private int[] findOrderByTopologySortWithDAGVerify(int numCourses, int[][] prerequisites) {
        /* 重要！！不要这样check:
        if(numCourses <= 0 || prerequisites == null || prerequisites.length == 0){
            return new int[numCourses];
        }
        这样是不对的，根据题目，prerequisites只是may，所以如果prerequisites不存在，那么我们更可以无所顾虑的完成我们需要的所有课程，所以这个check是错的。如下例
        // input: 3 []
        // output: [2,1,0]
        */
        // Special case
        if (numCourses <= 0) {
            return new int[0];
        }

        // Make graph
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] pre : prerequisites) {
            graph.putIfAbsent(pre[1], new ArrayList<>());
            graph.get(pre[1]).add(pre[0]);
        }

        // 3-colors, -1 is visied, 0 is not visited, 1 is visiting.
        int[] colors = new int[numCourses];

        // Record Finish course in List by their finish time. This will be the reverse order of the topological sort.
        List<Integer> finishList = new ArrayList<>();

        // Topological Sort and Verify DAG: if find cycle at any time, return int[0]
        for (int i = 0; i < numCourses; i++) {
            if (colors[i] != -1) {
                if (topologicalSortWithDAGVerifyAndDFS(graph, i, colors, finishList)) {
                    return new int[0];
                }
            }
        }

        // Get correct order courses.
        int[] ret = new int[numCourses];
        int j = 0;
        for (int i = finishList.size() - 1; i >= 0; i--) {
            ret[j] = finishList.get(i);
            j++;
        }

        return ret;
    }

    //Return true if find cycle.
    private boolean topologicalSortWithDAGVerifyAndDFS(Map<Integer, List<Integer>> graph, int courseIdx, int[] colors, List<Integer> finishList) {
        // Set status to visiting.
        colors[courseIdx] = 1;

        // Children courses.
        List<Integer> nextCourseList = graph.getOrDefault(courseIdx, new ArrayList<>());
        for (int i = 0; i < nextCourseList.size(); i++) {
            int nextCourseIdx = nextCourseList.get(i);

            if (colors[nextCourseIdx] == -1) { // Visited.
                continue;
            } else if (colors[nextCourseIdx] == 0) { // Not visited, process it recursively.
                if (topologicalSortWithDAGVerifyAndDFS(graph, nextCourseIdx, colors, finishList)) {
                    return true;
                }
            } else if (colors[nextCourseIdx] == 1) { // Visiting, cycle detected.
                return true;
            }
        }

        // Set status to visited.
        colors[courseIdx] = -1;

        // Current node finished.
        finishList.add(courseIdx);

        return false;
    }
}
