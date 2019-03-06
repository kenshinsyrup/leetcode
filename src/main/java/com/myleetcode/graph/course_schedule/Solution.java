package com.myleetcode.graph.course_schedule;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // special case
        // 这里LeetCode设定为true
        if(numCourses <= 0 || prerequisites == null || prerequisites.length == 0){
            return true;
        }

        return canFinisiByTopologyAndDFS(numCourses, prerequisites);
    }

    // Topology
    // https://leetcode.com/problems/course-schedule/discuss/58532/5ms-DFS-beat-98-and-9ms-BFS-in-java
    // https://efficientcodeblog.wordpress.com/2017/11/28/topological-sort-dfs-bfs-and-dag/
    //https://www.khanacademy.org/computing/computer-science/algorithms/graph-representation/a/representing-graphs
    private boolean canFinisiByTopologyAndDFS(int numCourses, int[][] prerequisites){
        // use DFS implemented Topology to check, if has Topological sorting, then can finish.

        // classical dfs topology
        //status
        // first, we need a visiting status to indicate the vertex is been visiting now
        // second, we need a visited status to indicate the vertex is visited and visited now
        // represent a graph
        // given int[][] prerequisites is course2->course1 meaning course1 is the prerequisite of the course2. We should convert it to normal graph: course1->course2, meaning we could reach course2 from course1.

        // init graph, normalize the int[][] prerequisites to graph with List<List<Integer>>
        List<List<Integer>> graph = new ArrayList<List<Integer>>();
        for(int i = 0; i < numCourses; i++){
            graph.add(new ArrayList<Integer>());
        }
        for(int[] pre: prerequisites){
            graph.get(pre[1]).add(pre[0]);// make graph, prerequisite_course->target_courses
        }

        // status, boolean[]
        boolean[] visiting = new boolean[numCourses];
        boolean[] visited = new boolean[numCourses];

        for(int i = 0; i < numCourses; i++){
            if(!canTopoByDFS(graph, i, visiting, visited)){
                return false;
            }
        }

        return true;
    }

    private boolean canTopoByDFS(List<List<Integer>> graph, int vertexNum, boolean[] visiting, boolean[] visited){
        // entering this func means we are visiting current vertex, this question is current course. so:

        // check status
        // if current vertex is visited, return true, we are backing
        if(visited[vertexNum]){
            return true;
        }
        // if current vertex is visiting, then this is a cycle, invalid, because only DAG(Directed Acyclic Graph) has Topological Sorting, ie, canFinish in this question
        if(visiting[vertexNum]){
            return false;
        }

        // if current vertex is not visited, we can set it visiting status
        visiting[vertexNum] = true;

        // process children nodes
        List<Integer> children = graph.get(vertexNum);
        for(int i = 0; i < children.size(); i++){
            int childVertexNum = children.get(i);
            if(!canTopoByDFS(graph, childVertexNum, visiting, visited)){
                return false;
            }
        }

        // processed current vertex, mark finish status
        visited[vertexNum] = true;

        return true;
    }

}
