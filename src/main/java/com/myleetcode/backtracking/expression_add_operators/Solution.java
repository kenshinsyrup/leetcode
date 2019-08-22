package com.myleetcode.backtracking.expression_add_operators;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> addOperators(String num, int target) {
        return addOperatorsByBacktracking(num, target);
    }

    /*
    出错点:
    1 思路: backtracking尝试所有数字组合，试所有四种操作
    2. backtracking的结构，内部是用来尝试构建所有的数字组合，注意跳过有leading 0但不是数字0的情况
    3. 注意无操作的情况，当我们仍在build num from numStr 0 idx时，无操作
    4. *的应用复杂，需要multated来辅助
    5. path的构建复杂，使用String而非StringBuilder主要是用来降低代码书写的复杂度
    6. long防止int溢出
    */

    // TC: O(4^N), N is the length of num. we have 4 recursion, that is the operators _ - * and the no operator(ie, 12 we dont put any operator between them, this is how the curNumStr come, so this is also a choice)
    // SC: O(N)
    // https://leetcode.com/problems/expression-add-operators/discuss/71895/Java-Standard-Backtrace-AC-Solutoin-short-and-clear
    private List<String> addOperatorsByBacktracking(String num, int target){
        List<String> ret = new ArrayList<>();
        if(num == null || num.length() == 0){
            return ret;
        }

        backtracking(num, "", ret, target, 0, 0, 0);

        return ret;
    }

    private void backtracking(String numStr, String pathStr, List<String> ret, int target, int startIdx, long curSum, long multated){
        // base, exhausted numStr
        if(startIdx == numStr.length()){
            // if find
            if(curSum == target){
                ret.add(pathStr);
            }

            return;
        }

        // explore
        for(int i = startIdx; i < numStr.length(); i++){
            // !!! get current num, because the curNum is from startIdx through i, so if the char at startIdx is 0 and i!=startIdx, that means a num not 0 with leading 0, that is invalid, we pass this situation.
            if(i != startIdx && numStr.charAt(startIdx) == '0'){
                break;
            }
            String curNumStr = numStr.substring(startIdx, i + 1);

            // !!! get current num with long, if larger than max int, pass
            Long curNumL = Long.parseLong(curNumStr);
            if(curNumL > Integer.MAX_VALUE){
                break;
            }

            // !!! special case: pos is 0, means we are stil build the very first num, no operator needed, and the whole num is the curNumL
            if(startIdx == 0){
                backtracking(numStr, pathStr + curNumStr, ret, target, i + 1, curNumL, curNumL);
            }else{
                // plus
                backtracking(numStr, pathStr + "+" + curNumL, ret, target, i + 1, curSum + curNumL, curNumL);
                // minus
                backtracking(numStr, pathStr + "-" + curNumL, ret, target, i + 1, curSum - curNumL, -curNumL);
                // !!! multiply: tricky, multiply have high priority than any other operators, and we have no ( and ) operator to modify it. So we need to resotre the status of curSum then apply the *.
                // to restore is hard to understand, we first think tha + and - before current *, that means, if we plus a 10 in last step, and we want to apply * 2 this step, then we should know the 10 should first * 2 then we plus 20, not plus 10 then * 2, so we should first restore the multated value, that is minus it, then we caculated their multiplication, then plus to the restored curSum.
                // then let's think * before current *, since when operator is *, we mark the current mutated as previous mutated multiply curNumL, so it also applied. Let's say we want to caculate 2*3*5, now we have 2*3, then curSum is 6, mutated is 2*3 is 6, since we want to *5, so we resotre, that means curSum-mulatated means we get 0, then we caculate the previous multated 2*3 multiply with curNumL 5 get 30, correctly, then we pass the new multated 30 to next recursion
                backtracking(numStr, pathStr + "*" + curNumL, ret, target, i + 1, curSum - multated + multated * curNumL, multated * curNumL);
            }
        }
    }
}
