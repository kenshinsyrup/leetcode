package com.myleetcode.binary_search.online_election;

import java.util.HashMap;
import java.util.Map;

public class TopVotedCandidate {

    /*
    The use of Map to record leader and votes at time.
    https://leetcode.com/problems/online-election/discuss/173382/C++JavaPython-Binary-Search-in-Times/187874
    */

    int[] times;
    Map<Integer, Integer> timeLeaderMap;
    Map<Integer, Integer> personVoteMap;

    public TopVotedCandidate(int[] persons, int[] times) {
        this.times = times;
        this.timeLeaderMap = new HashMap<>();
        this.personVoteMap = new HashMap<>();

        int len = persons.length;
        int leader = -1;
        for (int i = 0; i < len; i++) {
            int person = persons[i];
            int time = this.times[i];

            // Votes of person.
            this.personVoteMap.put(person, this.personVoteMap.getOrDefault(person, 0) + 1);

            // Leader at this time.
            // This is wrong, we may have many person, not just two candidates 0 and 1.
            // int person0Vote = this.personVoteMap.getOrDefault(0, 0);
            // int person1Vote = this.personVoteMap.getOrDefault(1, 0);
            // if(person0Vote > person1Vote){
            //     this.timeLeaderMap.put(time, 0);
            // }else if(person0Vote < person1Vote){
            //     this.timeLeaderMap.put(time, 1);
            // }else{
            //     this.timeLeaderMap.put(time, person);
            // }

            // Leader at this time.
            if (i == 0 || this.personVoteMap.get(person) >= this.personVoteMap.get(leader)) {
                leader = person;
            }
            this.timeLeaderMap.put(time, leader);
        }
    }

    public int q(int t) {
        int timeIdx = firstLessOrEqualByBS(this.times, t);
        if (timeIdx == -1) {
            return -1;
        }

        return this.timeLeaderMap.get(times[timeIdx]);
    }

    private int firstLessOrEqualByBS(int[] times, int target) {
        int left = 0;
        int right = times.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (times[mid] == target) {
                return mid;
            } else if (times[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left - 1;
    }
}

/**
 * Your TopVotedCandidate object will be instantiated and called as such:
 * TopVotedCandidate obj = new TopVotedCandidate(persons, times);
 * int param_1 = obj.q(t);
 */
