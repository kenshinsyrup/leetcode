package com.myleetcode.math.fraction_to_recurring_decimal;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public String fractionToDecimal(int numerator, int denominator) {
        return fractionToDecimalByDivide(numerator, denominator);
    }

    // https://leetcode.com/problems/fraction-to-recurring-decimal/discuss/51106/My-clean-Java-solution
    // The time com should be O (denominator), cause the mod must be less than denominator. so you can run from 1 to mod times
    private String fractionToDecimalByDivide(int num, int den){
        if(num == 0){
            return "0";
        }

        StringBuilder sb = new StringBuilder();

        // 1: if one of numerator and denominator is negative, append a leading -
        if(!((num < 0 && den < 0) || (num > 0 && den > 0))){
            sb.append("-");
        }

        // 2: transform to positive
        long numerator = Math.abs((long)num);
        long denominator = Math.abs((long)den);

        // 3: get the integral part
        sb.append(numerator / denominator);

        // if devisible, return result
        long remainder = numerator % denominator;
        if(remainder == 0){
            return sb.toString();
        }

        // 4: otherwise, caculate the fractional part
        sb.append(".");
        // use the Map to store the remainders and its first occurion pos. if we find repeated remainders, means the quotient nums repeat too. then we need to add ( at the first occurion pos and add a ) at the last of sb
        Map<Long, Integer> remainderPosMap = new HashMap<>();
        remainderPosMap.put(remainder, sb.length());

        while(remainder != 0){
            // for eg, 2/3, the remainder is 2, then after we get the 0. as integral part, we want the fractional part, in division, we use 20/3 to get 6 which is part of the quotient, and we get the new remainder 2
            long tempNumerator = remainder * 10;
            sb.append(tempNumerator / denominator);
            remainder = tempNumerator % denominator;

            // check, if the remainder already in the Set, then the last append tempNumerator/denominator is the repeating num, we insert a ( before it and append a ) and break
            if(remainderPosMap.containsKey(remainder)){
                int pos = remainderPosMap.get(remainder);
                sb.insert(pos, "(");
                sb.append(")");
                break;
            }else{
                // otherwise put the remainder into Set
                remainderPosMap.put(remainder, sb.length());
            }
        }

        return sb.toString();

    }
}
