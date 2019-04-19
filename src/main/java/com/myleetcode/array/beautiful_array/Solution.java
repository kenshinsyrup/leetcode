package com.myleetcode.array.beautiful_array;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public int[] beautifulArray(int N) {
        // special case
        if(N <= 0){
            return new int[0];
        }

        return beautifulArrayByOddPlusEvenPattern(N);
    }

    private int[] beautifulArrayByOddPlusEvenPattern(int N){
        //http://www.noteanddata.com/leetcode-932-Beautiful-Array-java-solution-note.html
        // https://leetcode.com/problems/beautiful-array/discuss/186679/

        List<Integer> ans = new ArrayList<Integer>();

        // base, a beautiful array
        ans.add(1);

        // construct a 2*N beautiful。
//         重要！！！这里必须是<不可以是<=,因为我们需要的是N个，那么我们只要在while退出时保证ans.size()>=N，所以这里进入循环的条件就是<N.如果写成<=N会死循环.
        while(ans.size() < N){
            List<Integer> temp = new ArrayList<Integer>();

            // for a beautiful array ans, its elements, v*2, should be also beautiful. After this, temp has all odds and beautiful
            for(int v: ans){
                if(2*v - 1 <= N){// 在寻找值时，我们要的是<=N的奇数和偶数，如果只找<N的会漏掉内容，导致找不齐>=N个元素，导致死循环.
                    temp.add(2*v - 1);
                }
            }

            // for every value in ans, since ans is beautiful, then 2*v is also beautiful. Before this, temp is a odds array and beautiful. After this, temp's first half is a odd beautiful array and last half is a even beautiful array. Because odds beautiful + even beautiful is still beautiful, so temp is beautiful.
            for(int v: ans){
                if(2*v <= N){
                    temp.add(2*v);
                }
            }

            // update ans list, and loop again. In the last loop, ans.size() < N, after two for loop,we will get: ans.size() >= N.
            ans = temp;
        }

        // get first N elements from a big beautiful array.
        int[] ret = new int[N];
        for(int i = 0; i < N; i++){
            ret[i] = ans.get(i);
        }

        return ret;
    }
}