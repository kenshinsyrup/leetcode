package com.myleetcode.string.decoded_string_at_index;

public class Solution {
    public String decodeAtIndex(String S, int K) {
        return decodeAtIndexByLogic(S, K);
    }

    /*
    Based on Solution part and below solution.

    https://leetcode.com/problems/decoded-string-at-index/discuss/157390/Logical-Thinking-with-Clear-Code

    If we have a decoded string like appleappleappleappleappleapple and an index like K = 24, the answer is the same if K = 4.

    In general, when a decoded string is equal to some word with size length repeated some number of times (such as apple with size = 5 repeated 6 times), the answer is the same for the index K as it is for the index K % size.

    We can use this insight by working backwards, keeping track of the size of the decoded string. Whenever the decoded string would equal some word repeated d times, we can reduce K to K % (word.length).

    Only the character in the Kth position matters rather than the whole string decoded, so we only keep track of curLength (long data type to avoid integer overflow).

    Let's see the code commented, first a1, a2, then b1, b2. They are matching except that we manage K in a2, b2.
Since the result character can only be a letter rather than digit, in b2 we ought to check K. Whenever K % curLength == 0, we figure out the result.

    */
    private String decodeAtIndexByLogic(String str, int K) {
        if (str == null || str.length() == 0) {
            return "";
        }
        if (K < 0) {
            return "";
        }

        // Decode to get every repeated string length.
        int len = str.length();
        long curLen = 0;
        for (int i = 0; i < len; i++) {
            char curCh = str.charAt(i);

            if (Character.isDigit(curCh)) { // a1
                curLen *= curCh - '0';
            } else { // b1
                curLen++;
            }
        }

        // Find result.
        for (int i = len - 1; i >= 0; i--) {
            char curCh = str.charAt(i);

            if (Character.isDigit(curCh)) { // a2
                curLen /= curCh - '0';
                K %= curLen;
            } else { // b2
                if (K == 0 || K == curLen) {
                    return "" + curCh;
                }

                curLen--;
            }
        }

        return "";

    }

}
