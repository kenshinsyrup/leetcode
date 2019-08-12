package com.myleetcode.greedy.rearrange_string_k_distance_apart;

import java.util.*;

class Solution {
    public String rearrangeString(String s, int k) {
        return rearrangeStringByGreedyAndPQ(s, k);
    }

    // https://leetcode.com/problems/rearrange-string-k-distance-apart/discuss/83192/Java-7-version-of-PriorityQueue-O(nlogn)-with-comments-and-explanations
    // TC: O(N * log26) => O(N)
    // SC: O(N)
    private String rearrangeStringByGreedyAndPQ(String str, int k){
        if(str == null || str.length() == 0 || k < 0){
            return "";
        }

        int len = str.length();
        Map<Character, Integer> charNumMap = new HashMap<>();

        // count char num
        for(int i = 0; i < len; i++){
            charNumMap.put(str.charAt(i), charNumMap.getOrDefault(str.charAt(i), 0) + 1);
        }

        // use a MaxHeap to guarantee we could get the char with most freq
        PriorityQueue<Map.Entry<Character, Integer>> maxHeap = new PriorityQueue<>(new Comparator<Map.Entry<Character, Integer>>(){
            public int compare(Map.Entry<Character, Integer> e1, Map.Entry<Character, Integer> e2){
                return e2.getValue() - e1.getValue();
            }
        });
        maxHeap.addAll(charNumMap.entrySet());

        // traverse MaxHeap, everytime we peek the max freq char, put into a waiting queue and then append char to sb. if not reach gap k, continue; if reached, we put the first char in waiting queue back into MaxHeap
        Deque<Map.Entry<Character, Integer>> waitQueue = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        while(!maxHeap.isEmpty()){
            // poll most freq
            Map.Entry<Character, Integer> curEntry = maxHeap.poll();
            curEntry.setValue(curEntry.getValue() - 1);

            // offer to wait queue
            waitQueue.offer(curEntry);

            // append to sb
            sb.append(curEntry.getKey());

            // if gap dont reach k, continue
            if(waitQueue.size() < k){
                continue;
            }

            // gap is enough, put back most front entry to heap
            Map.Entry<Character, Integer> backEntry = waitQueue.poll();
            if(backEntry.getValue() > 0){
                maxHeap.offer(backEntry);
            }
        }

        // if not enough char into sb, ""
        if(sb.length() != len){
            return "";
        }
        return sb.toString();
    }
}
