package com.myleetcode.backtracking.next_closest_time;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public String nextClosestTime(String time) {
        return nextClosestTimeByBacktracking(time);
    }

    // intuition:
    // backtracking to try all possible time with given four nums(int String time),
    // how could we choose the closest one? we only get the valid time, then we compare them as minutes
    class Result{
        List<Integer> numList;
        int dist;
    }
    // TC: O(4^4)
    // SC: O(4)
    private String nextClosestTimeByBacktracking(String time){
        if(time == null || time.length() < 5){
            return "";
        }

        int hourFirst = Integer.parseInt(time.substring(0, 1));
        int hourSecond = Integer.parseInt(time.substring(1, 2));
        int minFirst = Integer.parseInt(time.substring(3, 4));
        int minSecond = Integer.parseInt(time.substring(4, 5));

        // !!! check if we have 4 equal num in time
        if(hourFirst == hourSecond && hourSecond == minFirst && minFirst == minSecond){
            // !!! this problem requires we return the original time when all num in time are equal, not next closest time
            // minSecond++;
            // return "" + hourFirst + hourSecond + ":" + minFirst + minSecond;
            return time;
        }

        // time to minutes based number
        int timeNum = (hourFirst * 10 + hourSecond) * 60 + minFirst * 10 + minSecond;

        // List for trying
        List<Integer> numList = new ArrayList<>();
        numList.add(hourFirst);
        numList.add(hourSecond);
        numList.add(minFirst);
        numList.add(minSecond);

        // ret for store intermediate result
        Result ret = new Result();

        // try
        backtracking(numList, timeNum, new ArrayList<>(), ret);

        // build final string
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < ret.numList.size(); i++){
            if(i == 2){
                sb.append(":");
            }
            sb.append(ret.numList.get(i));
        }

        return sb.toString();
    }

    private void backtracking(List<Integer> numList, int timeNum, List<Integer> snippet, Result ret){
        if(snippet.size() == 4){
            if(!isValid(snippet)){
                return;
            }

            int curTimeNum = (snippet.get(0) * 10 + snippet.get(1)) * 60 + snippet.get(2) * 10 + snippet.get(3);
            if(curTimeNum == timeNum){
                return;
            }

            int dist = Integer.MAX_VALUE;
            if(curTimeNum < timeNum){
                dist = 24 * 60 - (timeNum - curTimeNum);
            }else{
                dist = curTimeNum - timeNum;
            }

            if(ret.numList == null || ret.dist > dist){
                ret.numList = new ArrayList(snippet);
                ret.dist = dist;
            }

            return;
        }

        for(int i = 0; i < numList.size(); i++){
            snippet.add(numList.get(i));

            backtracking(numList, timeNum, snippet, ret);

            snippet.remove(snippet.size() - 1);

        }
    }

    private boolean isValid(List<Integer> numList){
        int hourFirst = numList.get(0);
        int hourSecond = numList.get(1);
        int minFirst = numList.get(2);
        int minSecond = numList.get(3);

        // min 00 : 59
        if(minFirst > 5){
            return false;
        }
        // hour 00 : 23
        if(hourFirst > 2){
            return false;
        }
        if(hourFirst == 2 && hourSecond > 3){
            return false;
        }

        return true;

    }
}
