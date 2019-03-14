package com.myleetcode.dynamic_program.pascal_triangle_ii;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> getRow(int rowIndex) {
        return getRowByDP(rowIndex);
        // return getRowByDPOpt(rowIndex);
    }

    // follow up: 要求只能使用O(k)空间，所以不能按照118的解法，保存整个Pascal Triangle。应该只保存一行。
    // f(i, j) = f(i-1, j) + f(i - 1, j - 1),if i > 0, j >= 0.可以看出当前行的结果只和上一行有关，所以是可以优化为一行的
    // https://leetcode.com/problems/pascals-triangle-ii/discuss/38420/Here-is-my-brief-O(k)-solution
    private List<Integer> getRowByDPOpt(int rowIndex){
        List<Integer> rowList = new ArrayList<>();

        if(rowIndex < 0){
            return rowList;
        }

        rowList.add(1);
        if(rowIndex == 0){
            return rowList;
        }

        int jValuePre = rowList.get(0); // keep the value of f(i-1, j -1). and we know the value at j, then we have f(i-1,j-1) and f(i-1,j), and we store the sum to the postion (i,j)
        for(int i = 1; i < rowIndex + 1; i++){
            // use res as the source, ie the row above row at rowIndex, and also use res as the destination, because every time we update the value at j, we need value at j-1 and j, and we update this value dont affect the value after it. ONLY need keep in mind is to keep the value at j and then update it.
            for(int j = 1; j < rowList.size(); j++){
                int jValue = rowList.get(j);
                rowList.set(j, jValuePre + jValue);

                jValuePre = jValue;
            }

            rowList.add(1);
        }

        return rowList;
    }

    // same as the 118. Pascal's Triangle, only be carefull about the index
    private List<Integer> getRowByDP(int rowIndex){

        List<List<Integer>> pascalList = new ArrayList<>();
        List<Integer> rowList = new ArrayList<>();

        // special case
        if(rowIndex < 0){
            return rowList;
        }

        // base case1
        rowList.add(1);
        if(rowIndex == 0){
            return rowList;
        }
        pascalList.add(rowList);

        List<Integer> preList = new ArrayList<>();
        for(int i = 1; i < rowIndex + 1; i++){
            rowList = new ArrayList<>();

            rowList.add(1);

            preList = pascalList.get(i - 1);
            for(int j = 1; j < preList.size(); j++){
                rowList.add(preList.get(j - 1) + preList.get(j));
            }

            rowList.add(1);

            pascalList.add(rowList);
        }

        return pascalList.get(rowIndex);
    }
}
