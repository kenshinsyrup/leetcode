package com.myleetcode.math.add_to_array_form_of_integer;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> addToArrayForm(int[] A, int K) {
        // return addToArrayFormByMathAndArray(A, K);
        return addToArrayFormByMathAndArrayII(A, K);
    }

    // 实际上，A变成List是没有必要的. (K变成List也是没有必要的, 但是为了考虑K合法，A不合法的情况下，必须返回K的List的形式，我们还是要先把K变成List)
    private List<Integer> addToArrayFormByMathAndArrayII(int[] A, int K){
        List<Integer> ret = new ArrayList<>();
        if(K < 0){
            return ret;
        }

        // arraylist form K
        List<Integer> KList = new ArrayList<>();
        while(K > 0){
            KList.add(0, K % 10);
            K = K / 10;
        }

        // special case
        if(A == null || A.length == 0){
            return KList;
        }

        int sum = 0; // 每个位置的和
        int carry = 0; // 每个位置的进位
        int i = A.length - 1;
        int j = KList.size() - 1;
        int vA = 0; // 每个位置A的值
        int vK = 0;// 每个位置K的值
        while(i >= 0 || j >= 0){
            if(i < 0){
                vA = 0;
            }else{
                vA = A[i];
            }

            if(j < 0){
                vK = 0;
            }else{
                vK = KList.get(j);
            }

            sum = (vA + vK + carry) % 10;
            carry = (vA + vK + carry) / 10;

            // collect
            ret.add(0, sum);

            // move index
            i--;
            j--;
        }

        if(carry != 0){
            ret.add(0, carry);
        }

        return ret;

    }

    // intuition: make a array B which represents the array-form of K, then we get a very similar problem as 67. Add Binary
    private List<Integer> addToArrayFormByMathAndArray(int[] A, int K){

        List<Integer> ret = new ArrayList<>();
        if(K < 0){
            return ret;
        }

        // arraylist form K
        List<Integer> KList = new ArrayList<>();
        while(K > 0){
            KList.add(0, K % 10);
            K = K / 10;
        }

        // special case
        if(A == null || A.length == 0){
            return KList;
        }

        // convert array A to AList
        // List<Integer> AList = Arrays.asList(A); // stupid Java
        List<Integer> AList = new ArrayList<>();
        for(int v: A){
            AList.add(v);
        }

        int sum = 0; // 每个位置的和
        int carry = 0; // 每个位置的进位
        int i = AList.size() - 1;
        int j = KList.size() - 1;
        int vA = 0; // 每个位置A的值
        int vK = 0;// 每个位置K的值
        while(i >= 0 || j >= 0){
            if(i < 0){
                vA = 0;
            }else{
                vA = AList.get(i);
            }

            if(j < 0){
                vK = 0;
            }else{
                vK = KList.get(j);
            }

            sum = (vA + vK + carry) % 10;
            carry = (vA + vK + carry) / 10;

            // collect
            ret.add(0, sum);

            // move index
            i--;
            j--;
        }

        if(carry != 0){
            ret.add(0, carry);
        }

        return ret;

    }
}
