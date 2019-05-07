package com.myleetcode.design.logger_rate_limiter;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Solution {
    class Logger {

        // https://leetcode.com/problems/logger-rate-limiter/discuss/83284/A-solution-that-only-keeps-part-of-the-messages
    /*
    A typical (accepted) solution is to keep a hash map of String that maps to the recent time stamp.
But this way, it needs to keep the record of the entire messages, even when the message is rare.

Alternatively, I keep a heap to get rid of the old message and set of String to keep the recent messages only. This approach would make sense when the number of logs within 10 minutes time window is not too large and when we have lots of different messages.
    */
        class MsgTime {
            String message;
            Integer timestamp;

            MsgTime(String message, Integer timestamp){
                this.message = message;
                this.timestamp = timestamp;
            }
        }

        PriorityQueue<MsgTime> msgTimePQ;
        Set<String> msgSet;

        /** Initialize your data structure here. */
        public Logger() {
            msgTimePQ = new PriorityQueue<>((msgTimeFir, msgTimeSec) -> {
                return msgTimeFir.timestamp - msgTimeSec.timestamp;
            });
            msgSet = new HashSet<>();
        }

        /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
         If this method returns false, the message will not be printed.
         The timestamp is in seconds granularity. */
        public boolean shouldPrintMessage(int timestamp, String message) {
            // everytime we first check our queue with current timestamp, if there's any msg in queue that is earlier than or equals to current timestamp-10, then it's too old, we remove them
            while(!msgTimePQ.isEmpty()){
                if(msgTimePQ.peek().timestamp <= timestamp - 10){
                    msgSet.remove(msgTimePQ.poll().message);
                }else{
                    break;
                }
            }

            // then, we have a set that all messages in it is in recent 10 seconds. now we have 2 conditions: 1 we have this msg in set, then we return false; 2 otherwise, we add it to the set and queue, and return true;
            if(msgSet.contains(message)){
                return false;
            }

            msgTimePQ.offer(new MsgTime(message, timestamp));
            msgSet.add(message);
            return true;
        }

        // naive HashMap solution:

//     Map<String, Integer> msgTimeMap;

//     /** Initialize your data structure here. */
//     public Logger() {
//         msgTimeMap = new HashMap<>();
//     }

//     /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
//         If this method returns false, the message will not be printed.
//         The timestamp is in seconds granularity. */
//     public boolean shouldPrintMessage(int timestamp, String message) {
//         if(timestamp < msgTimeMap.getOrDefault(message, 0)){
//             return false;
//         }

//         msgTimeMap.put(message, timestamp + 10);

//         return true;
//     }
    }

/**
 * Your Logger object will be instantiated and called as such:
 * Logger obj = new Logger();
 * boolean param_1 = obj.shouldPrintMessage(timestamp,message);
 */
}
