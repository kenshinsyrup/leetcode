package com.myleetcode.priority_queue.smallest_subsequence_of_distinct_characters;

import java.util.*;

public class Solution {
    public String smallestSubsequence(String text) {
        // return removeDuplicateLettersByStack(text);
        return removeDuplicateLettersByPQ(text);
    }

    /*
    PriorityQueue
    https://leetcode.com/problems/remove-duplicate-letters/discuss/76766/Easy-to-understand-iterative-Java-solution
    https://leetcode.com/problems/remove-duplicate-letters/discuss/76766/Easy-to-understand-iterative-Java-solution/150076

The basic idea is to find out the smallest result letter by letter (one letter at a time). Here is the thinking process for input "cbacdcbc":

find out the last appeared position for each letter;
c - 7
b - 6
a - 2
d - 4

find out the smallest index from the map in step 1 (a - 2);

the first letter in the final result must be the smallest letter from index 0 to index 2;

repeat step 2 to 3 to find out remaining letters.

    TC: O(N*logN)
    SC: O(N)
    */
    private String removeDuplicateLettersByPQ(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        int len = str.length();

        // 1. Record char and its last present index.
        PriorityQueue<Integer> idxPQ = new PriorityQueue<>();
        Map<Character, Integer> charLastIdxMap = new HashMap<>();
        for (int i = len - 1; i >= 0; i--) {
            char ch = str.charAt(i);

            if (!charLastIdxMap.containsKey(ch)) {
                charLastIdxMap.put(ch, i);
                idxPQ.offer(i);
            }
        }

        // 2. Choose.
        StringBuilder sb = new StringBuilder();
        // Left bound of search range for min char.
        int startIdx = 0;
        while (!idxPQ.isEmpty()) {
            // This gives us the right bound of search range for min char.
            int topIdx = idxPQ.peek(); // !!! Not poll.

            char minCh = 'z' + 1;
            // Search the min char in this range.
            for (int i = startIdx; i <= topIdx; i++) {
                char curCh = str.charAt(i);
                if (minCh > curCh && charLastIdxMap.containsKey(curCh)) {
                    minCh = curCh;
                    startIdx = i + 1;
                }
            }

            // If we get a minCh, remove its idx from pq, remove it from map and append to sb. Remove from map to avoid duplicate letters.
            if (minCh != 'z' + 1) {
                // Remove used element.
                int minChIdx = charLastIdxMap.get(minCh);
                idxPQ.remove(minChIdx);
                charLastIdxMap.remove(minCh);

                // Build sb.
                sb.append(minCh);
            }
        }

        return sb.toString();

    }

    /*
    Stack
    https://leetcode.com/problems/remove-duplicate-letters/discuss/76762/Java-O(n)-solution-using-stack-with-detail-explanation

    Most important part is to get the thought.

    TC: O(26*N) => O(N)
    SC: O(N)
    */
    private String removeDuplicateLettersByStack(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }

        int len = str.length();
        Map<Character, Integer> charNumMap = new HashMap<>();

        // 1. Coutn each char number.
        for (int i = 0; i < len; i++) {
            char curCh = str.charAt(i);

            charNumMap.put(curCh, charNumMap.getOrDefault(curCh, 0) + 1);
        }

        // 2. Process.
        Deque<Character> charStack = new ArrayDeque<>();
        Map<Character, Boolean> charVisitMap = new HashMap<>();
        for (int i = 0; i < len; i++) {
            char curCh = str.charAt(i);

            // Reduce the total number of the char.
            charNumMap.put(curCh, charNumMap.get(curCh) - 1);

            // Have processed this char.
            if (charVisitMap.containsKey(curCh) && charVisitMap.get(curCh)) {
                continue;
            }

            // If stack is not empty, if current char is smaller than the char at stack top, and if the char at stack top is not exhausted, ie the char at stack top still exist after current char. This means we could reach a lexicographical smaller substring
            while (!charStack.isEmpty() && curCh < charStack.peek() && charNumMap.get(charStack.peek()) > 0) {
                char topCh = charStack.pop(); // Pop the top char out since we could get a lexicographical smaller substring with this char at other behind position.

                charVisitMap.put(topCh, false); // Mark the popped char as not visited.
            }

            charStack.push(curCh);
            charVisitMap.put(curCh, true);
        }

        // 3. Result.
        StringBuilder sb = new StringBuilder();
        while (!charStack.isEmpty()) {
            sb.append(charStack.pop());
        }

        return sb.reverse().toString();
    }
}
