package com.myleetcode.string.integer_to_english_words;

class Solution {
    public String numberToWords(int num) {
        return numberToWordsByTraverse(num);
    }

    // intuition: Solid Programming
    // seperate num every 3 digits, the right most one is the base let's say its index is idx, then idx-1 is the tens and idx-2 is the hunderds
    // https://leetcode.com/problems/integer-to-english-words/discuss/70625/My-clean-Java-solution-very-easy-to-understand/72852
    final String[] LESS_THAN_20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    final String[] TENS = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    final String[] THOUSANDS = {"Billion", "Million", "Thousand", ""};
    final int[] RADIX = {1000000000, 1000000, 1000, 1}; // base billion, base million, base thousand, base 1

    // TC: O(N)
    // SC: O(N)
    private String numberToWordsByTraverse(int num){
        if(num == 0){
            return "Zero";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < RADIX.length; i++) {
            if (num / RADIX[i] == 0){
                continue;
            }

            sb.append(build(num / RADIX[i])).append(THOUSANDS[i]).append(' ');

            num %= RADIX[i];
        }

        return sb.toString().trim();
    }

    private String build(int num){
        if (num == 0) {
            return "";
        }

        if (num < 20) {
            return LESS_THAN_20[num] + " ";
        }

        if (num < 100) {
            return TENS[num / 10] + " " + build(num % 10);
        }

        return LESS_THAN_20[num / 100] + " Hundred " + build(num % 100);
    }

}
