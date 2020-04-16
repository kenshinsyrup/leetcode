package com.myleetcode.math.implement_rand10_using_rand7;

public class Solution extends SolBase {

    /**
     * The rand7() API is already defined in the parent class SolBase.
     * public int rand7();
     *
     * @return a random integer in the range 1 to 7
     */
    public int rand10() {
        return generate();
    }

    /*
    Idea: rand7() -> rand49() -> rand40() -> rand10()

    https://leetcode.com/problems/implement-rand10-using-rand7/discuss/150301/Three-line-Java-solution-the-idea-can-be-generalized-to-%22Implement-RandM()-Using-RandN()%22
    https://leetcode.com/problems/implement-rand10-using-rand7/discuss/150301/Three-line-Java-solution-the-idea-can-be-generalized-to-"Implement-RandM()-Using-RandN()"/246718

    */
    private int generate() {

        int row = rand7();
        int col = rand7();
        int ret = 7 * (row - 1) + col; // 1-49 with probability 1/49

        // To make even probability for 1-10, the most efficient way is to reject at least 41-49 from the possible ret candidates(because from 49 to 1, the first number of multiple of 10 is 40), so if ret>40 we re-generate it.
        while (ret > 40) {
            row = rand7();
            col = rand7();
            ret = 7 * (row - 1) + col;
        }

        return ret % 10 == 0 ? 10 : ret % 10;

    }
}

// For compile.
class SolBase {
    public int rand7() {
        return 0;
    }
}

