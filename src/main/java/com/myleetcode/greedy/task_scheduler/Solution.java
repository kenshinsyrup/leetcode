package com.myleetcode.greedy.task_scheduler;

import java.util.*;

class Solution {
    public int leastInterval(char[] tasks, int n) {
        return leastIntervalByPQ(tasks, n);
    }

    /*
    出错点:
    1 思路: 想到这是一个：按指定距离分组问题， 即358题模式，使用Greedy思路，MaxHeap配合waitQueue处理
    2 思路：与358题的区别在于，这个题一次要填充的距离固定，如maxHeap不够用，则使用idle填充，所以计算出填充量k=n+1是一个重点
    */

    // this seems like the problem: 358. Rearrange String k Distance Apart
    // intuition: so we should do as many as tasks we could before a idle comes, eg: [A,A,A,B,B,C], we should do like this[A,B,C]->idle1->[A,B]->idle2->[A], so total intervals is (idle1 + idle2)*n, where idle is the same tasks on its two sides.
    // but this is not correct.(why? read the solution part of leectcode).

    // 这个题的最难点在于题意
    // 题意解释：https://leetcode.com/problems/task-scheduler/discuss/104500/Java-O(n)-time-O(1)-space-1-pass-no-sorting-solution-with-detailed-explanation
    // 实现思路：https://leetcode.com/problems/task-scheduler/discuss/104501/Java-PriorityQueue-solution-Similar-problem-Rearrange-string-K-distance-apart

    // 对于[A,A,A,B,B,B] n=2，总时间是8的解释(对于下面的算法)
    // stage0 pq(A3, B3)
    // stage1 pq(A3, B3) => k == 1, count == 2, waitlingList<A2,B2>, pq(A2, B2), count == 3. 此时我们相当于得到了第一个执行列表A,B,idle
    // stage2 pq(A2,B2) => k ==1, count == 2 + 3 == 5, waitlingList<A1,B1>, pq(A1,B1), count == 6.此时我们相当于得到了第二个执行列表A,B,idle.结合第一个的总执行列表就是A,B,idle,A,B,idle
    // stage3 pq(A1,B1) => k == 1, count == 2 + 6 == 8,waitlingList空,pq空。结束。此时我们得到了第三个执行列表A,B.结合第一个第二个，总执行列表为A,B,idle,A,B,idle,A,B. 总共8

    // priority queue act as MapHeap
    private int leastIntervalByPQ(char[] tasks, int n){
        if(tasks == null || tasks.length == 0){
            return 0;
        }

        int len = tasks.length;
        // taks and its amount
        Map<Character, Integer> taskNumMap = new HashMap<>();
        for(int i = 0; i < len; i++ ){
            taskNumMap.put(tasks[i], taskNumMap.getOrDefault(tasks[i], 0) + 1);
        }

        // priority queue, order tasks entry by num descending
        PriorityQueue<Map.Entry<Character, Integer>> maxHeap = new PriorityQueue<>(new Comparator<Map.Entry<Character, Integer>>(){
            public int compare(Map.Entry<Character, Integer> e1, Map.Entry<Character, Integer> e2){
                return e2.getValue() - e1.getValue();
            }
        });
        //fullfill pq
        maxHeap.addAll(taskNumMap.entrySet());

        int count = 0; // count intervals
        // a waiting list
        Deque<Map.Entry<Character, Integer>> waitQueue = new ArrayDeque<>();
        while(!maxHeap.isEmpty()){// every this while loop, is a process loop

            int k = n + 1; // !!! Since the interval is n, the actual task we can execute, is the one we're executing right now, plus the n in the interval. Therefore, it is n+1

            // execute k nums tasks that most frequent
            while(k > 0 && !maxHeap.isEmpty()){
                // execute this task
                Map.Entry<Character, Integer> task = maxHeap.poll();

                // reduce its total num
                task.setValue(task.getValue() - 1);

                // add to waitQueue to next process loop, if the task is not exhausted
                if(task.getValue() > 0){
                    waitQueue.offer(task);
                }

                k--; //successfully executed a task in this process loop
                count++; // successfully executed a task in total process
            }

            // add not exhausted tasks to pq again, repeat process loop
            while(!waitQueue.isEmpty()){
                maxHeap.offer(waitQueue.poll());
            }

            // if pq is not empty, then we should lookup if we need "idle", ie, if the k > 0, if k > 0, means we need k number "idle" in this process loop, so add it to count.
            if(!maxHeap.isEmpty()){
                count += k; // because k cannot smaller than 0, we could just plus it
            }
        }

        return count;

    }

}