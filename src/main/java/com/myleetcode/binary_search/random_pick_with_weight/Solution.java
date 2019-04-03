package com.myleetcode.binary_search.random_pick_with_weight;

import java.util.Random;

class Solution {

    // stupid problem, dont understand what it's saying
    // 题意描述：https://leetcode.com/problems/random-pick-with-weight/discuss/154044/Java-accumulated-freq-sum-and-binary-search
    // 有一个w数组，其中的value表达的是index的weight，比如w[4,5,6]，意思是index 0的weight是4，index 1的weight是5，index 2的weight是6
    // pickIndex方法希望能根据index的weight，来随机的返回index

    // 那么我们根据w数组，比如w为[2,1,3],说明index 0的weight是2，index 1的weight是2，index的weight是3，制作一个value是w中的index的数组就是[0,0,1,2,2,2]。这样就明白一点来，这过新数组我们称之为A,那么pickIndex就是在A中随机选一个值返回，那么怎么随机选一个值呢，可以生成一个范围在0:A.length-1之间的随机数，这个就是我们要返回的A中的值Av的index(成为Ai)，然后返回A[Ai]就可以了.
    // 但是可以优化，我们知道A数组中Av很多值是相同的，其数量就是w[Av]。我们不需要A数组，只需要把所有的weight用前项和的方式加起来，这个wSum的最后一个值就是我们的A数组的长度，我们可以根据这个长度来生成随机的一个idx，这个idx就是我们要pick的，那么这个idx要pick的index是什么呢，就看idx落在那个wSum的区间就好了.
    /*
Use accumulated freq array to get idx.
w[] = {2,5,3,4} => wsum[] = {2,7,10,14}
then get random val random.nextInt(14)+1, idx is in range [1,14]

idx in [1,2] return 0(index in w)
idx in [3,7] return 1(inedex in w)
idx in [8,10] return 2(inedex in w)
idx in [11,14] return 3(inedex in w)

if helps, the not exists A looks like: [0,0,1,1,1,1,1,2,2,2,3,3,3,3]
    */
    // then become LeetCode 35. Search Insert Position

    Random random;
    int[] weightPrefixSum;
    // init TC: O(N), N is the length of w
    // init SC: O(N)
    public Solution(int[] w) {
        // get a Random instance
        random = new Random();

        // fulfill weightPrefixSum array
        int len = w.length;
        weightPrefixSum = new int[len];
        weightPrefixSum[0] = w[0];
        for(int i = 1; i < w.length; i++){
            weightPrefixSum[i] = weightPrefixSum[i-1] + w[i];
        }
    }

    // TC: O(log(N)), N is the length of w
    // SC: O(1)
    public int pickIndex() {
        int len = weightPrefixSum.length;

        int idx = random.nextInt(weightPrefixSum[len - 1]) + 1;

        int start = 0;
        int end = len - 1;
        while(start <= end){
            int mid = start + (end - start) / 2;

            if(weightPrefixSum[mid] == idx){
                return mid;
            }else if(weightPrefixSum[mid] < idx){
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }

        return start;

    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(w);
 * int param_1 = obj.pickIndex();
 */
