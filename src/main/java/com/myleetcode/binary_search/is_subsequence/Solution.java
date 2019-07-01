package com.myleetcode.binary_search.is_subsequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public boolean isSubsequence(String s, String t) {
        // return isSubsequenceByMapAndBS(s, t);
        return isSubsequenceBySubstring(s, t);
    }

    // TC: O(S + T), worst case of substring is T, but not for every ch in s, everytime we process a ch in s, we cut the t off some length, so O(S + T) is good
    // SC: O(1)
    public boolean isSubsequenceBySubstring(String s, String t) {
        if(s == null || s.length() == 0){
            return true;
        }
        if(t == null || t.length() == 0){
            return false;
        }

        int startIdx = 0;
        for (char c : s.toCharArray()) {
            int newIndex = t.substring(startIdx).indexOf(c); // worst case O(T)
            if (newIndex == -1) {
                return false;
            }

            startIdx += newIndex + 1; // !!! newIdx is the idx of c at substring of t, so the newIdx+1 is the start idx of next c at substring of t, add it to the startIdx so it's the idx at the whole t
        }
        return true;
    }

    /*
    https://leetcode.com/problems/is-subsequence/discuss/87302/Binary-search-solution-for-follow-up-with-detailed-comments/92266

Binary search:
record the indexes for each character in t, if s[i] matches t[j], then s[i+1] should match a character in t with index bigger than j. This can be reduced to find the first element larger than a value in an sorted array (find upper bound), which can be achieved using binary search.
Trie:

For example, if s1 has been matched, s1[last char] matches t[j]. Now, s2 comes, if s1 is a prefix of s2, i.e., s1 == s2.substr[0, i-1], we can start match s2 from s2[i], right?
So, the idea is to create a Trie for all string that have been matched so far. At a node, we record the position in t which matches this char represented by the node. Now, for an incoming string s, we first search the longest prefix in the Trie, find the matching position of the last prefix-char in t, say j. Then, we can start matching the first non-prefix-char of s from j+1.
Now, if we have done the preprocessing as stated in the binary search approach, we can be even faster.
    */

    // for the follow up, if have a lot of s, means we need do a lot of gets, so we could use a map to process t only one time and do bs multiple times. if we have K s, so TC will be TC: O(min(K * S * logT + T)), here we could say K*S*logT larger than T, so TC is O(K*S*logT), normally T is larger than S, and when K is large, should be better than the above one with TC O(K*(S+T))
    /*
    出错点：
    1 智障: bs最后应该返回的是idx in T，也就是idxList.get(start); 但是错误的写成了start;
    */

    // intuition:
    // first change String T to Map<Character, List<Integer>> where key is the char in T, and List is the idxes of the char
    // then traverse S; use a variable to record where we current are at T, initiall the idx is -1; for every char sc, if Map contains this key, we need to find the first elem in List that is not smaller than idx+1, if we find, means this sc we find in T and the sequence is valid, update the idx to this value and next sc.
    // TC: O(min(S * logT + T)), S is the length of s, and T is the length of t, S*logT is the cost of search all chars in S in the Map; T is the build Map cost
    // SC: O(T)
    private boolean isSubsequenceByMapAndBS(String s, String t){
        if(s == null || s.length() == 0){
            return true;
        }
        if(t == null || t.length() == 0){
            return false;
        }

        // get map
        Map<Character, List<Integer>> charIdxMap = new HashMap<>();
        char[] tCharArr = t.toCharArray();
        for(int i = 0; i < tCharArr.length; i++){
            charIdxMap.putIfAbsent(tCharArr[i], new ArrayList<>());
            charIdxMap.get(tCharArr[i]).add(i);
        }

        // search
        int idx = -1;
        for(char sCh: s.toCharArray()){
            if(!charIdxMap.containsKey(sCh)){
                return false;
            }

            List<Integer> idxList = charIdxMap.get(sCh);
            idx = binarySearch(idxList, idx + 1);
            if(idx == -1){
                return false;
            }
        }

        return true;

    }

    private int binarySearch(List<Integer> idxList, int target){
        int start = 0;
        int end = idxList.size() - 1;

        // special, if idxList last idx is smaller than target, return -1
        if(idxList.get(end) < target){
            return -1;
        }

        // bs to find the first elem that not smaller than target
        while(start <= end){
            int mid = start + (end - start) / 2;

            int midIdx = idxList.get(mid);
            if(midIdx == target){ // if target exists, return it
                return idxList.get(mid);
            }if(midIdx < target){ // if target not exists, after while loop, start stores the first elem that is not samller than target
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }

        return idxList.get(start);
    }
}
