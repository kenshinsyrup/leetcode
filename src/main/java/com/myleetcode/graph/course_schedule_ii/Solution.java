package com.myleetcode.graph.course_schedule_ii;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // special case
        /* 重要！！不要check:
        if(numCourses <= 0 || prerequisites == null || prerequisites.length == 0){
            return new int[numCourses];
        }
        这样是不对的，根据题目，prerequisites只是may，所以如果prerequisites不存在，那么我们更可以无所顾虑的完成我们需要的所有课程，所以这个check是错的。如下例
        // input: 3 []
        // output: [2,1,0]
        */
        if(numCourses < 0){
            return new int[0];
        }

        return findOrderByTopologyAndDFS(numCourses, prerequisites);
    }

    // This is a followup of 207.Course Schedule
    // in Course Schedule we use dfs to check the possibility, now we should return a possible one. In DFS based Topological Sort, we processed every vertex but in the reverse order, so we should use Stack to record vertex when we DFS and at last output Stack to make the order correct.
    private int[] findOrderByTopologyAndDFS(int numCourses, int[][] prerequisites){
        // make a source->target graph
        List<List<Integer>> graph = new ArrayList<List<Integer>>();
        for(int i = 0; i < numCourses; i++){
            graph.add(new ArrayList<Integer>());
        }
        for(int[] pre: prerequisites){
            graph.get(pre[1]).add(pre[0]);
        }

        // status
        boolean[] visited = new boolean[numCourses];
        boolean[] visiting = new boolean[numCourses];

        // store traverse ret in stack
        Stack<Integer> courseStack = new Stack<Integer>();
        int[] ret = new int[numCourses];

        // traverse all vertex
        for(int i = 0; i < numCourses; i++){
            if(!canTopoByDFS(graph, i, visited, visiting, courseStack)){
                return new int[0];// empty [], 注意这里不要返回ret，ret是数组，数组有初始值0，所以现在ret是[0...0]其中有numCourses个，不是空数组
            }
        }

        // get correct order courses
        int i = 0;
        while(!courseStack.isEmpty()){
            ret[i] = courseStack.pop();
            i++;
        }

        return ret;
    }

    private boolean canTopoByDFS(List<List<Integer>> graph, int vertexNum, boolean[] visited, boolean[] visiting, Stack<Integer> courseStack){
        // visited, back
        if(visited[vertexNum]){
            return true;
        }
        // cycle
        if(visiting[vertexNum]){
            return false;
        }

        // mark current status visiting
        visiting[vertexNum] = true;

        // processing
        List<Integer> children = graph.get(vertexNum);
        for(int i = 0; i < children.size(); i++){
            if(!canTopoByDFS(graph, children.get(i), visited, visiting, courseStack)){
                return false;
            }
        }

        // we have processed current vertex, store to stack
        courseStack.push(vertexNum);

        // mark status
        visiting[vertexNum] = false;
        visited[vertexNum] = true;

        return true;
    }
}
