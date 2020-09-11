package com.myleetcode.string.reorder_data_in_log_files;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Solution {
    public String[] reorderLogFiles(String[] logs) {
        return recorderLogFiles(logs);
    }

    /*
    Intuition:
    We could use one MinHeap for letter-log and one List for digit-log

    TC: O(N * M*logN * M), N is logs length, logN is cost of MinHeap, M is longest log length
    SC: O(N)
    */
    private String[] recorderLogFiles(String[] logs) {
        if (logs == null || logs.length <= 1) {
            return logs;
        }

        // PQ for letter logs.
        PriorityQueue<String> letterLogPQ = new PriorityQueue<>((a, b) -> {
            if (subLog(a).compareTo(subLog(b)) != 0) {
                return subLog(a).compareTo(subLog(b));
            }
            return a.compareTo(b);
        });

        // List for digit logs.
        List<String> digitLogList = new ArrayList<>();

        // Traverse logs, put each log into correct container.
        for (String log : logs) {
            if (isDigitLog(log)) {
                digitLogList.add(log);
            } else {
                letterLogPQ.offer(log);
            }
        }

        String[] ret = new String[logs.length];
        int i = 0;
        while (!letterLogPQ.isEmpty()) {
            ret[i] = letterLogPQ.poll();
            i++;
        }
        for (int j = 0; j < digitLogList.size(); j++) {
            ret[i] = digitLogList.get(j);
            i++;
        }

        return ret;
    }

    private String subLog(String log) {
        int i = 0;
        while (i < log.length() && log.charAt(i) != ' ') {
            i++;
        }
        return log.substring(i, log.length());
    }

    /*
    TC: O(M), M is log length
    */
    private boolean isDigitLog(String log) {
        int i = 0;
        while (i < log.length() && log.charAt(i) != ' ') {
            i++;
        }
        while (i < log.length() && log.charAt(i) == ' ') {
            i++;
        }

        if (i < log.length() && log.charAt(i) >= '0' && log.charAt(i) <= '9') {
            return true;
        }

        return false;
    }
}
