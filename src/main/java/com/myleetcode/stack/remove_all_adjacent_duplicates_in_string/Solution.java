package com.myleetcode.stack.remove_all_adjacent_duplicates_in_string;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution {
    public String removeDuplicates(String S) {
        return removeDuplicatesByStack(S);
    }

    private String removeDuplicatesByStack(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        if (str.length() == 1) {
            return str;
        }

        int len = str.length();
        Deque<Character> chStack = new ArrayDeque<>();
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);

            if (!chStack.isEmpty() && chStack.peek().equals(ch)) {
                chStack.pop();
            } else {
                chStack.push(ch);
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!chStack.isEmpty()) {
            sb.append(chStack.pop());
        }


        return sb.reverse().toString();
    }
}
