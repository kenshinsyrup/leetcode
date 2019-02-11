package com.myleetcode.array.squares_of_a_sorted_array;

class Solution {
    public int[] sortedSquares(int[] A) {
        // what means "non-decreasing"
        
        // special case
        if(A == null){
            return null;
        }
        
        // sort法
        // return sortedSqqaresBySort(A);
        
        // two pointers
        return sortedSquaresByTwoPointers(A);
    }
    
    // two pointers法,一次遍历来完成可以将时间复杂度降低为O(n).
    // 原理是，原始数组是升序，假设数组中存在负数，那么一定存在最后一个负数或者第一个非负数比如index是k，那么从k到n是升序的，从0到k - 1是降序的。这样我们用两个指针一个i从k - 1开始向0移动，一个j从k向n移动。在移动过程中来检查那个index对应的数字的square小，就先放入ans数组。最后要记得检查两个指针是否都移动到最后，没有的，应该继续移动并square并append到ans。
    private int[] sortedSquaresByTwoPointers(int[] A){
        int len = A.length;
        int[] ans = new int[len];
        
        int k = 0;
        while(k < len && A[k] < 0){
            k++; // 注意，这里循环结束的时候，k指向的是第一个非负数
        }
        
        // 0到k - 1小于0，k到n 大于等于0
        int i = k - 1;
        int j = k;
        int l = 0;
        int squareI = 0;
        int squareJ = 0;
        while(i >= 0 && j < len){
            squareI = A[i] * A[i];
            squareJ = A[j] * A[j];
            if(squareI < squareJ){
                ans[l] = squareI;
                i--;
            }else{
                ans[l] = squareJ;
                j++;
            }
            l++;
        }
        
        while(i >= 0){
            ans[l] = A[i] * A[i];
            i--;// 不要忘记i继续移动
            l++;
        }
        
        while(j < len){
            ans[l] = A[j] * A[j];
            j++;//不要忘记j继续移动
            l++;
        }
        
        return ans;
        
    }
    
    // 最基本的想法，遍历数组求square，然后排序结果数组，再返回
    // TC: 遍历是n；排序是nlogn。总体是 O(nlogn)
    // SC: 遍历过程中，没有递归，没有额外空间，O(1)；排序是快速排序保证时间是nlogn，所以空间是logn，最差是n。leetcode说是O(n)??? 我怎么感觉是O(logn)啊
    private int[] sortedSqqaresBySort(int[] A){
        for(int i = 0; i < A.length; i++){
            A[i] = A[i] * A[i];
        }
        
        Arrays.sort(A);
        
        return A;
    }
}