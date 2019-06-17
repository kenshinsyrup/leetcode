package com.myleetcode.topological_sort.course_schedule_ii;

import java.util.*;

class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        return findOrderByTopologyWithDAGVerifyAndDFS(numCourses, prerequisites);
    }

    // intuition: Topological Sort with DAG Verify and DFS
    // TC: O(M + N)
    // SC: O(N)
    // This is a followup of 207.Course Schedule
    // 标准化写法
    private int[] findOrderByTopologyWithDAGVerifyAndDFS(int numCourses, int[][] prerequisites){
        /* 重要！！不要这样check:
        if(numCourses <= 0 || prerequisites == null || prerequisites.length == 0){
            return new int[numCourses];
        }
        这样是不对的，根据题目，prerequisites只是may，所以如果prerequisites不存在，那么我们更可以无所顾虑的完成我们需要的所有课程，所以这个check是错的。如下例
        // input: 3 []
        // output: [2,1,0]
        */
        // special case
        if(numCourses <= 0){
            return new int[0];
        }

        // make graph
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for(int[] pre: prerequisites){
            graph.putIfAbsent(pre[1], new ArrayList<>());
            graph.get(pre[1]).add(pre[0]);
        }

        // colors
        int[] colors = new int[numCourses];

        // result List, at last remember reverse the order
        List<Integer> retList = new ArrayList<>();

        // DFSAll: if find cycle at any time, return int[0]
        for(int i = 0; i < numCourses; i++){
            if(colors[i] != -1){
                if(topologicalSortWithDAGVerifyAndDFS(graph, i, colors, retList)){
                    return new int[0];
                }
            }
        }

        // get correct order courses
        int[] ret = new int[numCourses];
        int j = 0;
        for(int i = retList.size() - 1; i >= 0; i--){
            ret[j] = retList.get(i);
            j++;
        }

        return ret;
    }

    // if has cycle, return true
    private boolean topologicalSortWithDAGVerifyAndDFS(Map<Integer, List<Integer>> graph, int courseIdx, int[] colors, List<Integer> retList){
        // set status to visiting
        colors[courseIdx] = 1;

        // children courses
        List<Integer> nextCourseList = graph.getOrDefault(courseIdx, new ArrayList<>());
        for(int i = 0; i < nextCourseList.size(); i++){
            int nextCourseIdx = nextCourseList.get(i);

            if(colors[nextCourseIdx] == 1){
                return true;
            }else if(colors[nextCourseIdx] == 0){
                if(topologicalSortWithDAGVerifyAndDFS(graph, nextCourseIdx, colors, retList)){
                    return true;
                }
            }
        }

        // set status to visited
        colors[courseIdx] = -1;

        // current node finish, add to retList
        retList.add(courseIdx);

        return false;
    }
}
