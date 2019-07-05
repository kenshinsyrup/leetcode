package com.myleetcode.bucket_sort.top_k_frequent_elements;

import java.util.*;

class Solution {
    public List<Integer> topKFrequent(int[] nums, int k) {
        // return topKFrequentByPQ(nums, k);
        return topKFrequentByBuckeySort(nums, k);
    }

    // TC: O(N)
    // SC: O(N)
    // and, there's a more efficient solution called Bucket Sort, which is perfect for this type problem: top K frequency but no order requirement about this top K elems.
    /*
    It is intuitive to map a value to its frequency. Then our problem becomes 'to sort map entries by their values'.
Since frequency is within the range [1, n] for n is the number of elements, we could apply the idea of Bucket Sort:

we divide frequencies into n + 1 buckets, in this way, the list in buckets[i] contains elements with the same frequency i
then we go through the buckets from tail to head until we collect k elements.
    */
    // https://leetcode.com/problems/top-k-frequent-elements/discuss/179084/Bucket-Sort-Java-with-Explanation
    private List<Integer> topKFrequentByBuckeySort(int[] nums, int k){
        List<Integer> ret = new ArrayList<>();

        if(nums == null || nums.length == 0 || k < 0){
            return ret;
        }

        // TC: O(N)
        Map<Integer, Integer> valNumMap = new HashMap<>();
        for(int num: nums){
            valNumMap.put(num, valNumMap.getOrDefault(num, 0) + 1);
        }

        // here we use Buckey Sort
        // put vals to its coresponding freq bucket, because we have nums.length nums, so we may be have num.length+1 freqs that is freq 0, freq 1...freq n where n is nums.length. so we have nums.length + 1 buckets.
        // for every bucket, its indx in freqBuckets is the freq of the values that it stores.
        // TC: O(N)
        List<Integer>[] freqBuckets = new List[nums.length + 1];
        for(int val: valNumMap.keySet()){
            int freq = valNumMap.get(val);
            if(freqBuckets[freq] == null){
                freqBuckets[freq] = new ArrayList<>();
            }

            freqBuckets[freq].add(val);
        }

        // TC: O(N)
        // then, we output the required buckets to ret
        for(int i = freqBuckets.length - 1; i >= 0; i--){
            // only put exact k elems to ret.
            if(ret.size() < k && freqBuckets[i] != null){
                int bucketSize = freqBuckets[i].size();

                if(ret.size() + bucketSize > k){ // if add all elems in current bucket to ret is larger than k, we only need some of them
                    int idx = 0;
                    for(int j = ret.size(); j < k; j++){
                        ret.add(freqBuckets[i].get(idx));
                        idx++;
                    }
                }else{ // otherwise, add all elems in current bucket to ret
                    ret.addAll(freqBuckets[i]);
                }
            }
        }

        return ret;
    }


    // intuition: problem requirements say TC needs better than O(N*log(N)) so could not sort array directly.
    // first we could traverse the array and store the value-number to a map, because we should keep our TC better than O(N*logN) so could not use TreeMap directly
    // so we could use a PriorityQueue, and only store the first k-frequent vals in the queue, this could give us O(N*logK) TC which is better than O(N*logN), this is tricky...
    private List<Integer> topKFrequentByPQ(int[] nums, int k){
        List<Integer> ret = new ArrayList<>();

        if(nums == null || nums.length == 0 || k < 0){
            return ret;
        }

        Map<Integer, Integer> valNumMap = new HashMap<>();
        for(int num: nums){
            valNumMap.put(num, valNumMap.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>((valA, valB)->{
            return valNumMap.get(valA) - valNumMap.get(valB);
        });
        for(int val: valNumMap.keySet()){
            pq.offer(val);
            // only keep k elem in pq to make the TC is O(N*logK)
            if(pq.size() > k){
                pq.poll();
            }
        }

        while(!pq.isEmpty()){
            ret.add(pq.poll());
        }

        return ret;

    }
}
