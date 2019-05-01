package com.myleetcode.dynamic_program.paint_fence;

class Solution {
    public int numWays(int n, int k) {
        // return numWaysByDP(n, k);
        // return numWaysByDPII(n, k);
        return numWaysByDPIII(n, k);
    }

    // another way: https://leetcode.com/problems/paint-fence/discuss/178010/The-only-solution-you-need-to-read
    /*
If you are painting the ith post, you have two options:

make it different color as i-1th post
make it same color as i-1 th post (if you are allowed!)

simply add these for your answer:
num_ways(i) = num_ways_diff(i) + num_ways_same(i)

Now just think of how to calculate each of those functions.

The first one is easy. If you are painting the ith post, how many ways can you paint it to make it different from the i-1 th post? k-1

num_ways_diff(i) = num_ways(i-1) * (k-1)

The second one is hard, but not so hard when you think about it.

If you are painting the ith post, how many ways can you paint it to make it the same as the i-1th post? At first, we should think the answer is 1 -- it must be whatever color the last one was.

num_ways_same(i) = num_ways(i-1) * 1

But no! This will fail in the cases where painting the last post the same results in three adjacent posts of the same color! We need to consider ONLY the cases where the last two colors were different. But we can do that!

num_ways_diff(i-1) <- all the cases where the i-1th and i-2th are different.

THESE are the cases where can just plop the same color to the end, and no longer worry about causing three in a row to be the same.

num_ways_same(i) = num_ways_diff(i-1) * 1

We sum these for our answer, like I said before:

num_ways(i) = num_ways_diff(i) + num_ways_same(i)
= num_ways(i-1) * (k-1) + num_ways_diff(i-1)

We know how to compute num_ways_diff, so we can substitute:
num_ways(i) = num_ways(i-1) * (k-1) + num_ways(i-2) * (k-1)

We can even simplify a little more:

num_ways(i) = (num_ways(i-1) + num_ways(i-2)) * (k-1)

As a note, trying to intuitively understand that last line is impossible. If you think you understand it intuitively, you are fooling yourself. Only the original equation makes intuitive sense.
    */
    private int numWaysByDPIII(int n, int k){
        if(n <= 0 || k <= 0){
            return 0;
        }
        if(n == 1){
            return k;
        }
        if(n == 2){
            return k + k*(k-1);
        }

        int[] dp = new int[n + 1];
        dp[0] = 0;
        // if there is only one post, there are k ways to paint it
        dp[1] = k;
        // if there are only two posts, you can't make a triplet, so you
        // are free to paint however you want.
        // first post, k options. second post, k options
        dp[2] = k * k;

        for(int i = 3; i <= n; i++){
            dp[i] = (dp[i - 1] + dp[i - 2]) * (k - 1);
        }

        return dp[n];
    }

    // we have two conditions: ith fence has the same corlor as i-1th, or not
    // https://leetcode.com/problems/paint-fence/discuss/182181/Java-DP-solution-with-detailed-comments
    /*
diff = # of ways when color of last
        two posts is different
same = # of ways when color of last
        two posts is same
total ways = diff + sum
    */
    private int numWaysByDPII(int n, int k){
        if(n <= 0 || k <= 0){
            return 0;
        }

        if(n == 1){
            return k;
        }
        if(n == 2){
            return k + k*(k-1);
        }

        // sameDP[i] means the ith post has the same color with the (i-1)th post.
        int[] sameDP = new int[n];
        // diffDP[i] means the ith post has a different color with the (i-1)th post.
        int[] diffDP = new int[n];
        sameDP[0] = k;
        diffDP[0] = k;
        sameDP[1] = k;
        diffDP[1] = k * (k - 1);

        for(int i = 2; i < n; i++){
            // same[i] can only be diff[i-1]. It cannot be same[i-1] since if it is same[i-1],
            // then same[i-1] could be same[i-2].. which violates the rule (no more than two posts have same color).
            // So same[i] should just grab whatever diff[i-1] is to be the same as previous one,
            // cuz diff[i-1] is difference than diff[i-2],
            // in which case that diff[i] and diff[i-1] are the same color which is different than
            // what diff[i-2] color is.
            sameDP[i] = diffDP[i-1];

            // Because diff[i] should be different than diff[i-1] and same[i-1], we consider both cases;
            // if diff[i] want to be different from same[i-1], then diff[i] has same[i-1]*(k-1) possibilities,
            // if diff[i] want to be different from diff[i-1], then diff[i] has diff[i-1]*(k-1) possibilities,
            // in total is the sum of these two cases.
            diffDP[i] = (sameDP[i-1] + diffDP[i-1]) * (k-1);
        }

        return sameDP[n-1] + diffDP[n-1];

    }
    // WRONG idea.
    // intuition: this is similar with 256. Paint House
    // naive solution is use backtracking to get all combinations, it's exponential TC
    // use DP to reduce the TC
    // we have two constraints so we need a 2-d array dp[][]
    // let dp[i][j] mean the total ways we paint the fences if we paint the ith fence with jth color, so dp[i][j] has two condition: the ith fence has same color with i-1th, the ithfence not has same color with i-1th
    //  dp[i][j] = (dp[i-1][j]) + (dp[i-1][l]), where l != j
    // base case is
    // 1 one fence, so dp[0][j] = k
    private int numWaysByDP(int n, int k){
        if(n <= 0 || k <= 0){
            return 0;
        }

        int[][] dp = new int[n][k];
        for(int j = 0; j < k; j++){
            dp[0][j] = k;
        }

        // dp
        for(int i = 1; i < n; i++){
            for(int j = 0; j < k; j++){
                dp[i][j] += dp[i - 1][j];
                for(int l = 0; l < k; l++){
                    if(l == j){
                        continue;
                    }

                    dp[i][j] += dp[i - 1][l];
                }
            }
        }

        return dp[n - 1][k - 1];
    }
}
