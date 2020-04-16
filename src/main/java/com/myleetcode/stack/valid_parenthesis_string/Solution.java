package com.myleetcode.stack.valid_parenthesis_string;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    public boolean checkValidString(String s) {
        // return checkValidStringByBacktracking(s);
        return checkValidByStack(s);
    }

    /*
    Most important part is to solve cases like ")(".
    */

    /*
    Two Stacks
    https://leetcode.com/problems/valid-parenthesis-string/discuss/107572/Java-using-2-stacks.-O(n)-space-and-time-complexity.

    TC: O(N)
    SC: O(N)
    */
    private boolean checkValidByStack(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }

        int len = str.length();
        Deque<Integer> charIdxStack = new ArrayDeque<>();
        Deque<Integer> starIdxStack = new ArrayDeque<>();
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);

            if (ch == ')') {
                if (charIdxStack.isEmpty() && starIdxStack.isEmpty()) {
                    return false;
                }

                if (!charIdxStack.isEmpty()) {
                    charIdxStack.pop();
                } else {
                    starIdxStack.pop();
                }
            } else if (ch == '(') {
                charIdxStack.push(i);
            } else {
                starIdxStack.push(i);
            }
        }

        if (charIdxStack.size() > starIdxStack.size()) {
            return false;
        }

        while (!charIdxStack.isEmpty() && !starIdxStack.isEmpty()) {
            if (charIdxStack.pop() > starIdxStack.pop()) {
                return false;
            }
        }

        return true;
    }

    /*
    Backtracking, enumerate all possible cases when meet star.

    https://leetcode.com/problems/valid-parenthesis-string/discuss/107566/Java-12-lines-solution-backtracking

    TC: O(2^N)
    SC: O(N)
    */
    private boolean checkValidStringByBacktracking(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }

        return backtracking(str, 0, 0);
    }

    private boolean backtracking(String str, int preVal, int curIdx) {
        int len = str.length();
        if (curIdx >= len) {
            return preVal == 0;
        }

        // !!! Most important part to solve case like )(". Whenever we find preVal is already smaller than 0, means a ) has no been matched which means wrong.
        if (preVal < 0) {
            return false;
        }

        char curCh = str.charAt(curIdx);
        if (curCh == '(') {
            if (backtracking(str, preVal + 1, curIdx + 1)) {
                return true;
            }
        } else if (curCh == ')') {
            if (backtracking(str, preVal - 1, curIdx + 1)) {
                return true;
            }
        } else {
            if (backtracking(str, preVal, curIdx + 1) || backtracking(str, preVal + 1, curIdx + 1) || backtracking(str, preVal - 1, curIdx + 1)) {
                return true;
            }
        }

        return false;

    }
}
