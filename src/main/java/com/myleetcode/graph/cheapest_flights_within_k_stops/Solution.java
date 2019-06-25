package com.myleetcode.graph.cheapest_flights_within_k_stops;

import java.util.*;

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        return findCheapestPriceByDijkstra(n, flights, src, dst, K);
    }

    // intuition: the flight Graph has directions(but no need to be DAG) and non-negative weight on edge, we need to caculate the lowest cost so Dijkstra Algo is a good sol here
    // BUT there's a big difference with the traditional Dijkstra Algo
    // 1 is we dont use the processed Set to skip already processed nodes, the K will let us end the while loop
    // 2 is we dont relax the dist into the distArr, because least weight may have more than K stops, so we just offer all routes to the PQ and process all of them
    // 3 we need keep the number of stops in the int[] elem in PQ
    /*
The key difference with the classic Dijkstra algo is, we don't maintain the global optimal distance to each node, i.e. ignore below optimization:
alt ← dist[u] + length(u, v)
if alt < dist[v]:
Because there could be routes which their length is shorter but pass more stops, and those routes don't necessarily constitute the best route in the end. To deal with this, rather than maintain the optimal routes with 0..K stops for each node, the solution simply put all possible routes into the priority queue, so that all of them has a chance to be processed. IMO, this is the most brilliant part.
And the solution simply returns the first qualified route, it's easy to prove this must be the best route.
    */
    // TC: O(M * logN)
    // SC: O(N)
    private int findCheapestPriceByDijkstra(int n, int[][] flights, int src, int dst, int K){
        if(flights == null || flights.length == 0 || flights[0] == null || flights[0].length == 0){
            return -1;
        }
        if(n <= 0){
            return -1;
        }
        if(src < 0 || dst < 0 || K < 0){
            return -1;
        }

        if(src == dst){
            return 0;
        }

        // 0 build G
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for(int[] flight: flights){
            graph.putIfAbsent(flight[0], new ArrayList<>());
            graph.get(flight[0]).add(new int[]{flight[1], flight[2]});
        }

        // 1 init
        // PQ for min dist to S
        PriorityQueue<int[]> nodeInfoPQ = new PriorityQueue<>(new Comparator<int[]>(){
            public int compare(int[] nodeInfo1, int[] nodeInfo2){
                return nodeInfo1[1] - nodeInfo2[1];
            }
        });

        // 2 caculate distArr
        nodeInfoPQ.offer(new int[]{src, 0, 0});
        while(!nodeInfoPQ.isEmpty()){
            // min dist to S node
            int[] nodeInfo = nodeInfoPQ.poll();
            int node = nodeInfo[0];
            int distUToS = nodeInfo[1];
            int stops = nodeInfo[2];

            // check 1 if takes more then K stops to reach this node, then it and its neighbors are all not valid
            if(stops > K + 1){
                continue;
            }

            // check 2 if reach dst, return cost
            if(node == dst){
                return distUToS;
            }

            // neighbors
            List<int[]> neighborNodeInfos = graph.getOrDefault(node, new ArrayList<>());
            for(int[] neighborNodeInfo: neighborNodeInfos){
                // put all possible routes into PQ
                nodeInfoPQ.offer(new int[]{neighborNodeInfo[0], distUToS + neighborNodeInfo[1], stops + 1});
            }
        }

        return -1;
    }
}
