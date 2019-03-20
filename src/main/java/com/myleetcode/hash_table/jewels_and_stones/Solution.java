package com.myleetcode.hash_table.jewels_and_stones;

import java.util.HashSet;
import java.util.Set;

class Solution {
    public int numJewelsInStones(String J, String S) {
        // special case
        if(J == null || S == null || J.length() == 0 || S.length() == 0){
            return 0;
        }

        return numJewelsInStonesByHashSet(J, S);
    }

    // intuition: nested loop, O(n^2)
    // optimization: use a hashset store letters of J, then O(n) traverse S to count num
    private int numJewelsInStonesByHashSet(String J, String S){
        Set<Character> store = new HashSet<>();
        for(char c: J.toCharArray()){
            store.add(c);
        }

        int count = 0;
        for(int i = 0; i < S.length(); i++){
            if(store.contains(S.charAt(i))){
                count++;
            }
        }

        return count;
    }
}
