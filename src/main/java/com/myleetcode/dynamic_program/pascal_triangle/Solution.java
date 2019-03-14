package com.myleetcode.dynamic_program.pascal_triangle;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> generate(int numRows) {
        // 1
        // 1 1
        // 1 2 1
        // 1 3 3 1
        // 1 4 6 4 1
        // f(i,j) = f(i - 1, j - 1) + f(i - 1, j), if i > 0, j > 0
        // base i == 0, return [[1]]; i == 1, return [[1], [1,1]]

        return generatePascalTriangleByDP(numRows);
    }

    // dp
    // 注意，题目是numRows是1-based的，所以我们要生成的一共是numRows行，也就是最终数组的长度.所以numRows是0的时候不是返回[[1]]而是[]
    private List<List<Integer>> generatePascalTriangleByDP(int numRows){
        List<List<Integer>> ret = new ArrayList<>();

        // special case
        if(numRows <= 0){
            return ret;
        }

        // base case1, numRows == 1
        List<Integer> tempList = new ArrayList<>();
        tempList.add(1);
        ret.add(tempList); // must write out of  if(numRows == 1){}, we should use this to caculate others.
        if(numRows == 1){
            return ret;
        }

        // base case2, numRows == 2; no need, we just need i > 0
        // tempList = new ArrayList<>();
        // tempList.add(1);
        // tempList.add(1);
        // ret.add(tempList); // must write out of  if(numRows == 1){}, we should use this to caculate others.
        // if(numRows == 2){
        //     return ret;
        // }

        List<Integer> preList;
        for(int i = 1; i < numRows; i++){
            tempList = new ArrayList<>();

            // The first row element is always 1.
            tempList.add(1);

            preList = ret.get(i - 1);
            for(int j = 1; j < preList.size(); j++){
                tempList.add(preList.get(j-1) + preList.get(j));
            }

            // The last row element is always 1.
            tempList.add(1);

            ret.add(tempList);
        }

        return ret;
    }
}
