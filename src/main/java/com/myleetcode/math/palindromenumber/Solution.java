package com.myleetcode.math.palindromenumber;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public boolean isPalindrome(int x) {
        // special case
        if(x < 0){
            return false;
        }
        if(x == 0){
            return true;
        }
        if(x > Integer.MAX_VALUE){
            return false;
        }

        return isPalindromeByIntAndStack(x); // follow up
        // return isPalindromeByStringAndTwoPointer(x); // naive solution

    }


    /* intuition:
    if we could make it to string, it becomes to the classic check palindromic string problem, and here we only check is or not, that's easy, two pointer(same as list).
    */
     /*
     Say N is the length of the int x, not value
     TC: O(N),
     SC: O(N)
     */
    private boolean isPalindromeByStringAndTwoPointer(int x){
        String xStr = Integer.toString(x);
        int i = 0;
        int j = xStr.length() - 1;

        // check
        while(i <= j){
            if(xStr.charAt(i) != xStr.charAt(j)){
                return false;
            }
            i++;
            j--;
        }

        return true;
    }

    // follow up: 如果不能转成String的话，就利用数字的特性，一个数字一个数字的比较，要用两次循环，那么很多解法，都是先%到一个东西比如list,然后再处理
    /* intuition:
    Loop, every time we % to get the tail num and / to get the head num to compare. But, then problem is to / what?
    So consider only use % to get tail, we could put every tail to a Stack, and then in another loop, we % and check pop value, dont forget every time to / 10 to reduce num.
    */
    /*
    Say N is the length of the int x, not value
    TC: O(N),
    SC: O(N)
    */
    private boolean isPalindromeByIntAndStack(int x){
        Deque<Integer> intStack = new ArrayDeque<>();
        int temp = x;
        while(temp != 0){
            intStack.push(temp % 10);
            temp = temp / 10;
        }

        while(!intStack.isEmpty()){
            if(intStack.pop() != x % 10){
                return false;
            }
            x = x / 10;
        }

        return true;

    }
}