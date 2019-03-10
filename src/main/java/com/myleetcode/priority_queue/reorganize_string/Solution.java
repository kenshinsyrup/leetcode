package com.myleetcode.priority_queue.reorganize_string;

import java.util.*;

class Solution {
    public String reorganizeString(String S) {
        // special case
        if(S == null || S.length() == 0){
            return "";
        }
        if(S.length() == 1){
            return S;
        }

        return reorganizeStringByPQ(S);
    }

    // intuition: similiar with 621. Task Scheduler, but, dont know how to solve this.

    // https://leetcode.com/problems/reorganize-string/discuss/128907/Java-solution-99-similar-to-358
    // 这个相当于k==1的Task Scheduler问题。主要的重点在于waiting queue的使用，我们为了实现相邻char不同，做了三步：1把char-frequency pair按照freq降序放入了pq 2遍历pq，每次poll一个pair出来，char存入sb，value降低1，把这个pair存入waiting queue，然后我们把除了最后一个pair（也就是我们刚加入waiting queu的这个pair）在queue里的东西都再次存入pq（出发heapify），这样的话，唯一一个与sb的最后一个char相同的pair在queue而不再pq，那么下一轮从pq取pair出来的时候肯定取到的是和这个char不同的最高频的char 3pq遍历结束，我们只需要判断sb和source string的长度：相同，则说明source string可以被reorginaze成需要的样子，那么sb.toString()；不同，则说明不可以，那么说明有一个char肯定不符合要求，导致其最后留在了waiting queue，则没有被sb append到，所以reorgnize失败，返回""
    // "abb"
    // init
    // pq:{{b:2}, {a:1}}  sb:""  q:{}
    // stage1
    // pq:{{b:2}, {a:1}} => pq:{{a:1}} sb:"b" q:{{b:1}} => pq:{{a:1}} q:{{b:1}}
    // stage2
    // pq:{{a:1}} => pq:{} sb:"ba" q:{{b:1}, {a:0}} => pq:{{b:1}} q:{{a:0}}
    // stage3
    // pq:{{b:1}} => pq:{} sb:"bab" q:{{a:0}} => pq:{} q:{{a:0}}
    // stage4
    // pq:{}} 此时pq为空，while循环退出。这种可以正确reorganize的情况下，q里面最后剩余的内容的value肯定都是0的
    // sb.length() == "abb".length(), return sb.toString() => "bab"

    // "aaab"
    // init
    // pq:{{a:3}, {b:1}} sb:"" q:{}
    // stage1
    // pq:{{a:3}, {b:1}} => pq:{{b:1}} sb:"a" q:{{a:2}} => pq:{{b:1}} q:{{a:2}}
    // stage2
    // pq:{{b:1}} => pq{} sb:"ab" q:{{a:2}, {b:0}} => pq:{{a:2}} q:{{b:0}}
    // stage3
    // pq:{{a:2}} => pq{} sb:"aba" q:{{b:0},{a:1}} => pq:{} q:{{a:1}}
    // stage4
    // pq:{} 退出while. 注意stage3，q中poll出的{b:0}因为value == 0是不会被add到pq中的，所以退出while循环后，q中还有value非0的内容，那么sb的长度肯定小于source string
    private String reorganizeStringByPQ(String s){
        // char-frequency pair
        Map<Character, Integer> charFreq = new HashMap<>();
        for(int i = 0; i < s.length(); i++){
            int freq = charFreq.getOrDefault(s.charAt(i), 0);
            charFreq.put(s.charAt(i), freq + 1);
        }

        // put this to PQ
        PriorityQueue<Map.Entry<Character, Integer>> pq = new PriorityQueue<>((a, b) -> {
            return b.getValue() - a.getValue();// descending
        });
        pq.addAll(charFreq.entrySet());

        // StringBuilder to store ret
        StringBuilder sb = new StringBuilder();

        // queue to store the polled entry, everytime we poll an entry from pq, and append it to the sb, and reduce its value by 1, then put into the waiting queue.
        // must use queue because we want to add all entries to pq except the one that just be polled out of the pq, ie the one at the tail of the queue. Why? Because this is how we to reorgnize the chars to make no adjacent chars are the same.
        Queue<Map.Entry<Character, Integer>> waitingQ = new LinkedList<>();

        // try to reorganize
        while(!pq.isEmpty()){
            Map.Entry<Character, Integer> curEntry = pq.poll();
            sb.append(curEntry.getKey());
            curEntry.setValue(curEntry.getValue() - 1);
            waitingQ.add(curEntry);

            // if queue.size() > 1, then we need add entries to pq again until queue.size <= 1. this is because we must append the most freq entry to sb, and we should avoid the same entry to be adjacent, so we only need just one entry (ie, the last one in the sb, ie, the curEntry) in the queue.
            while(waitingQ.size() > 1){
                Map.Entry<Character, Integer> waitingEntry = waitingQ.poll();
                if(waitingEntry.getValue() > 0){
                    // we only need to not exhausted entry to pq.
                    pq.add(waitingEntry);
                }
            }
        }

        // 由于使用了while(waitingQ.size() > 1){}这个判断，所以在面对无法合法reorganize的string的时候，肯定有多出来的那个char还在queue里面，所以肯定sb的长度不等于s
        return sb.length() == s.length() ? sb.toString() : "";

    }
}