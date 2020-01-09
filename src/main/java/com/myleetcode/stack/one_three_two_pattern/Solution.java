package com.myleetcode.stack.one_three_two_pattern;

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public boolean find132pattern(int[] nums) {
        // return find132patternByBruteForce(nums); // TLE
        // return find132patternByBetterBruteForce(nums);
        return find132patternByStack(nums);
    }

    /*
    TLE
    Brute Force:
    Just traverse to find the pattern.

    TC: O(N^3)
    SC: O(1)
    */
    private boolean find132patternByBruteForce(int[] nums) {
        // Special case.
        if (nums == null || nums.length < 3) {
            return false;
        }

        int len = nums.length;
        for (int i = 0; i < len; i++) {
            int numI = nums[i];

            for (int j = i + 1; j < len; j++) {
                int numJ = nums[j];

                if (numI < numJ) {
                    for (int k = j + 1; k < len; k++) {
                        int numK = nums[k];

                        if (numI < numK && numK < numJ) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /*
    Better Brute Force:
    Keep track of the largest scope of numI:numJ, and try to find the numK in nums[j:end]

    TC: O(N^2)
    SC: O(1)
    */
    private boolean find132patternByBetterBruteForce(int[] nums) {
        // Special case.
        if (nums == null || nums.length < 3) {
            return false;
        }

        int len = nums.length;

        // If there's valid 132 pattern, then the numK must exist in the scope numI:numJ, so we could keep track of the largest scope of numI:numJ where numI<numJ for every nums[j], then try to find the numK
        int numI = Integer.MAX_VALUE;
        for (int j = 0; j < len; j++) {
            int numJ = nums[j];
            numI = Math.min(numI, numJ);

            for (int k = j + 1; k < len; k++) {
                int numK = nums[k];

                if (numI < numK && numK < numJ) {
                    return true;
                }
            }
        }

        return false;
    }

    /*
    Stack:
    https://leetcode.com/problems/132-pattern/discuss/400774/Intuitive-Java-Solution-With-Explanation

    TC: O(N), in worst case, every number in and out of stack once.
    SC: O(N)
    */
    private boolean find132patternByStack(int[] nums) {
        // Special case.
        if (nums == null || nums.length < 3) {
            return false;
        }

        // Get the minArr where stores the corresponding min num for each index in nums. Such we could know the largest value scope for each num in nums, ie we know the largest scope of numI:numJ for each nums[j]
        int len = nums.length;
        int[] minArr = new int[len];
        minArr[0] = nums[0];
        for (int i = 1; i < len; i++) {
            minArr[i] = Math.min(minArr[i - 1], nums[i]);
        }

        Deque<Integer> numKCandidateStack = new ArrayDeque<>();
        for (int idx = len - 1; idx >= 0; idx--) {
            int curNum = nums[idx];
            int numI = minArr[idx];
            int numJ = curNum;

            // If numJ > numI, we have a scope numI:numJ and it's the largest scope for this nums[j], so we could try to find the numK in this scope.
            if (numJ > numI) {
                // Try to find numK>numI
                while (!numKCandidateStack.isEmpty() && numKCandidateStack.peek() <= numI) {
                    numKCandidateStack.pop();
                }
                // Try to find numK<numJ
                if (!numKCandidateStack.isEmpty() && numKCandidateStack.peek() < numJ) {
                    return true;
                }

                // If not find such numK in current scope, then we have a new numK candidate for next numJ(in its scope) since the idx is moving to left.
                numKCandidateStack.push(curNum);
            }
        }

        return false;
    }
}
