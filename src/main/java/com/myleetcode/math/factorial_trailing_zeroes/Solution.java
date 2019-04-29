package com.myleetcode.math.factorial_trailing_zeroes;

class Solution {
    public int trailingZeroes(int n) {
        // return trailingZeroesByCount(n); // WRONG DEDUCTION
        return trailingZeroesByFive(n);
    }

    // TC: O(log5(N)), O(logN);
    // the problem is to find how many 5
    // https://leetcode.com/problems/factorial-trailing-zeroes/discuss/196311/topic
    /*
    https://leetcode.com/problems/factorial-trailing-zeroes/discuss/52367/My-explanation-of-the-Log(n)-solution
    10 is the product of 2 and 5. In n!, we need to know how many 2 and 5, and the number of zeros is the minimum of the number of 2 and the number of 5.

Since multiple of 2 is more than multiple of 5, the number of zeros is dominant by the number of 5.

Here we expand

  2147483647!
=2 * 3 * ...* 5 ... *10 ... 15* ... * 25 ... * 50 ... * 125 ... * 250...
=2 * 3 * ...* 5 ... * (5^1*2)...(5^1*3)...*(5^2*1)...*(5^2*2)...*(5^3*1)...*(5^3*2)... (Equation 1)
We just count the number of 5 in Equation 1.

Multiple of 5 provides one 5, multiple of 25 provides two 5 and so on.

Note the duplication: multiple of 25 is also multiple of 5, so multiple of 25 only provides one extra 5.

Here is the basic solution:

return n/5 + n/25 + n/125 + n/625 + n/3125+...;
You can easily rewrite it to a loop.
    */
    private int trailingZeroesByFive(int n){
        int result=0; // how many 5 we need to caculate the number n
        while (n>0){
            result+=n/5;

            n/=5;
        }
        return result;
    }



    // WRONG. This deduction is wrong.
    // intuition: if we caculate the Factorial of n, we could get the trailing 0 number but that could give us the overflow in int, maybe long could save us? dont know, but that's not smart
    // we could deduce that the trailing 0 # is associated with the 0 and 5.
    /*
        x! # of 0 is 0
    5! # of 0 is 1
        x! # of 0 is 1
    10! # of 0 is 2
        x! # of 0 is 2
    15! # of 0 is 3
        x! # of 0 is 3
    20! # of 0 is 4
    ...
    */

    // the proble needs us give a O(logN) algo, we could first find the last number that has trailing 0 and is less than the n, then we could caculate the # of 0 of the num' factorial, then we figure out if the num and n's gap is more than 5, if it is, then plus one more.
   /* private int trailingZeroesByCount(int n){
        if(n <= 0){
            return 0;
        }

        int num = n;
        for(int i = 0; i < 10; i++){
            if((num-i) % 10 == 0){
                num -= i;
                break;
            }
        }

        int zeroes = 0;
        int i = 1;
        while(i * 10 <= num){
            zeroes += 2;

            i++;
        }

        if(n - num >= 5){
            zeroes += 1;
        }

        return zeroes;
    }*/
}
