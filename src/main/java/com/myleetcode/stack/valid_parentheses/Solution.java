package com.myleetcode.stack.valid_parentheses;

import java.util.HashMap;
import java.util.Stack;

class Solution {
    public boolean isValid(String s) {
        // 错误思路：
        // use map, key is character, value is number. at last, check if pairs valid. 复杂度TC O(n), SC O(n)。 然后可以改成，map只存左边，左边的值为正，右边值为负，那么在遍历过程中map不能存在负值，当遍历完成时，map不能存在非0值，即为valid。
//         解释：上述思路是针对单一类型的括号的，由于该题目有多个类型的括号，而[{]}这样的括号是非法的，但是上面的算法会认为合法.

        // stack
        /*
        Algorithm

1 Initialize a stack S.
2 Process each bracket of the expression one at a time.
3 If we encounter an opening bracket, we simply push it onto the stack. This means we will process it later, let us simply move onto the sub-expression ahead.
4 If we encounter a closing bracket, then we check the element on top of the stack. If the element at the top of the stack is an opening bracket of the same type, then we pop it off the stack and continue processing. Else, this implies an invalid expression.
5 In the end, if we are left with a stack still having elements, then this implies an invalid expression.
        */

        // https://leetcode.com/problems/valid-parentheses/discuss/9178/Short-java-solution这个解法直接不需要使用map。加深对stack这些经典数据结构的了解。

        // special case
        if(s == null){
            return true;
        }

        return isValidByStack(s);

    }
    // TC: O(n)
    // SC: O(n)
    private boolean isValidByStack(String s){
        // use a map to store right parts of parenthesis )}] as key and coresponding part as value. 这里使用右半边作为key是因为如此一来：我们要push入栈的东西不存在于map的keys中，所以map也不对其进行操作，没问题；而对于存在于map的keys中的char，我们要获取他的value来于栈中的内容对比，所以也没问题。但是如果改用左边作为key，那么就是包含在key中的要push入栈，不包含在key中的，我们要获取他所对应的部分的时候就无从获取了。
//         举例就是，如果map是{{'(':')'}},那么在遇到了string中的')'时，我们无法用O(1)的时间获取到')'的对应内容去和栈中pop出来的内容比较，就失去了map的意义。
        HashMap<Character, Character> map = new HashMap<Character, Character>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');

        // use stack to store left part, when encounter right part, pop and compare.
        Stack<Character> stack = new Stack<Character>();

        // traverse
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);

            // check character
            if(!map.containsKey(c)){
                stack.push(c);
            }else{
                // 注意stack为空时执行pop会直接exception，所以需要处理下
                if(stack.isEmpty()){
                    return false;
                }else{
                    Character topChar = stack.pop();
                    if(topChar != map.get(c)){
                        return false;
                    }
                }
            }
        }

        return stack.isEmpty();

    }
}
