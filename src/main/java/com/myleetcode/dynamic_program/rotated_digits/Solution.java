package com.myleetcode.dynamic_program.rotated_digits;

public class Solution {
    public int rotatedDigits(int N) {
        // special case
        if(N <= 0){
            return 0;
        }

        return rotatedDigitsByDP(N);
    }

    // time complexity is O(N)
    private int rotatedDigitsByDP(int N){
        //https://leetcode.com/problems/rotated-digits/discuss/117975/Java-dp-solution-9ms
        // first:
        /*
        For each X from 1 to N, let's analyze whether X is good.

If X has a digit that does not have a valid rotation (3, 4, or 7), then it can't be good.

If X doesn't have a digit that rotates to a different digit (2, 5, 6, or 9), it can't be good because X will be the same after rotation.

Otherwise, X will successfully rotate to a valid different number.
        */

        // second:
        // we could use dp because, if we know a number x is valid, then for x*10 + y-from-[0...9], we could jsut fetch the dp value of x, and check the y, if x and y both belong to [0, 1, 8] then after rotation they are the same, else if one of them belongs to [0, 1, 8] and the other belongs to [2, 5, 6, 9] then the number is valid, else they are not rotatable.
        // so dp value should represent 3 status. we could use: 0 for not rotatable number; 1 for could rotate but after that it keeps no change; 2 for good number

        int[] dp = new int[N + 1];

        // base case. Actually base case should be dp[0...9], but, we could not know if N is greater than 9 or something, so, we write these in the traversal to make code clear. Just for clarification.

        // count good number
        int count = 0;
        for(int i = 0; i <= N; i++){
            if(i < 10){
                // default is 0 so we dont write it
                if(i == 0 || i == 1 || i == 8){
                    dp[i] = 1;
                }else if(i == 2 || i == 5 || i == 6 || i == 9){
                    dp[i] = 2;
                    count++;
                }
            }else{
                // we split number greater than 10
                int left = i / 10; // left parts, 1 or more digits
                int right = i % 10; // right parts, 1 digit
                if(dp[left] == 1 && dp[right] == 1){
                    dp[i] = 1;
                }else if((dp[left] == 1 && dp[right] == 2) || (dp[left] == 2 && dp[right] == 1) || (dp[left] == 2 && dp[right] == 2)){
                    dp[i] = 2;
                    count++;
                }
            }
        }

        // we use count to remember the good number. dp[] use to avoid re-caculation of numbers we have know the answer. for example, we need to caculate 51 now, but we already know dp[5] is 2 and dp[1] is 1, so, after we split the number to 5 and 1, we know 52 is good, this save the time to caculate every digit with [2, 5, 6, 9] and [0, 1, 8] balabala. Especially when number is getting greater, for example, next we caculate 512, since we know dp[51] is 2, and dp[2] is 2, so dp[512] is 2, we dont need to re-caculate every digit.

        return count;

    }
}
