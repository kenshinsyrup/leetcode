package com.myleetcode.breadth_first_search.network_delay_time;

class Solution {
    public int networkDelayTime(int[][] times, int N, int K) {
        // special case
        if(times == null){
            return -1;
        }
        
//         尝试用最短的路径访问从K开始图中所有的节点，最长的路就是最长的delay time。
//         思路：https://leetcode.com/problems/network-delay-time/discuss/109968/Simple-JAVA-Djikstra's-(PriorityQueue-optimized)-Solution-with-explanation
//         代码：https://leetcode.com/problems/network-delay-time/discuss/210698/Real-Dijjistra-Java-Concise
        
        return networkDelayTimeByDijkstra(times, N, K);
    }
    
    private int networkDelayTimeByDijkstra(int[][] times, int N, int K){
        // 图
        Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
        
//         times的length就是图中所有的起点的个数
        int len = times.length;
        
//         画图，把图中所有的起点，及起点能达到的终点及对应的距离，画入
        for(int i = 0; i < len; i++){
            graph.putIfAbsent(times[i][0], new HashMap<>());
            graph.get(times[i][0]).put(times[i][1], times[i][2]);
            // 这样是错误的，从一个起点u应该想到可以到达多个v，所以u为key的时候应该对应一个map并且这个map很可能是有多个key-value的，所以下面这样变成了一个u对应了 一个只包含了一个key-value的map，也就是变成了一个u只能到一个v。
            /*
//             当前起点能到达的所有的终点及对应的距离
            Map<Integer, Integer> vAndDistance = new HashMap<>();
            vAndDistance.put(times[i][1], times[i][2]);
//             起点和必要的元素画入
            graph.put(times[i][0], vAndDistance);
            */
        }
        
//         使用pq，比较方法为返回第一个元素值较小者所在的数组。该pq中保存的是一个数据，数组的第一个元素为 图中的某个节点, 第二个元素为 从K到达目前的的节点的距离
        Queue<int[]> pq = new PriorityQueue<>((int[] a, int[] b)->{return a[1] - b[1];});
        
//      起始，把起点K放入，从K到K距离是0   
        pq.add(new int[]{K, 0});
        
//         记录已经访问过的点，由于使用来pq，那么访问过的点肯定不用再访问了，因为第一次访问的时候的距离是最短的
        Set<Integer> seen = new HashSet<>();
        
//         最终结果
        int finalDistance = 0;
        
        // 遍历pq
        while(!pq.isEmpty()){
            int[] cur = pq.poll();
            int curNode = cur[0];
            int curDistance = cur[1];
            
//          如果当前节点已经通过某种方式被访问过了，那么目前到达他的距离肯定不是最短的，所以不用考虑了直接下一个
            if(seen.contains(curNode)){
                continue;
            }
            
            // 没有被访问过的节点，本次被访问了，加入seen
            seen.add(curNode);
            
//             最终的结果。我们在遍历整个pq的过程中，越靠后被访问的点肯定离K越远，所以每次更新finalDistance为curDistance即可
            finalDistance = curDistance;
            
//             计数
            N--;
            
            if(graph.containsKey(curNode)){
                //             当前节点作为u，在graph中，能访问到的所有的 v 和 对应的记录
                Map<Integer, Integer> vAndDistance = graph.get(curNode);
                Set<Integer> vs = vAndDistance.keySet();
            
                for(int v: vs){
//                 能从当前节点作为u到达的点v，距离是 从u到v的距离 + 从K到u的距离curDistance。推入pq。
                    pq.add(new int[]{v, curDistance + vAndDistance.get(v)});
                }   
            }
        }
        
//         如果最后计数的结果发现并不是所有的node都被访问过，那么说明有一些node不能从K到达
        if(N != 0){
            return -1;
        }
        return finalDistance;   
    }
}