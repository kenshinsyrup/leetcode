package com.myleetcode.graph.reconstruct_itinerary;

import java.util.*;

class Solution {
    public List<String> findItinerary(String[][] tickets) {
        // return findItineraryByDFS(tickets);
        return findItineraryByEulerianPath(tickets);
    }

    // 解法2 Eulerian path
    /*
    Hierholzer's Algo to solve Eulerian path problem

    retPath = []
    DFS(u):
        While (u存在未被访问的边e(u,v))
            mark边e(u,v)为访问
            DFS(v)
        End
    retPath.pushLeft(u)

    */
    // Eulerian paht is much faster than naive DFS because naive DFS are try every path and if it's not valid we backtrack, so it's worst case is all paths. But Eulerian path algo we just visit every edge one time, and then we get the answer, much much better.
    // https://leetcode.com/problems/reconstruct-itinerary/discuss/78766/Share-my-solution
    private List<String> findItineraryByEulerianPath(String[][] tickets){
        Map<String, PriorityQueue<String>> graph = new HashMap<String, PriorityQueue<String>>();
        int length = tickets.length;
        for (int i = 0; i < length; i++) {
            if (!graph.containsKey(tickets[i][0])) {
                graph.put(tickets[i][0], new PriorityQueue<String>());
            }

            graph.get(tickets[i][0]).add(tickets[i][1]);
        }

        List<String> ret = new ArrayList<>();

        dfsByEulerianPath("JFK", graph, ret);

        Collections.reverse(ret);

        return ret;
    }

    public void dfsByEulerianPath(String cur, Map<String, PriorityQueue<String>> graph, List<String> ret) {
        // get all cur(start) node's arrivals(ends)
        PriorityQueue<String> arrivals = graph.get(cur);
        String arrival;
        // graph.get(cur) must exists, and must not empty
        while (arrivals != null && !arrivals.isEmpty()) {
            arrival = arrivals.poll();
            dfsByEulerianPath(arrival, graph, ret);
        }

        ret.add(cur);
    }

    // 解法1
    // intuition: Graph DFS, when choose child node, choose the smallest lexical one first, traverse through all paths try to find the valid one.
    // build graph; dfs
    // https://leetcode.com/problems/reconstruct-itinerary/discuss/78789/Java-14ms.-DFS-backtrack
    private List<String> findItineraryByDFS(String[][] tickets){
        List<String> ret = new ArrayList<>();
        // special case
        if(tickets == null || tickets.length == 0){
            return ret;
        }

        // 1 build graph, adj list graph
        Map<String, List<String>> graph = new HashMap<>();
        String start;
        String end;
        List<String> ends;
        for(String[] ticket: tickets){
            start = ticket[0];
            end = ticket[1];
            ends = graph.getOrDefault(start, new ArrayList<>());
            ends.add(end);

            graph.put(start, ends);
        }
        // ends should be sorted by lexical order, string sort is by lexical order by default
        for(List<String> v: graph.values()){
            Collections.sort(v);
        }

        // dfs, start node is "JFK". We should go through every edge and we are allowed to visit one node many times as long as it's in the edge, so we need to remove edge if we have visied it
        dfs(graph, "JFK", ret, tickets.length + 1);// n张票对应n+1个点

        return ret;
    }

    //[["JFK","KUL"],["JFK","NRT"],["NRT","JFK"]], this should return ["JFK","NRT","JFK","KUL"]. if dont check ret.size() == n, we will get sth like ["JFK", "KUL", "JFK", "NRT"] because we choose by lexical order and this answer is wrong because we could not reach JFK from KUL
    // 所以，看这里https://leetcode.com/problems/reconstruct-itinerary/discuss/78789/Java-14ms.-DFS-backtrack
    // return true means get result, ie, the full valid path
    private boolean dfs(Map<String, List<String>> graph, String start, List<String> ret, int n){
        // any recursion, first consider what's the base case to let us return

        // record
        ret.add(start); // we record our node here because we want to record normal node and the end node, if we write this inside the for(...ends) loop as usual, we will lost our end node because end node has no ends list as start node

        // base case 1, get result. the check is based on the ret.size(), so we need put the ret.add(start) beyond this
        if(ret.size() >= n){
            return true;
        }

        // base case 2: a. we did not use all of our tickets, but we reach the end point(whose outdegree is 0, ie, the start is not in the graph.keySet), then we are on the wrong path
        if(!graph.containsKey(start)){
            return false;
        }

        // then, if the start is valid, means we could traver to another airport, let's go
        List<String> ends = graph.get(start);
        String end;
        //the reason of "why we dont get the concurrent modification exception since we are traverse a list and remove its elem in the mean time" is we dont use the for(String end: ends) version.
        for(int i = 0; i < ends.size(); i++){
            end = ends.get(i);
            ends.remove(i); // remove edge, ie, remove this end from the start's adj list
            // ends.remove(end) is wrong, list could contain duplicates, we want to remove the end at i, not the possible exsiting end at its first show up position
            // BTW, end = ends.get(i); ends.remove(i); could be writen as: end = ends.remove(i);

            // dfs
            if(dfs(graph, end, ret, n)){
                return true;
            }

            // if dfs false, then we should add the end back to repair the graph, this is a backtrack.

            ends.add(i, end); // backtrack, put back edge, repair graph, must!!! put at the same index in ends, because next we will visiting i+1, if we add(end), end will be put in the end of ends, which is definitely wrong. And we could not add(0, end) because we dont want to disturb the lexical order in ends. so must at the same position.
            ret.remove(ret.size()- 1); // backtrack, remove this node from ret for now because it should not appear this position
        }

        return false;

    }

}
