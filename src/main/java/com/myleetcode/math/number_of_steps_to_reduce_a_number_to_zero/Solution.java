package com.myleetcode.math.number_of_steps_to_reduce_a_number_to_zero;

public class Solution {
    public int numberOfSteps(int num) {
        return numberOfStepsBySimulate(num);
    }

    private int numberOfStepsBySimulate(int num) {
        if (num <= 0) {
            return 0;
        }

        int steps = 0;
        while (num > 0) {
            if (num % 2 == 0) {
                num /= 2;
            } else {
                num -= 1;
            }

            steps++;
        }

        return steps;
    }
}
