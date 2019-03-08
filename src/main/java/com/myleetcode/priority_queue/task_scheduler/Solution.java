package com.myleetcode.priority_queue.task_scheduler;

import java.util.*;

class Solution {
    public int leastInterval(char[] tasks, int n) {
        // special case
        if(tasks == null || tasks.length == 0){
            return 0;
        }

        return leastIntervalByPQ(tasks, n);
    }

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

    // priority queue
    private int leastIntervalByPQ(char[] tasks, int n){
        // taks and its amount
        Map<Character, Integer> taskMap = new HashMap<Character, Integer>();
        // fullfill the taskMap
        int num = 0;
        for(int i = 0; i < tasks.length; i++ ){
            num = taskMap.getOrDefault(tasks[i], 0);
            taskMap.put(tasks[i], num + 1);
        }

        // priority queue, anonymouse new comparator class has too many codes to write, learn to write in lambda form
        PriorityQueue<Map.Entry<Character, Integer>> pq = new PriorityQueue<>((a, b) -> {
            // 原答案按照的是注释掉的部分写法，值不同按值倒序，值相同按key升序。
            // if(a.getValue() != b.getValue()){ // if a, b value(frequency) is not equal, descending by value(frequency)
            //     return b.getValue() - a.getValue();
            // }else{
            //     // if a, b value(frequency) is equal, ascending by key
            //     return a.getKey() - b.getKey();
            // }

            // 但是感觉我们只需要考虑值，所以只按值倒序来pq应该就可以。
            return b.getValue() - a.getValue();
        });
        //fullfill pq
        pq.addAll(taskMap.entrySet());

        int count = 0; // count intervals
        while(!pq.isEmpty()){// every this while loop, is a process loop

            int k = n + 1; // Since the interval is 2, the actual task we can execute, is the one we're executing right now, plus the two in the interval. Therefore, it is n+1

            // a waiting list
            List<Map.Entry<Character, Integer>> waitingList = new ArrayList<>();

            // execute k nums tasks that most frequent
            while(k > 0 && !pq.isEmpty()){
                // execute this task
                Map.Entry<Character, Integer> task = pq.poll();

                // reduce its total num
                task.setValue(task.getValue() - 1);
                // add to waitinglist to next process loop, if the task isnot exhausted, it wil be added to pq again with the new num.
                waitingList.add(task);

                k--; //successfully executed a task in this process loop
                count++; // successfully executed a task in total process
            }

            // add not exhausted tasks to pq again, repeat process loop
            for(int i = 0; i < waitingList.size(); i++){
                if(waitingList.get(i).getValue() > 0){
                    pq.add(waitingList.get(i));
                }
            }

            // if pq is not empty, then we should lookup if we need "idle", ie, if the k > 0, if k > 0, means we need k number "idle" in this process loop, so add it to count.
            if(!pq.isEmpty()){
                count += k; // because k cannot smaller than 0, we could just plus it
            }
        }

        return count;

    }

}