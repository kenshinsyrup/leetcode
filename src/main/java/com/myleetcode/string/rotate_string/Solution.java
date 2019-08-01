package com.myleetcode.string.rotate_string;

class Solution {
    public boolean rotateString(String A, String B) {
        // return rotateStringBySplit(A, B); // 思路错误，wrong
        return rotateStringByKMP(A, B);
    }

    /*
    naive solution is:
    All rotations of A are contained in A+A. Thus, we can simply check whether B is a substring of A+A. so we could just do this to solve the problem:
    return A.length() == B.length() && (A + A).contains(B);

    but the TC is O(N^2) and SC is O(N).

    we could optimize this solution, because actually what we are doing is to search substring in another string, so the KMP Algo is a good fit
    */

    // https://leetcode.com/problems/rotate-string/discuss/330307/Java-or-KMP
    // what is KMP: https://www.geeksforgeeks.org/kmp-algorithm-for-pattern-searching/
    // what is LPS: https://www.youtube.com/watch?v=tWDUjkMv6Lc&lc=z12bxp2htrrgfzxdy225hrgbqm2sj3a5s04
    // KMP algo
    // TC: O(N)
    // SC: O(N)
    private boolean rotateStringByKMP(String A, String B){
        // special case 1: maybe null
        if(A == null && B == null){
            return true;
        }
        if(A == null || B == null){
            return false;
        }
        // special case 2: no null, maybe equal
        if(A.equals(B)){
            return true;
        }
        // special case 3: maybe length is not equal
        int lenA = A.length();
        int lenB = B.length();
        if(lenA != lenB){
            return false;
        }

        // use KMP to search B in str
        // 1 get lps array
        int[] lps = caculateLPS(B);

        // 2 search
        return searchByKMP(A + A, B, lps);
    }

    private boolean searchByKMP(String src, String tar, int[] lps){
        int lenSrc = src.length();
        int lenTar = tar.length();
        int i = 0;
        int j = 0;
        while(i < lenSrc && j < lenTar){
            if(src.charAt(i) == tar.charAt(j)){
                i++;
                j++;

                // find the whole tar in src
                if(j == lenTar){
                    return true;
                }
            }else{
                if(j == 0){
                    i++;
                }else{
                    j = lps[j - 1];
                }
            }
        }

        return false;
    }

    private int[] caculateLPS(String str){
        int lenPreLPS = 0; // 0
        int i = 1; // start with 1
        int len = str.length();
        int[] lps = new int[len]; // Longest proper Prefix and Suffix
        while(i < len){
            if(str.charAt(i) == str.charAt(lenPreLPS)){
                lps[i] = lenPreLPS + 1;
                i++;
                lenPreLPS++;
            }else{
                if(lenPreLPS == 0){
                    lps[i] = 0;
                    i++;
                }else{
                    lenPreLPS = lps[lenPreLPS - 1];
                }
            }
        }

        return lps;
    }



    // 思路错误
    // intuition:
    // find A's the 0th char's idx in B, then the idx should be a indicator, that B[idx: len-1] equals to A[0:len-1-idx+1-1], and B[0:idx-1] equals to A[len-1-idx+1-1+1:len-1]
    // TC: O(N)
    // SC: O(1)
    private boolean rotateStringBySplit(String A, String B){
        if(A == null && B == null){
            return true;
        }
        if(A == null || B == null){
            return false;
        }

        int lenA = A.length();
        int lenB = B.length();
        if(lenA != lenB){
            return false;
        }

        int idx = 0;
        while(idx < lenB){
            if(A.charAt(0) == B.charAt(idx)){
                break;
            }

            idx++;
        }

        if(A.substring(0, lenB - 1 - idx + 1).equals(B.substring(idx, lenB)) && A.substring(lenB - 1 - idx + 1, lenA).equals(B.substring(0, idx))){
            return true;
        }
        return false;
    }
}
