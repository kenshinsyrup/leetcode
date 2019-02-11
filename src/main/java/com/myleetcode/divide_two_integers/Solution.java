package com.myleetcode.divide_two_integers;


class Solution {
    public int divide(int dividend, int divisor) {
        // special case
        if(divisor == 0){
            return Integer.MAX_VALUE;
        }
//         这里不要直接写成if(dividend == 0 || dividend < divisor)，因为如果dividend是负数，divisor是正数，那肯定是满足条件，就会直接return 0; 也不要写if(dividend == 0 || (Math.abs(dividend) < Math.abs(divisor)))，因为这样有一种特殊情况无法处理就是当dividend是Integer.MIN_VALUE时，其abs会越界，导致奇怪的返回0事件
//         我们只检查dividend是否为0，为0则不论divisor为何（我们在前面处理来divisor为0的情况）都return 0没问题。
//         然后任何涉及来具体数字处理的比如abs之类的，我们先把数字转成long再说
        if(dividend == 0){
            return 0;
        }
        
        //get sign
        int sign = 1;
        if((dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0)){
            sign = -1;
        }
        
        //convert to long
        long longDividend = Math.abs((long)dividend);
        long longDivisor = Math.abs((long)divisor);
        
        // long answer
        long longAns = longDivide(longDividend, longDivisor);

        // handle overflow
        int ans = 0;
        if(longAns > Integer.MAX_VALUE){
            ans = (sign == 1)? Integer.MAX_VALUE: Integer.MIN_VALUE;
        }else{
            ans = (sign == 1)?(int)longAns: -(int)longAns;
        }
        
        return ans;
    }
    
    private long longDivide(long dividend, long divisor){
        // 递归出口
        if(dividend < divisor){
            return 0;
        }
        
        long sum = divisor;
        long multiple = 1;
        while(sum + sum <= dividend){
            sum = sum + sum; // sum*2
            multiple = multiple + multiple; //记录下来divisor被用了的次数，也就是商.
        }
        // divisor每次都是在翻倍的方式来趋近dividend，可以视作二分。如果某次翻倍后会比dividend大（但还没翻倍），我们就让dividend-sum来得到一个小一点但dividend来继续寻找趋近她但divisor的个数，直到递归结束
        // divisor用翻倍的方式来趋近dividend，multiple就是计数器，从1开始用同样的翻倍倍率就可以记录到我们用来多少个divisor来趋近dividend，直到divisor大于了不断被减小的dividend来退出递归。这就是商。
        return multiple + longDivide(dividend - sum, divisor);
    }
}