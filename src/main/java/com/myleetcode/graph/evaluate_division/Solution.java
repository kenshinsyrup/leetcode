package com.myleetcode.graph.evaluate_division;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        //https://leetcode.com/problems/evaluate-division/discuss/171649/1ms-DFS-with-Explanations
        // special case
        if(equations == null || values == null || queries == null){
            return new double[0];
        }

        return calcEquationByGraphAndDFS(equations, values, queries);
    }

    // double directed graph with weight
    // build map with equations, vertex has children vertex and weight in path


    private double[] calcEquationByGraphAndDFS(String[][] equations, double[] values, String[][] queries){
        // graph
        Map<String, Map<String, Double>> graph = new HashMap<String, Map<String, Double>>();
        buildGraph(equations, values, graph);

        // status
        Map<String, Boolean> visiting = new HashMap<String, Boolean>();

        double[] ret = new double[queries.length];
        // dfs search
        for(int i = 0; i < queries.length; i++){
            String u = queries[i][0];
            String v = queries[i][1];

            ret[i] = calcValueByDFS(graph, u, v, visiting);
        }

        return ret;

    }

    private double calcValueByDFS(Map<String, Map<String, Double>>graph, String u, String v, Map<String, Boolean> visiting){
        // dont choose this path, avoid dead loop
        if(visiting.containsKey(u) && visiting.get(u)){
            return -1.0;
        }

        // dont contain u, invalid, -1.0
        if(!graph.containsKey(u)){
            return -1.0;
        }

        // contain v, find, return
        if(graph.get(u).containsKey(v)){
            return graph.get(u).get(v);
        }

        // 这个mark在上面两个检查contain并return之后只是为了少写一下put(u,false),因为不论何时，如果我们已经设置过u,true那么就应该在推出前设置好u,false，参考下面return ret * graph.get(u).get(key);
        // mark true
        visiting.put(u, true);

        // dfs children
        Map<String, Double> children = graph.get(u);
        double ret = -1.0;
        for(String key: children.keySet()){
            ret = calcValueByDFS(graph, key, v, visiting);
            if(ret != -1.0){
                // 重要！！！这里不可以直接return ret，因为我们还没有把visiting状态更新，如果要在这里return，那么应该把visiting状态更新的代码也在这里写一下
                // mark false
                visiting.put(u, false);
                return ret * graph.get(u).get(key);
            }
        }

        // mark false
        visiting.put(u, false);

        return ret;
    }

    // build graph很重要
    private void buildGraph(String[][] equations, double[] values, Map<String, Map<String, Double>>graph){
        String u;
        String v;
        double value;
        for(int i = 0; i < equations.length; i++){
            u = equations[i][0];
            v = equations[i][1];
            value = values[i];

            // u -> v with weight value
            graph.putIfAbsent(u, new HashMap<String, Double>());
            graph.get(u).put(v, value);

            // v -> u with weight 1.0/value
            graph.putIfAbsent(v, new HashMap<String, Double>());
            graph.get(v).put(u, 1.0/value);
        }
    }

}