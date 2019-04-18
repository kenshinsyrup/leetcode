package com.myleetcode.priority_queue.top_k_frequent_words;

import java.util.*;

class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        return topKFrequentByPQ(words, k);
    }

    // TC: O(N*log(K))
    // SC: O(N)
    // intuition: this looks like Bucket Sort but it has detail requirements about the order of elem in bucket, so should not use bucket sort. maybe it's better to use a PQ, we keep a k-length PQ with a comparator that order by freq first then alphabeta, every time we cost log(K) time to insert elem to pq, we need to try N times, so its O(N*logK)
    private List<String> topKFrequentByPQ(String[] words, int k){
        List<String> ret = new ArrayList<>();

        if(words == null || words.length == 0 || k <= 0){
            return ret;
        }

        // map to caculate freq
        Map<String, Integer> wordNumMap = new HashMap<>();
        for(String word: words){
            wordNumMap.put(word, wordNumMap.getOrDefault(word, 0) + 1);
        }

        // !!!k-length pq for order, here the Comparator we must keep in mind that we should keep the elems we want in the pq, and as a queue we only could remove the head, so we should keep the pq order is reversed with the final order we want.
        // for eg, input ["i", "love", "leetcode", "i", "love", "coding"] 2, we want ["i", "love"], so we should order them as "leetcode", "coding", "love","i" then when we poll we could discard the unwanted elem from the pq. then at last we reverse the ret list.
        // since we need descending num order and ascending alphabeta order, then we should make our pq order be ascending num and descending alphabeta
        PriorityQueue<String> pq = new PriorityQueue<>((firstW, secondW)->{
            // if freq is the same, descending order by alphabeta
            if(wordNumMap.get(firstW) == wordNumMap.get(secondW)){
                return secondW.compareTo(firstW);
            }

            // by default, ascending order by num
            return wordNumMap.get(firstW) - wordNumMap.get(secondW);
        });

        // build pq
        for(String word: wordNumMap.keySet()){
            pq.offer(word);
            if(pq.size() > k){
                pq.poll();
            }
        }

        // build ret
        while(!pq.isEmpty()){
            ret.add(pq.poll());
        }

        Collections.reverse(ret);

        return ret;
    }
}
