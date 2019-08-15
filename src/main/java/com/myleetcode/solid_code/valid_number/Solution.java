package com.myleetcode.solid_code.valid_number;

class Solution {
    public boolean isNumber(String s) {
        return isNumberByConditions(s);
    }

    private boolean isNumberByConditions(String s){
        // https://leetcode.com/problems/valid-number/discuss/23738/Clear-Java-solution-with-ifs/166852
        // https://leetcode.com/problems/valid-number/discuss/23738/Clear-Java-solution-with-ifs
        // 决定性有限自动机DFA https://leetcode.com/problems/valid-number/discuss/23728/A-simple-solution-in-Python-based-on-DFA
        s = s.toLowerCase().trim();

        boolean dotSeen = false;
        boolean eSeen   = false;
        boolean numberBeforeESeen = false;
        boolean numberAfterESeen  = false;
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            if ('0' <= cur && cur <= '9') {
                if (!eSeen) {
                    numberBeforeESeen = true;
                }

                if (eSeen) {
                    // 如果有了e但是前面没有数字是false
                    if(!numberBeforeESeen){
                        return false;
                    }

                    numberAfterESeen  = true;
                }
            } else if (cur == '-' || cur == '+') {
                // 如果-或者+不在idx 0，则只能在e之后第一. 否则为false
                if (i > 0 && s.charAt(i - 1) != 'e') {
                    return false;
                }
            } else if (cur == '.') {
                if (eSeen || dotSeen) {
                    return false;
                }

                dotSeen = true;
            } else if (cur == 'e' ) {
                if (eSeen) {
                    return false;
                }

                eSeen = true;
            } else { // invalid chars
                return false;
            }
        }

        // 如果有e，前面判断过来有e的情况下numberBeforeESeen为true，所以有e的话numberAfterESeen也为true那么有e的数字就没问题
        // 如果没有e，那么numberBeforeESeen为true则数字没问题
        return eSeen ? numberAfterESeen : numberBeforeESeen;
    }
}
