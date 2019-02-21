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
                //temp += "(";// 如果只写这一行是错误的, 不应该在这里改变temp而不对其进行复原。一般情况下，对于String，可以直接将tmp+"("作为参数去给下一个函数检查，这样每次对temp的更改都是来自于当前的recursion的上一层，不在本层改变temp的话再回到这里时temp和传入本层的时候一样，就是backtrack了，否则如果只写一个temp+="("就是状态没保持住.如果要使用temp+="("的形式，就是写法和list的方式保持一致(易于理解和总结题型pattern)，那么需要在backtracking回来的时候让temp恢复原样，也就是下面的注释语句temp = temp.substring(0, temp.length() - 1);
                generateParenthesisByBacktracking(ans, temp + "(", n, open + 1, close);
                // 配合上面注释掉的temp += "("; 结果也是对的
                //generateParenthesisByBacktracking(ans, temp, n, open + 1, close);
                //temp = temp.substring(0, temp.length() - 1);
            }
            if(close < open){

                generateParenthesisByBacktracking(ans, temp + ")", n, open, close + 1);
                // result is the same as above
                //  temp += ")";
                //  generateParenthesisByBacktracking(ans, temp, n, open, close + 1);
                // temp = temp.substring(0, temp.length() - 1);
            }
        }
    }

}