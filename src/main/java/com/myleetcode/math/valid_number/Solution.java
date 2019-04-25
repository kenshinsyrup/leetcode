package com.myleetcode.math.valid_number;

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
        boolean numberBeforeE = false;
        boolean numberAfterE  = false;
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            if ('0' <= cur && cur <= '9') {
                if (!eSeen) numberBeforeE = true;
                if (eSeen)  numberAfterE  = true;
            } else if (cur == '-' || cur == '+') {
                if (i != 0 && s.charAt(i - 1) != 'e') return false;
            } else if (cur == '.') {
                if (eSeen || dotSeen) return false;
                dotSeen = true;
            } else if (cur == 'e' ) {
                if (eSeen) return false;
                eSeen = true;
            } else { // invalid chars
                return false;
            }
        }
        return eSeen ? (numberBeforeE && numberAfterE) : numberBeforeE;
    }
}
