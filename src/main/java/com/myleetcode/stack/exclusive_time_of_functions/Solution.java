package com.myleetcode.stack.exclusive_time_of_functions;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {
        return exclusiveTimeByStack(n, logs);
    }

    // https://leetcode.com/problems/exclusive-time-of-functions/discuss/153497/Java-solution-using-stack-wrapper-class-and-calculation-when-pop-element-from-the-stack.
    // convert log to obj
    // if obj is start func, then push to stack
    // if obj is end func, since this is a function call stack simulation, then top of stack must be its start func, so pop the start obj out to get the time interval add to ret corresponding pos; after pop, if stack is not empty, we knwo this func interval should be minused by its parent func, ie the cur top func obj
    // TC: O(N)
    // SC: O(N)
    private int[] exclusiveTimeByStack(int n, List<String> logs){
        if(n <= 0 || logs == null || logs.size() == 0){
            return new int[0];
        }

        int[] ret = new int[n];
        Deque<Log> logStack = new ArrayDeque<>();
        for(String logStr: logs){
            Log log = new Log(logStr);

            if(log.isStart){
                logStack.push(log);
            }else{
                Log startLog = logStack.pop();
                int interval = log.time - startLog.time + 1; // !!! start at 3, end at 5, interval is 5-3+1==3

                // add interval to corresponding log in ret
                ret[startLog.id] += interval;

                // minus this interval from parent log
                if(!logStack.isEmpty()){
                    ret[logStack.peek().id] -= interval;
                }
            }
        }

        return ret;

    }

    private class Log{
        int id;
        int time;
        boolean isStart;

        public Log(String logStr){
            String[] strs = logStr.split(":");

            this.id = Integer.valueOf(strs[0]);
            this.time = Integer.valueOf(strs[2]);
            this.isStart = strs[1].equals("start");
        }
    }
}
