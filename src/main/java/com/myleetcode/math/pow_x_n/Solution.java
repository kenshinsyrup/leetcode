package com.myleetcode.math.pow_x_n;

class Solution {
    public double myPow(double x, int n) {
        // return myPowByMath(x, n);
        // return myPowByMathII(x, n);

        return myPowByFastPow(x, n);
    }

    private double myPowByFastPow(double x, int n){
        long nLong = n; // 注意，这类问题必须用long保存下int，主要是因为我们要取反。对于signed int来说，0在正数这边导致负数的绝对值比正数大1，也就是说 -Integer.MIN_VALUE 是无法存在int类型的，会越界
        if (nLong < 0) {
            x = 1 / x;
            nLong = -nLong;
        }

        return fastPow(x, nLong);
    }

    /*原理
    Assume we have got the result of x^(n/2), and now we want to get the result of x^(n).
Let A be result of x^(n/2)
If n is even, we can use the formula (x^n)^2 = x^(2*n) to get that x^n = A*A
If n is odd, then A * A = x^(n-1) so We need to multiply another x to the get the correct result, so x^n = A*A*x
This approach can be easily implemented using recursion. We call this method "Fast Power", because we only need at most O(log(n))computations to get x^n
n偶数，x^n = (x^(n/2)) * (x^(n/2))
n奇数，则n - 1偶数, x^n = (x^(n-1)) * x, 而在地板除中，n/2与(n-1)/2相等，所以x^n = (x^(n/2)) * (x^(n/2)) * x
    */
    private double fastPow(double x, long n){
        // base case
        if(n == 0){
            return 1.0;
        }

        double halfPow = fastPow(x, n / 2); // 如果这里用double product = fastPow(x, n / 2)*fastPow(x, n / 2)会导致递归过深
        if(n % 2 == 0){
            return halfPow * halfPow;
        }else{
            return halfPow * halfPow * x;
        }
    }

    // wrong: TLE
    // intuition: translate to code?
    private double myPowByMathII(double x, int n){
        // special case
        if(x == 0){
            return x;
        }

        long nLong = n;
        boolean nIsNegative = n < 0 ? true : false;
        if(nIsNegative){
            nLong = -nLong;
            x = 1 / x;
        }

        double ret = 1;
        for(long i = 1; i <= nLong; i++){
            ret *= x;
        }

        return ret;

    }

    // wrong: input 0.00001 2147483647, output: TLE
    // intuition: translate to code?
    private double myPowByMath(double x, int n){
        // special case
        if(x == 0){
            return x;
        }

        // 这段代码完全没有意义。包括下面同样注释段地方，本意是认为负数的偶次幂是正数，奇数次幂是负数，想保留这个，但是实际上，
        /*for(int i = 1; i <= n; i++){
                ret *= x;
          }
        这个代码自己就实现了这个关系了
        */
        boolean xIsNegative = x < 0 ? true : false;
        if(xIsNegative){
            x = -x;
        }

        boolean nIsNegative = n < 0 ? true : false;
        if(nIsNegative){
            n = -n;
        }

        double ret = 1;
        for(int i = 1; i <= n; i++){
            ret *= x;
        }

        // 这段代码完全没有意义
        if(xIsNegative && n % 2 != 0){
            ret = -ret;
        }

        return nIsNegative ? 1 / ret : ret;

    }
}
