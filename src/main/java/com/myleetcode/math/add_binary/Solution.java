package com.myleetcode.math.add_binary;

class Solution {
    public String addBinary(String a, String b) {
        // special case
        if(a == null){
            return b;
        }
        if(b == null){
            return a;
        }

        return addBinaryByMath(a, b);
    }

    // traverse a and b reverse order
    // use a variable to keep carry, carry = carry + a.charAt(i) + b.charAt(j) - 2 until one of them reach 0.
    // then carry and the leaving string to calculate until the leaving string reach 0
    private String addBinaryByMath(String a, String b){
        // keep a is longer
        if(a.length() < b.length()){
            String temp = "";
            temp = a;
            a = b;
            b = temp;
        }

        int lenA = a.length();
        int lenB = b.length();


        int i = lenA - 1;
        int j = lenB - 1;
        int vA = 0;
        int vB = 0;

        int carry = 0;
        int sum = 0;
        int[] ret = new int[lenA + 1];
        int k = lenA;
        while(i >= 0){
            // get int value
            if(a.charAt(i) == '1'){
                vA = 1;
            }else{
                vA = 0;
            }
            i--;

            // j is shorter
            if(j >= 0){
                if(b.charAt(j) == '1'){
                    vB = 1;
                }else{
                    vB = 0;
                }
                j--;
            }else{
                vB = 0;
            }

            // most important part is how to caculate the sum and carry
            ret[k] = (carry + vA + vB ) % 2;
            k--;

            // carry
            carry = (vA + vB + carry) / 2;
        }


        // if still have carry, ret[0] should be 1
        int from = 1;
        if(carry == 1){
            ret[0] = 1;
            from = 0;
        }

        StringBuilder sb = new StringBuilder();
        for(int f = from; f < ret.length; f++){
            sb.append(ret[f]);
        }

        return sb.toString();

    }
}
