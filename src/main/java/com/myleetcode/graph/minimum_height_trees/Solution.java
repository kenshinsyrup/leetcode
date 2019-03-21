package com.myleetcode.graph.minimum_height_trees;

import java.util.*;

class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        // return findMinHeightTreesByBFS(n, edges);// TLE
        // return findMinHeightTreesByIndegree(n, edges);// pass 173ms, 20%
        return findMinHeightTreesByIndegreeAndBFS(n, edges);
    }

    // 针对findMinHeightTreesByIndegree这个利用indegree的写法，还可以换一个更像用Queue做BFS的方式来完成
    // https://leetcode.com/problems/minimum-height-trees/discuss/76129/Share-my-BFS-JAVA-code-using-degree-with-explanation-which-beats-more-than-95
    private List<Integer> findMinHeightTreesByIndegreeAndBFS(int n, int[][] edges){
        List<Integer> ret = new ArrayList<>();

        // special case. 这个special case特别烦
        if(n == 1){
            ret.add(0);
            return ret;
        }

        // build graph
        int[] indegree = new int[n];
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for(int i = 0; i < edges.length; i++){
            graph.putIfAbsent(edges[i][0], new ArrayList<>());
            graph.get(edges[i][0]).add(edges[i][1]);
            indegree[edges[i][0]]++;

            graph.putIfAbsent(edges[i][1], new ArrayList<>());
            graph.get(edges[i][1]).add(edges[i][0]);
            indegree[edges[i][1]]++;
        }

        // outest leaves
        Queue<Integer> leafQ = new ArrayDeque<>();
        for(int i = 0; i < indegree.length; i++){
            if(indegree[i] == 1){//leaf
                leafQ.offer(i);
            }
        }

        while(!leafQ.isEmpty()){
            ret = new ArrayList<>();
            int size = leafQ.size();

            for(int i = 0; i < size; i++){
                int leaf = leafQ.poll();

                // 删除该leaf，也就是减1他的indegree，然后存入ret，我们每层的leaf都存入ret，如果quque还没有空，说明该层leaf还有靠近树心的子，那么把ret重置，以此循环.
                indegree[leaf]--;
                ret.add(leaf);

                for(int child: graph.get(leaf)){
                    indegree[child]--; // 无向图的边相当于双向边，我们要让leaf的indegree减少1，那么也一定要减少他的所有子的一个indegree
                    if(indegree[child] == 1){ // 如果子变成了新leaf，就queue
                        leafQ.offer(child);
                    }
                }
            }
        }

        return ret;
    }



    // 有个机智的解法，使用类似与拓扑排序的思路，每次处理indegree最小的node，因为是连通无向图，所以每次我们找到所有indegree为1的node，这些就都是leaf node，我们删除他们，再找所有新的indegree为1的node，他们是新leaf node，删除他们，以此类推。
    // 直到最后有两种情况：1剩余一个node，这个就是中心node 2剩余两个node，这两个就是中心node。不存在环所以不可能剩余比2更多个node，因为没有环的情况下，如果有3个node，那么肯定有两个是leaf可以删掉。
    // 这样我们剩余的节点是最长的路径的最中间的一个或者两个node，就是答案。proff：https://leetcode.com/problems/minimum-height-trees/discuss/200941/topic
    //https://leetcode.com/problems/minimum-height-trees/discuss/76055/Share-some-thoughts
    private List<Integer> findMinHeightTreesByIndegree(int n, int[][] edges){
        List<Integer> ret = new ArrayList<>();

        // special case
        if(n == 1){
            ret.add(0);
            return ret;
        }


        // build graph
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for(int i = 0; i < edges.length; i++){
            graph.putIfAbsent(edges[i][0], new ArrayList<>());
            graph.get(edges[i][0]).add(edges[i][1]);

            graph.putIfAbsent(edges[i][1], new ArrayList<>());
            graph.get(edges[i][1]).add(edges[i][0]);
        }

        // outest leaves
        List<Integer> leaves = new ArrayList<>();
        for(int k: graph.keySet()){
            if(graph.get(k).size() == 1){
                leaves.add(k);
            }
        }

        // add all nodes to ret, then deletes leaves layer by layer
        for(int i = 0; i < n; i++){
            ret.add(i);
        }

        List<Integer> temp = new ArrayList<>();
        while(ret.size() > 2){
            temp = new ArrayList<>();

            // deletes all leaves from ret
            ret.removeAll(leaves);

            // find all children of leaves, if after delete leaves their indegree is 1, then they are new leaves
            for(int i = 0; i < leaves.size(); i++){
                int leaf = leaves.get(i);

                for(int child: graph.get(leaf)){
                    // stupid java. remove(Object o)是移除value，remove(int i)是移除index，这里的leaf是int，所以如果直接remove(leaf)会容易出现删除不存在的index导致越界问题
                    graph.get(child).remove(Integer.valueOf(leaf)); // remove leaf node from its child's neighbor field.

                    if(graph.get(child).size() == 1){ // if indegree is 1, then it's next layer's leaf
                        temp.add(child);
                    }
                }
            }

            leaves = temp;
            // temp.clear(); 这里如果clear，上面的leaves由于不是深拷贝，也就被clear了，还是用原始的写法，给temp再new一个空的 temp = new ArrayList<>();这样的话这行代码放到while开头比较好
        }

        return ret;
    }


    // 这个解法理论正确，过大的输入TLE
    // TLE
    // intuition: traverse every node, for every node we use it as root for one time, then with this "root" we start a classic BFS to find it's height.
    // Keep a min_height, if any node has the same with it, record to list, if any node is smaller than min_height, empty the list and record again
    private List<Integer> findMinHeightTreesByBFS(int n, int[][] edges){
        List<Integer> ret = new ArrayList<>();

        // special case
        // no edges
        if(edges == null || edges.length == 0){
            if(n == 1){
                ret.add(n-1);
            }
            return ret;
        }

        // build graph
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for(int i = 0; i < edges.length; i++){
            graph.putIfAbsent(edges[i][0], new ArrayList<>());
            graph.get(edges[i][0]).add(edges[i][1]);

            graph.putIfAbsent(edges[i][1], new ArrayList<>());
            graph.get(edges[i][1]).add(edges[i][0]);
        }

        Queue<Integer> nodeQ = new LinkedList<>();
        int min = Integer.MAX_VALUE;
        // check every node as root node, caculate it's height
        for(int root: graph.keySet()){
            nodeQ.add(root);
            int height = 0;
            boolean[] visited = new boolean[n];
            while(!nodeQ.isEmpty()){
                // level by level, first keep size
                int size = nodeQ.size();
                // second process this size number nodes, this is a level
                for(int i = 0; i < size; i++){
                    int currentN = nodeQ.poll();
                    visited[currentN] = true;
                    // push next level nodes to queue
                    for(int child: graph.get(currentN)){
                        if(!visited[child]){
                            nodeQ.add(child);
                        }
                    }
                }

                height++;
            }

            if(height == min){
                ret.add(root); // update ret
            }else if(height < min){ // update min and add current node to ret
                min = height;
                // ret.removeAll(); // stupid java
                ret.clear();
                ret.add(root);
            }
        }

        return ret;

    }
}
