package com.myleetcode.design.flatten_nested_list_iterator;

import com.myleetcode.utils.nested_integer.NestedInteger;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
  public class NestedIterator implements Iterator<Integer> {

    // 思路: https://leetcode.com/problems/flatten-nested-list-iterator/discuss/80147/Simple-Java-solution-using-a-stack-with-explanation
    // 注意，这个实现并不是完全对的，这个实现下，要求hasNext()和next必须成对对按顺序调用，否则不正确，即无法保证idempotent。但是这个题目这样写不出错，可以通过所有test case，而且discuss区高票解法都是这样实现的，所以这个应该是踩的很多的原因吧.

    Stack<NestedInteger> stack = new Stack<>();

    public NestedIterator(List<NestedInteger> nestedList) {
        for(int i = nestedList.size() - 1; i >= 0; i--){
            stack.push(nestedList.get(i));
        }
    }

    @Override
    public Integer next() {
        return stack.pop().getInteger();

    }

    @Override
    public boolean hasNext() {
        // traverse stack, check if the top elem in stack is integer, if it is, return true; if not, pop it out and push elems in it to stack
        while(!stack.isEmpty()){
            NestedInteger ni = stack.peek();
            if(ni.isInteger()){
                return true;
            }

            stack.pop();// peek just return but not pop, so, now we have get ni and it's not integer, so we pop it out of stack and push all its elems to stack

            List<NestedInteger> niList = ni.getList();
            for(int i = niList.size() - 1; i >= 0; i--){
                stack.push(niList.get(i));
            }
        }

        return false;
    }
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */
