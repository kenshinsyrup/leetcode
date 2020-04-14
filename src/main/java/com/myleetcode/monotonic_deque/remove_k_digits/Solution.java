package com.myleetcode.monotonic_deque.remove_k_digits;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Solution {
    public String removeKdigits(String num, int k) {
        return removeKDigitsByStack(num, k);
    }

    /*
    Mono Stack
    */
    private String removeKDigitsByStack(String num, int K) {
        if (num == null || num.length() == 0 || K < 0) {
            return "0";
        }

        int len = num.length();
        if (len <= K) {
            return "0";
        }

        // 1. From largest digit in high position. For example "10200" and 1
        int idx = 0;
        Deque<Integer> idxStack = new ArrayDeque<>();
        List<Integer> removeList = new ArrayList<>();
        while (idx < len) {
            // Keep Mono increasing.
            char ch = num.charAt(idx);
            while (!idxStack.isEmpty() && ch < num.charAt(idxStack.peek())) {
                removeList.add(idxStack.pop());
            }

            idxStack.push(idx);

            idx++;
        }

        // 2. Complete, from largest digit in low position. For example, "112" and 1
        if (removeList.size() < K) {
            int remain = K - removeList.size();
            while (!idxStack.isEmpty() && remain > 0) {
                removeList.add(idxStack.pop());
                remain--;
            }
        }

        char[] chArr = num.toCharArray();
        for (int i = 0; i < removeList.size(); i++) {
            int removeIdx = removeList.get(i);
            chArr[removeIdx] = ' ';

            K--;
            if (K <= 0) {
                break;
            }
        }


        StringBuilder sb = new StringBuilder();
        // Remove leading 0 and append.
        for (char ch : chArr) {
            if (ch == '0' && sb.length() == 0) {
                continue;
            }
            if (ch == ' ') {
                continue;
            }

            sb.append(ch);
        }

        return sb.length() == 0 ? "0" : sb.toString();

    }
}
