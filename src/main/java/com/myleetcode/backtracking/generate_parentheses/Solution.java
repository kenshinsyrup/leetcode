package com.myleetcode.backtracking.generate_parentheses;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public List<String> generateParenthesis(int n) {
//         https://leetcode.com/problems/generate-parentheses/discuss/10100/Easy-to-understand-Java-backtracking-solution
        // https://stackoverflow.com/questions/23413881/understanding-function-to-generate-parentheses/23414519

        List<String> ans = new ArrayList<String>();

        // special case
        if(n <= 0){
            return ans;
        }

        generateParenthesisByBacktracking(ans, "", n, 0, 0);

        return ans;

    }

    private void generateParenthesisByBacktracking(List<String> ans, String temp, int n, int open, int close){
        if(temp.length() == n*2){
            ans.add(temp);
        }else{
            if(open < n){
                //temp += "(";// wrong, 不应该在这里改变temp。要将tmp+"("作为参数去给下一个函数检查。每次对temp的更改都是来自于当前的recursion的上一层.不然这里改变了temp的话再回到这里时temp和传入的时候不一样，就不是backtrack了，状态没保持住.
                generateParenthesisByBacktracking(ans, temp + "(", n, open + 1, close);
            }
            if(close < open){
                // temp += ")"; wrong
                generateParenthesisByBacktracking(ans, temp + ")", n, open, close + 1);
            }
        }
    }

}