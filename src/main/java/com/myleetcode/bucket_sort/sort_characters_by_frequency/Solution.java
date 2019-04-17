package com.myleetcode.bucket_sort.sort_characters_by_frequency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public String frequencySort(String s) {
        return frequencySortByBucketSort(s);
    }

    // TC: O(N)
    // SC: O(N)
    // intuition: this is a Bucket Sort problem because we only care about the order between "bucket" instead of all elems.
    private String frequencySortByBucketSort(String s){
        if(s == null || s.length() <= 1){
            return s;
        }

        // map to count freqs
        Map<Character, Integer> charNumMap = new HashMap<>();
        for(char strChar: s.toCharArray()){
            charNumMap.put(strChar, charNumMap.getOrDefault(strChar, 0) + 1);
        }

        // freqBuckets for freqs
        List<Character>[] freqBuckets = new List[s.length() + 1];
        for(char strChar: charNumMap.keySet()){
            int freq = charNumMap.get(strChar);
            if(freqBuckets[freq] == null){
                freqBuckets[freq] = new ArrayList<>();
            }

            freqBuckets[freq].add(strChar);
        }

        // output, though has many nested for, but at most cost O(N) time
        StringBuilder sb = new StringBuilder();
        for(int i = freqBuckets.length - 1; i >= 0; i--){
            if(freqBuckets[i] != null){
                for(char strChar: freqBuckets[i]){
                    for(int j = 0; j < charNumMap.get(strChar); j++){
                        sb.append(strChar);
                    }
                }
            }
        }

        return sb.toString();
    }
}
