package com.myleetcode.bit_manipulate.single_number_iii;

public class Solution {
    public int[] singleNumber(int[] nums) {
        return singleNumberByBM(nums);
    }

    /*
    Solution: https://leetcode.com/problems/single-number-iii/discuss/68900/Accepted-C%2B%2BJava-O(n)-time-O(1)-space-Easy-Solution-with-Detail-Explanations

    Explaination: https://leetcode.com/problems/single-number-iii/discuss/68900/Accepted-C++Java-O(n)-time-O(1)-space-Easy-Solution-with-Detail-Explanations/218733
    1. Let a and b be the two unique numbers
2. XORing all numbers gets you (a xor b)
3. (a xor b) must be non-zero otherwise they are equal
4. If bit_i in (a xor b) is 1, bit_i at a and b are different.
5. So, if we use this formula: m & ~(m-1), it gives us a number that only the right most bit is set, ie, the right most bit 1 in number m.
   Then, let's say the bit_l is the right_most_set_bit_number of (a xor b), we could use it to partition the nums into two groups: one group with bit_l == 1 and the other group with bit_l == 0.
6. a is in one group and b is in the other.
   a is the only single number in its group.
   b is also the only single number in its group.
7. XORing all numbers in a's group to get a
   XORing all numbers in b's group to get b

   Right Most Set Bit: https://algorithms.tutorialhorizon.com/find-the-right-most-set-bit-of-a-number/

   Visualization: https://leetcode.com/problems/single-number-iii/discuss/68900/Accepted-C++Java-O(n)-time-O(1)-space-Easy-Solution-with-Detail-Explanations/263807
    */
    private int[] singleNumberByBM(int[] nums) {
        if (nums == null || nums.length == 0) {
            return nums;
        }

        // Pass 1 :
        // Get the XOR of the two numbers we need to find
        int diff = 0;
        for (int num : nums) {
            diff ^= num;
        }
        // Get its last set bit
        diff &= ~(diff - 1);

        // Pass 2 :
        int[] rets = {0, 0}; // this array stores the two numbers we will return
        for (int num : nums) {
            if ((num & diff) == 0) { // the bit is not set
                rets[0] ^= num;
            } else {  // the bit is set
                rets[1] ^= num;
            }
        }

        return rets;
    }
}
