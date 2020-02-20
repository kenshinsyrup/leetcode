package com.myleetcode.bad_problems.find_the_closest_palindrome;

public class Solution {
    public String nearestPalindromic(String n) {
        return nearestPalindromicByMath(n);
    }

    /*
    Math
    Bad Problem.
    https://leetcode.com/problems/find-the-closest-palindrome/discuss/102389/Java-solution-with-detailed-proof

    */
    private String nearestPalindromicByMath(String num) {
        // First build a palindrom based on current num.
        char[] arr = num.toCharArray();
        for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
            arr[j] = arr[i];
        }
        String curP = String.valueOf(arr);

        // Get smaller nearest palindrom and larger nearest palindorm.
        String preP = nearestPalindromOfAPalindrom(curP, false);
        String nextP = nearestPalindromOfAPalindrom(curP, true);

        // Compare distance.
        long numL = Long.valueOf(num);
        long cur = Long.valueOf(curP);
        long pre = Long.valueOf(preP);
        long next = Long.valueOf(nextP);
        long d1 = Math.abs(numL - pre);
        long d2 = Math.abs(numL - cur);
        long d3 = Math.abs(numL - next);
        if (numL == cur) {
            return d1 <= d3 ? preP : nextP;
        } else if (numL > cur) {
            return d2 <= d3 ? curP : nextP;
        } else {
            return d1 <= d2 ? preP : curP;
        }
    }

    private String nearestPalindromOfAPalindrom(String curPalin, boolean inc) {
        int len = curPalin.length();
        int k = len / 2;
        int p = len - k;
        /*
        For example, if curPlin is 1221, the palindrom root is 12, the rootPart is 12; if curPalin is 121, the palindrom root is 12, the rootPart is 12.
        */
        int rootPart = Integer.valueOf(curPalin.substring(0, p));
        // inc true means we try to get the larger nearest, otherewise the smaller nearest
        if (inc) {
            rootPart += 1;
        } else {
            rootPart -= 1;
        }

        if (rootPart == 0) {
            return k == 0 ? "0" : "9";
        }

        StringBuilder leftPartSB = new StringBuilder(String.valueOf(rootPart));

        StringBuilder rightPartSB = new StringBuilder(leftPartSB).reverse();
        // Means rootPart after the inc modification becomes number has less digits. Like 10, after -1, becomes 9.
        if (k > leftPartSB.length()) {
            rightPartSB.append("9");
        }

        return leftPartSB.append(rightPartSB.substring(rightPartSB.length() - k)).toString();
    }


}
