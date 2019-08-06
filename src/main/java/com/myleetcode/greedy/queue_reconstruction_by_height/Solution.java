package com.myleetcode.greedy.queue_reconstruction_by_height;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class Solution {
    public int[][] reconstructQueue(int[][] people) {
        return reconstructQueueBySort(people);
    }

    // intuition: sort by height descending first; then from left to right, add to correct pos. This is a greedy sol, check solution section
    // https://leetcode.com/problems/queue-reconstruction-by-height/discuss/89345/Easy-concept-with-PythonC++Java-Solution/93976
    // TC: O(N^2), loop cost O(N), add cost O(N)
    // SC: O(N)
    private int[][] reconstructQueueBySort(int[][] people) {
        if(people == null || people.length == 0 || people[0] == null || people[0].length == 0){
            return people;
        }

        Arrays.sort(people, new Comparator<int[]>(){
            public int compare(int[] p1, int[] p2){
                if(p1[0] != p2[0]){
                    return p2[0] - p1[0];
                }
                return p1[1] - p2[1];
            }
        });

        List<int[]> ret = new ArrayList<>();
        // add to pos
        for(int[] cur: people){
            ret.add(cur[1], cur);
        }

        return ret.toArray(new int[][]{});
    }
}
