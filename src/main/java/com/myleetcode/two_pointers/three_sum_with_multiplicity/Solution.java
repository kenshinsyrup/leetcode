package com.myleetcode.two_pointers.three_sum_with_multiplicity;

import java.util.Arrays;

class Solution {
    public int threeSumMulti(int[] A, int target) {
        return threeSumMultiByTraverse(A, target);
    }

    // TC: O(N^2)
    // SC: O(1)
    // intuition: i think this is similar as 3Sum closest, we just need change the part of start and end pointer moving.
    // this problem is not good... the biggest chanlenge is the curRet variable, we need this long type variable help us to record the intermediate result and at last to add this to ret and module the mod variable and cast to int.
    // this module requirement also is very annoying
    // but the general thought is just like 3Sum
    // https://leetcode.com/problems/3sum-with-multiplicity/discuss/181080/Java-sorting-solution-O(N2)
    private int threeSumMultiByTraverse(int[] A, int target){
        if(A == null || A.length == 0){
            return 0;
        }

        int mod = 1000000007;

        Arrays.sort(A);

        if(3 * A[0] > target || 3 * A[A.length - 1] < target){
            return 0;
        }

        int ret = 0;
        for(int i = 0; i < A.length; i++){
            // early terminate
            if(3 * A[i] > target){
                return ret;
            }

            long curRet = 0;

            int start = i + 1;
            int end = A.length - 1;
            while(start < end){
                int curSum = A[i] + A[start] + A[end];

                // find, keep move and find
                if(curSum == target){
                    int countStart = 1;
                    int countEnd = 1;

                    // check if start and end is the same
                    // if not same, we count the # that same as start, count the # that same as end
                    if(A[start] != A[end]){
                        while(start + 1 < end && A[start] == A[start + 1]){
                            start++;

                            countStart++;
                        }
                        while(end - 1 > start && A[end - 1] == A[end]){
                            end--;

                            countEnd++;
                        }

                        // combination number is the countStart * countEnd
                        curRet += (countStart * countEnd) % mod;

                        // move start and end to next
                        start++;
                        end--;
                    }else{
                        // if same
                        int count = end - start + 1;
                        curRet += (count * (count - 1) / 2) % mod;

                        // here we break because we have process the start to end all
                        break;
                    }
                }else if(curSum < target){
                    start++;
                }else{
                    end--;
                }
            }

            ret = (int) (ret + curRet) % mod;
        }

        return ret;
    }
}
