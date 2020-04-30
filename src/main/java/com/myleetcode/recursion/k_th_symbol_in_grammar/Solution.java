package com.myleetcode.recursion.k_th_symbol_in_grammar;

public class Solution {
    public int kthGrammar(int N, int K) {
        return kthGrammarByRecursion(N, K);
    }

    /*
    Recursion.
    https://leetcode.com/problems/k-th-symbol-in-grammar/discuss/265892/Runtime-0ms-Java-solution-with-explanation
    */
    private int kthGrammarByRecursion(int N, int K) {
        if (N < 1 || K < 1) {
            return -1;
        }

        // Make K 0 based.
        K -= 1;
        return recurse(N, K);
    }

    /*
    For num in Kth index in row N, it comes from num's transformation at K/2 in row N-1. Since a num in row N-1 will become a sequence 01 or 10 in row N, we know if K is even, it will be the second digit and if K is odd then it's the first digit in the sequence.
    */
    private int recurse(int N, int K) {
        // Base case, if row N is 1, only has one digit 0.
        if (N == 1) {
            return 0;
        }

        // We could use the recursion get the corresponding second digit easily.
        int secondDigit = recurse(N - 1, K / 2);

        // Based on the K is even or odd, we return the second digit or first digit.
        if (K % 2 == 0) {
            return secondDigit;
        }
        return secondDigit == 1 ? 0 : 1;
    }
}
