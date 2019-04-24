package com.myleetcode.backtracking.expression_add_operators;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> addOperators(String num, int target) {
        return addOperatorsByBacktracking(num, target);
    }

    // TC: O(4^N), N is the length of num. we have 4 recursion, that is the operators _ - * and the no operator(ie, 12 we dont put any operator between them, this is how the curNumStr come, so this is also a choice)
    // SC: O(N)
    // https://leetcode.com/problems/expression-add-operators/discuss/71895/Java-Standard-Backtrace-AC-Solutoin-short-and-clear
    // dfs, backtracking
    private List<String> addOperatorsByBacktracking(String num, int target){
        List<String> ret = new ArrayList<>();
        if(num == null || num.length() == 0){
            return ret;
        }

        backtracking(num, "", ret, target, 0, 0, 0);

        return ret;
    }

    private void backtracking(String num, String path, List<String> ret, int target, int pos, long curSum, long multated){
        // find
        if(pos == num.length()){
            if(curSum == target){
                ret.add(new String(path));
                return;
            }
        }

        // explore
        for(int i = pos; i < num.length(); i++){
            // get current num, because the curNum is from pos through i, so if the pos is 0 that means a num with leading 0, that is invalid, we pass this situation.
            if(i != pos && num.charAt(pos) == '0'){
                break;
            }
            String curNumStr = num.substring(pos, i + 1);
            Long curNumL = Long.parseLong(curNumStr);

            // special case: pos is 0, no operator could put in the left of curNum
            if(pos == 0){
                backtracking(num, path + curNumL, ret, target, i + 1, curSum + curNumL, curNumL);
            }else{
                // plus
                backtracking(num, path + "+" + curNumL, ret, target, i + 1, curSum + curNumL, curNumL);
                // minus
                backtracking(num, path + "-" + curNumL, ret, target, i + 1, curSum - curNumL, -curNumL);
                // multiply: tricky, multiply have high priority than any other operators, so we need to resotre the status of curSum then apply the *. that means, if we plus a 10 in last step, then we need to apply * 2 this step, we should know the 10 should first * 2 then we plus 20, not plus 10 then * 2, so we should first restore the multated value, that is minus it, then we caculated their multiplication, then plus to the restored curSum.
                backtracking(num, path + "*" + curNumL, ret, target, i + 1, curSum - multated + multated * curNumL, multated * curNumL);
            }
        }
    }
}
