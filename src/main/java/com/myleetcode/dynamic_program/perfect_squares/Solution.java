package com.myleetcode.dynamic_program.perfect_squares;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

class Solution {
    public int numSquares(int n) {
        // return numSquaresByBFS(n);
        return numSquaresByDP(n);
    }

    // solution: BFS
    // TC: O(V + E), where V is nodes number, so it's <= n; E is edges number, it's <= root(n) for every node, so E is totally <= n*root(n).
    // https://leetcode.com/problems/perfect-squares/discuss/71488/Summary-of-4-different-solutions-(BFS-DP-static-DP-and-mathematics)
    // https://leetcode.com/problems/perfect-squares/discuss/71488/Summary-of-4-different-solutions-(BFS-DP-static-DP-and-mathematics)/73744
    // intuition: since all nums could be reused, and we are given the num is n, so we have 1 to n could be chosen. we consider every num(actually not every num) in this range as a node, then its children are all nums whose value is nodeV+(num in range [1, root(n)] and num is another num's square), for eg, we have n is 12, then we have range 1,2,3,4...12. we have the start nodeV is 0, then when we choose num from the range, we only choose those nums that is another num's square, ie the i*i where i*i <= 12. then we have nodeV+i*i is the children nodes of nodeV, let's say it's nodeU
    // we do BFS based on this graph,
    // if nodeU equals to n, then this is the least level # we need, ie the least number of perfect square numbers which sum to n
    // if nodeU larger than n, then continue next(actually because i is increasing in this loop, we could just break to reduce the RT)
    // if nodeU smaller than n, then we push the nodeU into the queue, and keep looking
    private int numSquaresByBFS(int n){
        if(n < 1){
            return 0;
        }

        Deque<Integer> nodeQueue = new ArrayDeque<>(); // all nodes waiting for check
        Set<Integer> nodeSet = new HashSet<>(); // all nodes that has benn processed, because we are doing the num sum check, so there must be many duplicates, so we use a Set to de-duplicate
        nodeQueue.offer(0);
        nodeSet.add(0);

        // BFS
        int level = 0;
        while(!nodeQueue.isEmpty()){
            int size = nodeQueue.size();
            level++;

            // level process
            for(int i = 0; i < size; i++){
                int curNode = nodeQueue.poll();

                // check current node's children: nodeV's children are the nums that could be reached by add i's square to nodeV, where i should be i >= 1 and i*i <= n
                for(int num = 1; num * num <= n; num++){
                    int childNode = curNode + num * num;

                    // check if has benn processed
                    if(nodeSet.contains(childNode)){
                        continue;
                    }
                    // remember we has processed this
                    nodeSet.add(childNode);

                    // check this child
                    if(childNode == n){
                        // if equals to n, we have find the least level # to reach result
                        return level;
                    }else if(childNode < n){
                        // we need offer this child to queue
                        nodeQueue.offer(childNode);
                    }else{
                        // we dont need this child and its children
                        // continue;
                        // because num is increasing in the for loop, so we could just break
                        break;
                    }
                }
            }
        }

        return level;
    }

    // solution: DP
    // very similar with the Coin Change Problem
    // https://leetcode.com/problems/perfect-squares/discuss/71495/An-easy-understanding-DP-solution-in-Java
    private int numSquaresByDP(int n){
        if(n < 1){
            return 0;
        }

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for(int i = 2; i <= n; i++){
            dp[i] = Integer.MAX_VALUE;
        }

        for(int i = 1; i <= n; i++){
            for(int j = 1; j * j <= i; j++){
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }

        return dp[n];

    }

}
