package com.myleetcode.two_pointers.four_sum_ii;

import java.util.Arrays;

class Solution {
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        // 为什么在binary search分组啊
        // 同一类题目先采用同一类解法
        
        
        // special case
        if ( A == null || B == null || C == null || D == null){
            return 0;
        }
        int lenA = A.length;
        int lenB = B.length;
        int lenC = C.length;
        int lenD = D.length;
        if(lenA == 0 || lenB == 0 || lenC == 0 || lenD == 0){
            return 0;
        }
        
        // 求和A和B
        int[] sumAB = new int[lenA * lenB];
        int indexAB = 0;
        for(int i = 0; i < lenA; i++){
            for(int j = 0; j < lenA; j++){
                sumAB[indexAB] = A[i] + B[j];
                indexAB++;
            }
        }
        // 排序sumAB
        Arrays.sort(sumAB);
        
        
//         求和C和D并取反，因为如果sumAB和sumCD中的数字相加为0，那么取反后会相同，为后面排序比较做准备
        int[] sumCD = new int[lenC * lenD];
        int indexCD = 0;
        for(int i = 0; i < lenC; i++){
            for(int j = 0; j < lenD; j++){
                sumCD[indexCD] = - (C[i] + D[j]);
                 indexCD++;
            }
        }
        // 排序sumCD
        Arrays.sort(sumCD);
        
        // 接下来只要找到sumAB和sumCD中相同的数字，也就是变成了基础算法
        indexAB = 0;
        indexCD = 0;
        int count = 0;
        while(indexAB < sumAB.length && indexCD < sumCD.length){
            if(sumAB[indexAB] == sumCD[indexCD]){
                // 相同，计数, sumAB和sumCD中相同的数字，各自应该分别计数。然后各自的计数相乘，才是正确的：该相同的值，对应的A和B中的数字组合，和对应的C和D中的数字组合，能组合到一起的总个数
//                 比如相同的数字为4，sumAB中是A1和B3以及A2和B2；sumCD中是C0和C4以及C2和C2以及C1和C3。那么能形成的组合应该是countAB*countCD
                int countAB = 1;
                int countCD = 1;
                indexAB++;
                indexCD++;
                
                // 找重复.注意判断条件的顺序，要先短路重要的条件
                while( (indexAB < sumAB.length) && (sumAB[indexAB] == sumAB[indexAB - 1])){
                    countAB++;
                    indexAB++;
                }
                while( (indexCD < sumCD.length) && (sumCD[indexCD] == sumCD[indexCD - 1])){
                    countCD++;
                    indexCD++;
                }
                
                count = count + countAB * countCD;
                
            }else if(sumAB[indexAB] < sumCD[indexCD]){
                indexAB++;
            }else{
                indexCD++;
            }
        }
        
        return count;
    }
}