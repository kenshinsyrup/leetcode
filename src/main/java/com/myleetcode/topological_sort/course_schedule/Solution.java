package com.myleetcode.topological_sort.course_schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // special case
        if(numCourses < 0){
            return false;
        }
        // LeetCode: 根据题目，prerequisites只是may，所以如果不存在的话，我们应该更无所顾虑的完成了所有的课毕竟没有依赖了，所以这里的case一定是true
        if(prerequisites == null || prerequisites.length == 0){
            return true;
        }

        return canFinisiByTopologyAndDFS(numCourses, prerequisites);
    }

    // TC: O(M + N), M is |E|, N is |V|
    // SC: O(N)
    // intuition: this problem dont need us to give the Topological Sort result, it only asks if there's a Topological Sort, so, this is actually a Verify DAG problem.
    // we only need to check if the G is a DAG, if it is, then it has Topological Sort, means we could finish all courses.
    // to Verify DAG, use the Three Color algo

    // Topology
    // https://leetcode.com/problems/course-schedule/discuss/58532/5ms-DFS-beat-98-and-9ms-BFS-in-java
    // https://efficientcodeblog.wordpress.com/2017/11/28/topological-sort-dfs-bfs-and-dag/
    //https://www.khanacademy.org/computing/computer-science/algorithms/graph-representation/a/representing-graphs
    private boolean canFinisiByTopologyAndDFS(int numCourses, int[][] prerequisites){
        // init graph, normalize the int[][] prerequisites to graph represented by Adjacent List
        Map<Integer, List<Integer>> graph = new HashMap<>(); // this problem we could use List<List<Integer>> graph because the courses are integer
        for(int[] pre: prerequisites){
            graph.putIfAbsent(pre[1], new ArrayList<>());
            graph.get(pre[1]).add(pre[0]);
        }

        // three colors for three status: 0 is new node, 1 is visiting node, -1 is visited node
        int[] colors = new int[numCourses];

        // DFSAll, if has cycle, then not DAG, return false
        for(int i = 0; i < numCourses; i++){
            if(colors[i] != -1){
                if(hasCycle(graph, i, colors)){
                    return false;
                }
            }
        }

        return true;
    }

    private boolean hasCycle(Map<Integer, List<Integer>> graph, int courseIdx, int[] colors){
        // set status to visiting for current course
        colors[courseIdx] = 1;

        // process children nodes, here should be careful that graph is the prerequisite graph, so maybe there's no children course list of current courseIdx, so we use getOrDefault to avoid the null pointer error in the for loop
        List<Integer> nextCourseList = graph.getOrDefault(courseIdx, new ArrayList<>());
        for(int i = 0; i < nextCourseList.size(); i++){
            // children courses
            int nextCourseIdx = nextCourseList.get(i);

            // check color:
            // if visiting, means has cycle, true
            // if new node and has cycle, true
            if(colors[nextCourseIdx] == 1){
                return true;
            }else if(colors[nextCourseIdx] == 0){
                if(hasCycle(graph, nextCourseIdx, colors)){
                    return true ;
                }
            }
        }

        // set status to visited for current course
        colors[courseIdx] = -1;

        return false;
    }

}
