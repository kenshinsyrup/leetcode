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
        int vA = 0; // value of char at location i of a
        int vB = 0; // value of char at location j of b

        int sum = 0; // (vA + vB + carry) % 2
        int carry = 0; //  (vA + vB + carry) / 2
        int[] sums = new int[lenA]; // store sum
        while(i >= 0){
            // get int value
            if(a.charAt(i) == '1'){
                vA = 1;
            }else{
                vA = 0;
            }

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
            sums[i] = (carry + vA + vB ) % 2;
            // carry
            carry = (vA + vB + carry) / 2;

            i--;

        }

        StringBuilder sb = new StringBuilder();
        // if still have carry, ret[0] should be 1
        if(carry == 1){
            sb.append(1);
        }

        for(int v: sums){
            sb.append(v);
        }

        return sb.toString();

    }
}