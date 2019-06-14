package com.myleetcode.bit_manipulate.gray_code;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> grayCode(int n) {
        return grayCodeByBitManipulate(n);
    }

    /*
when n=3, we can get the result based on n=2.
00,01,11,10 -> (000,001,011,010 ) (110,111,101,100).
They only differ at their highest bit, while the rest numbers of part two are exactly symmetric of part one.
    */
    // TC: O(N ^ 2)
    // SC: O(1) if not consider the ret List
    private List<Integer> grayCodeByBitManipulate(int n){
        List<Integer> ret=new ArrayList<Integer>();

        ret.add(0);

        for(int i = 0; i < n; i++){
            int size = ret.size();
            int mask = 1 << i;

            // besides the nums in ret, we need more which are add a bit 1 in every num that we have
            for(int k = size - 1; k >= 0; k--){
                ret.add(ret.get(k) | mask);
            }
        }

        return ret;
    }
}
