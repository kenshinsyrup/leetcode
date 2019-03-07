package com.myleetcode.math.palindromenumber;

import java.util.Stack;

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

        // return isPalindromeByIntAndStack(x);
        return isPalindromeByStringAndTwoPointer(x);

    }

    // if we could make it to string, it becomes to the classic check palindromic string problem, and here we only check is or not, that's easy, two pointer(same as list).
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

    // intuition: loop, every time we % to get the tail num and / to get the head num to compare. But, then problem is to / what?
    // So consider only use % to get tail, we could put every tail to a Stack, and then in another loop, we % and check pop value, dont forget every time to / 10 to reduce num.
    // 其实想多了，如果要用两次循环的话，那么很多解法，都是先%到一个东西比如list,然后再处理
    private boolean isPalindromeByIntAndStack(int x){
        Stack<Integer> intStack = new Stack<Integer>();
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
