package com.myleetcode.backtracking.remove_invalid_parentheses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    public List<String> removeInvalidParentheses(String s) {
        return removeInvalidParenthesesByBacktracking(s);
    }

    /*
    出错点：
    1 思路: 想明白backtracking要做的事情，也就是我们要如何试。这里，我们每次遇到一个ch，由于我们是尝试在删除一定数量的(和)的基础上寻找sol，所以有两个操作：删除当前ch也就是不加入snippet；加入snippet。然后对这两个操作进行细分，主要是条件要清晰。主要是ch是)且我们需要他时。
    另外，对于任何一个符合删除条件对ch，我们都尝试删除，然后这个recursion结束之后，我们不能返回，需要继续测试不删除
    2 细节: snippet可能是重复的，所以需要用Set来保存结果，最后转成List
    3 智障： 当idx>=str.length()时，要return，不止是当sb合法时返回
    4 智障；在special case检查时，错误的认为如果str长度小于2，那么输出[""].这个时不对的，因为str长度为1的时候可以包含一个非( or )的字符比如"n". 不如直接让backtracking去处理
    */

    // intuition: Backtracking
    // first, traverse the String to get the # of ( and ) we need to remove
    // second, do Backtracking to try to remove the leftPa and rightPa until reach the end of String, if leftPa and rightPa are both 0, this is a solution; if not at the end, we have two options to choose:
// 1 try to ignore current char, this only could be done if current is ( or ) and if leftPa or rightPa not 0
// 2 try to add current char into snippet String: 2.1 if current char is not ( or ), just add in and recursion further and back; 2.2 current char is (, add in and leftCount++ and recursion and back; 2.3 is ) and if rightCount < leftCount(otherwise not a valid snippet String), rightCount++ and add in and recursion then back
    // TC: O(2^N)
    // SC: O(N)
    private List<String> removeInvalidParenthesesByBacktracking(String str){
        List<String> ret = new ArrayList<>();
        if(str == null || str.length() == 0){
            ret.add("");
            return ret;
        }

        // 1
        int leftPa = 0;  // the min # of ( need remove
        int rightPa = 0; // the min # of ) need remove
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) != '(' && str.charAt(i) != ')'){
                continue;
            }

            if(str.charAt(i) == '('){
                leftPa++;
            }else{
                if(leftPa > 0){
                    leftPa--;
                }else{
                    rightPa++;
                }
            }
        }

        // 2, try
        Set<String> retSet = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        buildByBacktracking(retSet, str, 0, leftPa, rightPa, 0, 0, sb);

        for(String snippet: retSet){
            ret.add(snippet);
        }
        return ret;

    }

    private void buildByBacktracking(Set<String> retSet, String str, int idx, int leftPa, int rightPa, int leftCount, int rightCount, StringBuilder sb){
        // base case, build retSet
        if(idx >= str.length()){
            if(leftPa == 0 && rightPa == 0){
                retSet.add(sb.toString());
            }
            return;
        }

        char ch = str.charAt(idx);

        // 1, try to ignore current ch
        if(ch == '(' && leftPa > 0){
            buildByBacktracking(retSet, str, idx + 1, leftPa - 1, rightPa, leftCount, rightCount, sb);
        }
        if(ch == ')' && rightPa > 0){
            buildByBacktracking(retSet, str, idx + 1, leftPa, rightPa - 1, leftCount, rightCount, sb);
        }

        // !!! after ignore done, we try to include current ch
        sb.append(ch);

        // 1 not ( or )
        if(ch != '(' && ch != ')'){
            buildByBacktracking(retSet, str, idx + 1, leftPa, rightPa, leftCount, rightCount, sb);
        }else{
            // 2 ( or )
            if(ch == '('){
                buildByBacktracking(retSet, str, idx + 1, leftPa, rightPa, leftCount + 1, rightCount, sb);
            }
            if(ch == ')' && rightCount < leftCount){ // !!!
                buildByBacktracking(retSet, str, idx + 1, leftPa, rightPa, leftCount, rightCount + 1, sb);
            }
        }

        // back
        sb.deleteCharAt(sb.length() - 1);
    }

}
