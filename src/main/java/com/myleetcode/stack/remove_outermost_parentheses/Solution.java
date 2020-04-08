package com.myleetcode.stack.remove_outermost_parentheses;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    public String removeOuterParentheses(String S) {
        return removeOuterParenthesesByStack(S);
    }

    private String removeOuterParenthesesByStack(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        int len = str.length();
        char[] chars = str.toCharArray();
        Deque<Character> chStack = new ArrayDeque<>();
        int startIdx = 0;
        for (int i = 0; i < len; i++) {
            char ch = chars[i];
            if (ch == '(') {
                chStack.push(ch);
            }
            if (ch == ')') {
                chStack.pop();
            }

            // Now get a component.
            if (chStack.isEmpty()) {
                // If we could remove the outmost ( and ).
                if (startIdx + 1 < i) {
                    // Substring without the outmost ( and ).
                    sb.append(str.substring(startIdx + 1, i));
                }

                // Update startIdx.
                startIdx = i + 1;
            }
        }

        return sb.toString();
    }

}
