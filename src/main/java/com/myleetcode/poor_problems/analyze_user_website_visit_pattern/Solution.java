package com.myleetcode.poor_problems.analyze_user_website_visit_pattern;

import java.util.*;

class Solution {
    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        return mostVisitedPatternByBruteForce(username, timestamp, website);
    }

    /*
    Poor question, poor description.

    Brute Force:
    https://leetcode.com/problems/analyze-user-website-visit-pattern/discuss/355626/Java-Solution-with-2-HashMap

    TC: O(N^3)
    SC: O(N)
    */
    private List<String> mostVisitedPatternByBruteForce(String[] username, int[] timestamp, String[] website) {
        List<String> ret = new ArrayList<>();

        // Special case.
        if (username == null || username.length == 0 || timestamp == null || timestamp.length == 0 || website == null || website.length == 0) {
            return ret;
        }
        if (!(username.length == timestamp.length && timestamp.length == website.length)) {
            return ret;
        }

        int len = username.length;

        // List infoList, sorted by timestamp ascending order.
        List<Info> infoList = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            Info info = new Info(website[i], timestamp[i], username[i]);

            infoList.add(info);
        }
        Collections.sort(infoList, new Comparator<Info>() {
            public int compare(Info i1, Info i2) {
                return i1.visitTime - i2.visitTime;
            }
        });

        // Map userWebsitesmap, stores the list of websites a user has visited, user may visit the same website several times, keep record all of them in order.
        Map<String, List<String>> userWebsiteMap = new HashMap<>();
        for (Info info : infoList) {
            List<String> websiteList = userWebsiteMap.getOrDefault(info.userName, new ArrayList<>());

            websiteList.add(info.websiteName);

            userWebsiteMap.put(info.userName, websiteList);
        }

        // Kepp track of the max visited website number.
        int maxCount = 0;
        // Keep track of the max visited website with min lexicographical value.
        String minSequence = "";

        // Map sequenceCountMap.
        Map<String, Integer> sequenceCountMap = new HashMap<>();
        for (String userName : userWebsiteMap.keySet()) {
            // Get all 3-sequence of a user.
            List<String> visitedWebsiteList = userWebsiteMap.get(userName);
            if (visitedWebsiteList.size() < 3) {
                continue;
            }
            Set<String> sequenceSet = buildSequence(visitedWebsiteList);

            // Get the maxCount and minSequence.
            for (String sequence : sequenceSet) {
                sequenceCountMap.put(sequence, sequenceCountMap.getOrDefault(sequence, 0) + 1);

                int currentCount = sequenceCountMap.get(sequence);
                if (maxCount < currentCount) {
                    maxCount = currentCount;
                    minSequence = sequence;
                }

                if (maxCount == currentCount) {
                    if (minSequence.compareTo(sequence) > 0) {
                        minSequence = sequence;
                    }
                }
            }
        }

        // Normalize the minSequence to answer.
        for (String str : minSequence.split(",")) {
            ret.add(str);
        }
        return ret;

    }

    // Every 3 adjacent visited websites is a 3-sequence and we don't need duplicates.
    private Set<String> buildSequence(List<String> visitedWebsiteList) {
        int len = visitedWebsiteList.size();
        Set<String> sequenceSet = new HashSet<>();
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                for (int k = j + 1; k < len; k++) {
                    sequenceSet.add(visitedWebsiteList.get(i) + "," + visitedWebsiteList.get(j) + "," + visitedWebsiteList.get(k));
                }
            }
        }

        return sequenceSet;
    }

    class Info {
        String websiteName;
        int visitTime;
        String userName;

        Info(String websiteName, int visitTime, String userName) {
            this.websiteName = websiteName;
            this.visitTime = visitTime;
            this.userName = userName;
        }
    }
}
