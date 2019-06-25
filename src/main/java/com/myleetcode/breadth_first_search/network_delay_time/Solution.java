package com.myleetcode.breadth_first_search.network_delay_time;

import java.util.*;

class Solution {
    public int networkDelayTime(int[][] times, int N, int K) {
        // return networdDelayTimeBySSSPonDAG(times, N, K); // Not a solution of this problem, SSSPonDAG

        return networdDelayTimeByDijkstra(times, N, K); // Dijkstra
        // return networdDelayTimeByBellmanFord(times, N, K); // Bellman Ford
    }

    // intuition: classic SSSP problem
    // we need to get the dist[] from S to all other nodes, where dist[] stores he Shortest Path. then we just need to get the Max from the dist[], if its Integer.MAX_VALUE means impossible; if not, it is the max delay time
    // For SSSP problem, we have 3 solutions: 1 SSSPonDAG; 2 Dijkstra's Algo; 3 Bellman Ford's Algo
    // if this G is a DAG so we could use SSSPonDAG to solve it with RT O(N + M)
    // if G has no negative weight edge, we could use Dijkstra's Algo to solve it with RT O(M * logN)
    // for all G, we could use Bellman Ford's Algo to slove it with RT (M * N)
    // here we write all 3 three slolutions for reference
    // By the way, nodes are from 1 - N, and we have N nodes, and times[][] valid idx from 1 so be careful about this

    // 1: Dijkstra's Algo
    // TC: O(M*logN)
    // SC: O(N + M)
    // Best solution for This Problem, because we know that this G is no need to be a DAG and none of the edges' weight is negative
    private int networdDelayTimeByDijkstra(int[][] times, int N, int K){
        // if times invalid, means no valid connections in G, -1
        if(times == null || times.length == 0 || times[0] == null || times[0].length == 0){
            return -1;
        }
        // if N or K invalid, -1
        if(N <= 0 || K > N){
            return -1;
        }

        // 0 build G, Key is U, Value is V and distance from U to V
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for(int i = 0; i < times.length; i++){
            int[] timeEdge = times[i];

            graph.putIfAbsent(timeEdge[0], new ArrayList<>());
            graph.get(timeEdge[0]).add(new int[]{timeEdge[1], timeEdge[2]});
        }

        // 1 init
        // init the distArr
        int[] distArr = new int[N + 1];
        for(int i = 0; i <= N; i++){
            distArr[i] = Integer.MAX_VALUE;
        }
        distArr[K] = 0;

        // init the Set
        Set<Integer> processedNodeSet = new HashSet<>();

        // init the PQ
        // nodeAndDistPQ helps us store the nodes ordered by dist to S ascending
        PriorityQueue<int[]> nodeInfoPQ = new PriorityQueue<>(new Comparator<int[]>(){
            public int compare(int[] nodeInfo1, int[] nodeInfo2){
                return nodeInfo1[1] - nodeInfo2[1];
            }
        });
        // start vertex S is K, dist to S is 0
        nodeInfoPQ.offer(new int[]{K, 0});

        // 2 get distArr[]
        while(!nodeInfoPQ.isEmpty()){
            // pick the min dist node
            int[] nodeInfo = nodeInfoPQ.poll();
            int nodeU = nodeInfo[0];

            // check and add to set
            if(processedNodeSet.contains(nodeU)){
                continue;
            }
            processedNodeSet.add(nodeU);

            List<int[]> nodeVInfos = graph.getOrDefault(nodeU, new ArrayList<>());
            for(int[] nodeVInfo: nodeVInfos){
                int nodeV = nodeVInfo[0];
                int distUtoV = nodeVInfo[1];

                // relax the dist from nodeV to S
                relax(nodeU, nodeV, distUtoV, distArr);

                // put the nodeV with its dist to S to PQ
                nodeInfoPQ.offer(new int[]{nodeV, distArr[nodeV]});
            }
        }

        // 3 get the max delay
        int maxDelay = -1;
        for(int i = 1; i <= N; i++){ // careful, i from 1 not 0
            if(distArr[i] == Integer.MAX_VALUE){
                return -1;
            }

            maxDelay = Math.max(maxDelay, distArr[i]);
        }

        return maxDelay;

    }

    // 2 Bellman Ford's Algo
    // Pros: easy to write; most general, could sovle all problems of SSSP, no matter the G is DAG or not, no matter the G has negative weight or not
    // Cons: RT is O(M*N) > Dijkstra > SSSPonDAG
    // TC: O(M * N)
    // SC: O(M + N)
    private int networdDelayTimeByBellmanFord(int[][] times, int N, int K){
        // if times invalid, means no valid connections in G, -1
        if(times == null || times.length == 0 || times[0] == null || times[0].length == 0){
            return -1;
        }
        // if N or K invalid, -1
        if(N <= 0 || K > N){
            return -1;
        }

        // 0 build G, Key is U, Value is V and distance from U to V
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for(int i = 0; i < times.length; i++){
            int[] timeEdge = times[i];

            graph.putIfAbsent(timeEdge[0], new ArrayList<>());
            graph.get(timeEdge[0]).add(new int[]{timeEdge[1], timeEdge[2]});
        }

        // 1 init the distArr
        int[] distArr = new int[N + 1];
        for(int i = 0; i <= N; i++){
            distArr[i] = Integer.MAX_VALUE;
        }
        distArr[K] = 0;

        // 2 get distArr
        // loop N-1 times, this forloop cost O(N), the process all Vertex and neighbors nest-forloop cost O(N + M), so totally O(N * (N + M)), because generally, M >> N, so we could say cost O(N * M)
        for(int i = 1; i < N; i++){

            // process all Vertex
            for(int node = 1; node <= N; node++){
                // process all neighbors
                List<int[]> nodeVInfos = graph.getOrDefault(node, new ArrayList<>());
                for(int[] nodeVInfo: nodeVInfos){
                    relax(node, nodeVInfo[0], nodeVInfo[1], distArr);
                }
            }
        }

        // 3 get the max delay
        int maxDelay = -1;
        for(int i = 1; i <= N; i++){ // careful, i from 1 not 0
            if(distArr[i] == Integer.MAX_VALUE){
                return -1;
            }

            maxDelay = Math.max(maxDelay, distArr[i]);
        }

        return maxDelay;

    }

    // !!! this problem dont say the graph is a DAG which means may have cycles in the problem, so could not use this solution, just write for reference
    // 3: Best RT Solution for DAG, SSSPonDAG
    // TC: O(N + M)
    // SC: O(N + M)
    private int networdDelayTimeBySSSPonDAG(int[][] times, int N, int K){
        // if times invalid, means no valid connections in G, -1
        if(times == null || times.length == 0 || times[0] == null || times[0].length == 0){
            return -1;
        }
        // if N or K invalid, -1
        if(N <= 0 || K > N){
            return -1;
        }

        // 0 build G, Key is U, Value is V and distance from U to V
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for(int i = 0; i < times.length; i++){
            int[] timeEdge = times[i];

            graph.putIfAbsent(timeEdge[0], new ArrayList<>());
            graph.get(timeEdge[0]).add(new int[]{timeEdge[1], timeEdge[2]});
        }

        // 1 init the distArr
        int[] distArr = new int[N + 1];
        for(int i = 0; i <= N; i++){
            distArr[i] = Integer.MAX_VALUE;
        }
        distArr[K] = 0;

        // 2 get distArr
        // 2.1 Topological Sort the DAG, store the sorted nodes into Stack
        Deque<Integer> tsStack = new ArrayDeque<>();
        boolean[] visited = new boolean[N + 1];
        for(int i = 1; i <= N; i++){
            if(!visited[i]){
                topologicalSort(graph, times, i, visited, tsStack);
            }
        }

        // 2.2 caculate dist
        while(!tsStack.isEmpty()){
            int nodeU = tsStack.pop();

            List<int[]> nodeVInfos = graph.getOrDefault(nodeU, new ArrayList<>());
            for(int[] nodeVInfo: nodeVInfos){
                relax(nodeU, nodeVInfo[0], nodeVInfo[1], distArr);
            }
        }

        // 3 get the max
        int maxDelay = -1;
        for(int i = 1; i <= N; i++){
            if(distArr[i] == Integer.MAX_VALUE){
                return -1;
            }
            maxDelay = Math.max(maxDelay, distArr[i] );
        }

        return maxDelay;
    }

    // relax the distArr[]
    private void relax(int nodeU, int nodeV, int distUtoV, int[] distArr){
        // use minus operation to avoid outofboundary of int
        if(distArr[nodeU] < distArr[nodeV] - distUtoV){
            distArr[nodeV] = distArr[nodeU] + distUtoV;
        }
    }

    // topological sort for a DAG
    private void topologicalSort(Map<Integer, List<int[]>> graph, int[][] times, int node, boolean[] visited, Deque<Integer> tsStack){
        visited[node] = true;

        List<int[]> nodeVInfos = graph.getOrDefault(node, new ArrayList<>());
        for(int[] nodeVInfo: nodeVInfos){
            if(visited[nodeVInfo[0]]){
                continue;
            }

            topologicalSort(graph, times, nodeVInfo[0], visited, tsStack);
        }

        tsStack.push(node);
    }

}