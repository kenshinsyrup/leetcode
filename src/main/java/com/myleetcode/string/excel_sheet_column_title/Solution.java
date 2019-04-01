package com.myleetcode.string.excel_sheet_column_title;

class Solution {
    public String convertToTitle(int n) {
        return convertToTitleByMath(n);
    }

    // TC: O(N)
    // SC: O(1)
    // intuition: math problem, 1-26 is A-Z, and number greater than 26 could mod 26 to get its corresponding letter.
    //https://leetcode.com/problems/excel-sheet-column-title/discuss/51401/My-easy-to-understand-JAVA-solution
    private String convertToTitleByMath(int n){
        if(n <= 0){
            return "";
        }

        String ret = "";
        while(n > 0){
            String head = Character.toString((char)((n - 1) % 26 + 'A'));
            ret = head + ret;
            n = (n - 1) / 26;
        }

        return ret;
    }
}
