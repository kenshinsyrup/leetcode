package com.myleetcode.solid_code.zigzagconversion;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public String convert(String s, int numRows) {
        return convertByGroup(s, numRows);
    }

    /*
    intuition:
    one group of zigzag is two consecutive cols which first col is from 0th row and the second col is end at (numRows-1-1)th row.
    then, when we traverse the str, we first process the first col, then process the second col, then we start to traverse group.
    In implementation: we use a while loop to traverse the str, inner the loop, we use a for loop to process the first col that from 0th rwo to (numRows-1)th row and append them to a sb; then we use a for loop to process the second col that from (numRows-1-1)th row to 1th row and append them to a sb.

    TC: O(N)
    SC: O(N)
    */
    private String convertByGroup(String str, int numRows){
        if(str == null || str.length() == 0){
            return "";
        }

        // init the sb for every row
        StringBuilder[] rowSB = new StringBuilder[numRows];
        for(int idx = 0; idx < numRows; idx++){
            rowSB[idx] = new StringBuilder();
        }

        // process the str by group
        int len = str.length();
        int i = 0;
        while(i < len){
            // first col
            for(int j = 0; j < numRows && i < len; j++){
                rowSB[j].append(str.charAt(i));
                i++;
            }
            // second col
            for(int j = numRows - 1 - 1; j >= 1 && i < len; j--){
                rowSB[j].append(str.charAt(i));
                i++;
            }
        }

        // build ret
        StringBuilder retSB = new StringBuilder();
        for(int j = 0; j < numRows; j++){
            retSB.append(rowSB[j]);
        }

        return retSB.toString();

    }

    private String convertByTraverse(String str, int numRows){
        if(numRows == 1){
            return str;
        }

        List<StringBuilder> rows = new ArrayList<StringBuilder>();
        for(int i = 0; i<Math.min(numRows, str.length()); i++){
            rows.add(new StringBuilder());
        }

        int curRow = 0;
        boolean goingDown = false;

        for(char c: str.toCharArray()){
            rows.get(curRow).append(c);
            if(curRow == 0 || curRow == numRows-1){
                goingDown = !goingDown;
            }
            int shift = -1;
            if(goingDown){
                shift = 1;
            }
            curRow += shift;
        }

        StringBuilder ret = new StringBuilder();
        for(StringBuilder row: rows){
            ret.append(row);
        }
        return ret.toString();
    }
}