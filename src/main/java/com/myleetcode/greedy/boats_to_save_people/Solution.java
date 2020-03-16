package com.myleetcode.greedy.boats_to_save_people;

import java.util.Arrays;

class Solution {
    public int numRescueBoats(int[] people, int limit) {
        return numRescueBoatsByGreedy(people, limit);
    }

    /*
    Greedy
    https://leetcode.com/problems/boats-to-save-people/discuss/156855/6-lines-Java-O(nlogn)-code-sorting-%2B-greedy-with-greedy-algorithm-proof.

    TC: O(NlogN)
    SC: O(1)
    */
    private int numRescueBoatsByGreedy(int[] people, int limit) {
        if (people == null || people.length == 0 || limit <= 0) {
            return 0;
        }

        int len = people.length;

        Arrays.sort(people);

        int count = 0;
        int left = 0;
        int right = len - 1;
        while (left <= right) {
            if (people[right] + people[left] <= limit) {
                left++;
            }

            right--;
            count++;
        }

        return count;
    }
}
