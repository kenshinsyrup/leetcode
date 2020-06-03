package com.myleetcode.backtracking.the_k_th_lexicographical_string_of_all_happy_strings_of_length_n;

public class Solution {
    public String getHappyString(int n, int k) {
        return getHappyStringByBacktracking(n, k);
    }

    private String getHappyStringByBacktracking(int n, int k) {
        if (n <= 0 || k <= 0) {
            return "";
        }

        Arg arg = new Arg(k);
        StringBuilder sb = new StringBuilder();
        backtracking(n, sb, arg);
        return arg.res;
    }

    private void backtracking(int n, StringBuilder sb, Arg arg) {
        if (sb.length() == n) {
            arg.k--;
            if (arg.k == 0) {
                arg.res = sb.toString();
            }
            return;
        }
        if (sb.length() > n) {
            return;
        }

        for (int i = 0; i < 3; i++) {
            char ch = (char) ('a' + i);
            if (sb.length() != 0 && sb.charAt(sb.length() - 1) == ch) {
                continue;
            }

            sb.append(ch);
            backtracking(n, sb, arg);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    class Arg {
        int k;
        String res;

        public Arg(int k) {
            this.k = k;
            this.res = "";
        }
    }

}
