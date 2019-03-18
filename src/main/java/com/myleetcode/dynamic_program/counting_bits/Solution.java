package com.myleetcode.dynamic_program.counting_bits;

class Solution {
    public int[] countBits(int num) {
       // 用dp的思路的话
        // 思路讲解：https://leetcode.com/problems/counting-bits/discuss/79576/Share-my-thinking-a-JAVA-solution。该讲解中，最后的i&[i-1]==0是用来找2的幂，但实际上也符合偶数但定律，所以可以按照偶数的方法写，不必要单独写。
//     Solution considering odd/even status of the number:
// In this solution, we can think about if the number if even or odd. 
        // If the number is even, the number of 1s equal to the number which is half of it. For e.g., the number 8 has the same 1s as the number 4. The number 10 has the same amount of 1 bits as number 5. That is because number i is just left shift by 1 bit from number i / 2. Therefore, they should have the same number of 1 bits. 

// For the number as odd, e.g. 1, 3, 5, 7. The number of 1 bits is equal to the number (i - 1) plus 1. For e.g., for number 3, the number of 1 bits equals to the number 2 plus 1. For number 11, it equals to number 10 + 1. 
        
        int[] res = new int[num + 1];
        res[0] = 0; //dp的初始状态
        
        // i也可以从0开始，不过浪费了一次操作而已
        for(int i = 1; i < num + 1; i++){
            if(i % 2 == 0){
                res[i] = res[i/2];
            }else{
                res[i] = res[i - 1] + 1;
            }
        }
        
        return res;
        
    }
}