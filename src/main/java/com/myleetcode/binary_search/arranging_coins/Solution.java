package com.myleetcode.binary_search.arranging_coins;

class Solution {
    int stairs;
    public int arrangeCoins(int n) {
        // return arrangeCoinsByDP(n); // 错误，LME，大数求和越界

        // return arrangeCoinsByMath(n); // 错误，大数求和越界
        // return arrangeCoinsByMathII(n); // 正确，O(n)

        return arrangeCoinsByBS(n); // 机智,O(logn)

    }

    // 根据tag，这个题可以用BS做
    // Our target is to search for a factor k where k * (k+1) / 2 <= n, and this k is the critical value that its next value(ie k+1) could not meet this. This is referencing Gauss' formula on summation of consecutive numbers.
    // Using binary search for such purpose gives you O(log n) time.
    // 就是找最大的k，k满足条件k*(k+1)/2 <= n，高斯求和公式. 但是同样的，必须考虑大数求和越界的问题，所以要到long
    private int arrangeCoinsByBS(int n){
        if(n <= 0){
            return 0;
        }

        // 重要，必须用long来存储避免int越界问题
        long start = 1;
        long end = n;
        long mid = 0;

        long gaussSum = 0;
        long nLong = n;

        while(start <= end){
            mid = start + (end - start) / 2;

            gaussSum = mid * (mid + 1) / 2;

            if(gaussSum == nLong){
                return (int)mid;
            }else if(gaussSum < nLong){
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }

        // BS的三个返回值：
        // 如果要找等于(形如上面的gaussSum == nLong)，那么在while循环里返回mid
        // 如果要找最后一个满足小于条件的值(形如上面gaussSum < nLong)的最后一个mid，那么返回start-1
        // 如果要找最后一个满足大于条件的值(形如上面gaussSum > nLong)的最后一个mid，那么返回end+1
        return (int)start - 1; // 不要return mid;因为我们这里要找的是满足gaussSum<n的最后一个mid，那么这个时候start = mid + 1; 这时候while循环会继续一次，然后mid被重新赋值变成了midNew,然后不满足gaussSum<n所以start没变，所以end变，然后这时候while循环断了。所以最后能保存了符合要求的mid的是start，即start - 1， 而不是midNew。

    }

    // 根据arrangeCoinsByMath所以我们不能试图保存所有的台阶使用的coin去趋近n，而应该让n一直去减去我们用掉的coin，比如一阶1，那么n-=1，二阶2，那么n-=2...直到i阶i，那么n-=i,这时候n<0，那么i-1就是我们最大的台阶
    private int arrangeCoinsByMathII(int n){
        // https://leetcode.com/problems/arranging-coins/discuss/92281/JAVA-Cleaner-and-easier-to-understand-solution.
        int i = 0;

        while(n >= 0){
            i++;
            n -= i;
        }

        return i - 1;
    }

    // intuition: math problem? define use(i) = 1+...+i, then we need sum(use(1), use(2)...use(i)) > n, return i -1
    // 这个解法的问题是求sum的过程中，sum可能会越界
    private int arrangeCoinsByMath(int n){
        // special case
        if(n <= 0){
            return 0;
        }

        int sum = 0;
        int jthSum = 0;
        int stairs;
        for(int i = 1; i <= n; i++){
            jthSum = 0;
            for(int j = 1; j <= i; j++){
                jthSum += j;
            }
            sum += jthSum;
            if(sum > n){
                return i - 1;
            }
        }

        return 0;
    }

    private int arrangeCoinsByDP(int n){
        int[] dp = new int[n + 1];
        arrangeCoinsByDPTopDown(n, n, dp);
        return stairs;
    }

    // 这个解法MLE因为深度太大了, 而且同样会涉及到sum越界的问题
    // 用dp
    // f(i) means 1...i stairs needed coins, then f(i) = f(i - 1) + i, if n >= fi(i)
    // returns i stairs needed coins
    private int arrangeCoinsByDPTopDown(int n, int i, int[] dp){
        // base case
        if(i <= 0){
            return 0;
        }

        // memo
        if(dp[i] != 0){
            return dp[i];
        }

        // dp formula
        int preSum = arrangeCoinsByDPTopDown(n, i - 1, dp);
        if(preSum + i > n){
            dp[i] = preSum;
        }else{
            dp[i] = preSum + i;
            stairs = i;
        }

        return dp[i];

    }

}
