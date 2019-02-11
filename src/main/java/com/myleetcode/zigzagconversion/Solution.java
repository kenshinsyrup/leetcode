package com.myleetcode.zigzagconversion;

public class Solution {
    // 这个一个特别原始的想法，submit失败
    public String convert(String s, int numRows) {
        if (numRows == 0 || numRows == 1) {
            return s;
        }

        ArrayList<String> zigZagSubstrs = new ArrayList<String>();
        int counter = 0;//record number
        String startStr = new String();
        for (int i = 0; i < s.length(); i++) {
            counter++;
            startStr = startStr.concat(s.substring(i, i + 1));
            if (counter % numRows == 0) {
                zigZagSubstrs.add(startStr);
                if (numRows - 2 > 0) {
                    for (int j = 0; j < numRows - 2; j++) {
                        String midStr = new String("");
                        for (int m = 0; m < numRows; m++) {
                            if (j + 1 == m) {
                                midStr = midStr.concat(s.substring(i + j + 1, i + j + 1 + 1));
                            } else {
                                midStr = midStr.concat("-");
                            }
                        }

                        zigZagSubstrs.add(midStr);
                    }
                }
                startStr = "";
                counter = 0;
                i += numRows - 2;
            }
            if (i == s.length() - 1 && counter % numRows != 0) {
                for (int j = 0; j < numRows - startStr.length(); j++) {
                    startStr = startStr.concat("-");
                }
                zigZagSubstrs.add(startStr);
            }
        }

        //output
        String[] strs = zigZagSubstrs.toArray(new String[zigZagSubstrs.size()]);
        String resultStr = new String();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < strs.length; j++) {
                String str = strs[j];
                if (!str.substring(i, i + 1).equals("-")) {
                    resultStr = resultStr.concat(str.substring(i, i + 1));
                }
            }
        }
        return resultStr;
    }

    // 以下来自leetcode：https://leetcode.com/problems/zigzag-conversion/
    public String convertSoryByRow(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }

        //建立一个数组，每一行就是一个stringbuilder，这个stringbuilder就是用来保存最终的zigzag字符串的每行
        List<StringBuilder> rows = new ArrayList<>();
        int realNumRows = Math.min(numRows, s.length());
        for (int i = 0; i < realNumRows; i++) {
            rows.add(new StringBuilder());
        }

        //逐个取s中的字符，放入应该在的位置
        int curRow = 0;
        boolean goingDown = false;
        for (int i = 0; i < s.length(); i++) {
            StringBuilder row = rows.get(curRow);
            row.append(s.charAt(i));

            //移动curRow并确定方向
            if (curRow == 0 || curRow == numRows - 1) {
                goingDown = !goingDown;
            }
            int increasement = 1;
            if (!goingDown) {
                increasement = -1;
            }
            curRow += increasement;
        }

        // 我们得到了zigzag的每行组成的list，输出。
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < rows.size(); i++) {
            ret.append(rows.get(i));
        }
        return ret.toString();

    }
}