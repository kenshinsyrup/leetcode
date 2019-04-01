package com.myleetcode.string.implement_str_str;

class Solution {
    public int strStr(String haystack, String needle) {
        return strStrByTwoPointers(haystack, needle);
    }

    // TC: O(M*N), M is the length of needle and N is the length of haystack
    // SC: O(1)
    // intuition: two pointers fot two string, secondP initially at the 0 index of needle, firstP traverse the haystack, if firstP == secondP, keep a record of firstP called temp, then move together to next until secondP is the end of needle(success) or firstP != secondP(fail). if fail, secondP reset to 0 index of needle and firstP to tem+1
    private int strStrByTwoPointers(String haystack, String needle){
        // special case
        if(haystack == null || needle == null){
            return -1;
        }
        if(needle.length() == 0){ // needle is ""
            return 0;
        }

        int lenH = haystack.length();
        int lenN = needle.length();
        int haystackP = 0;
        int needleP = 0;

        for(int i = 0; i < lenH; i ++){
            // reset haystackP and eedleP
            haystackP = i;
            needleP = 0;

            // check
            while(haystack.charAt(haystackP) == needle.charAt(needleP)){
                haystackP++;
                needleP++;

                // needleP reaches end of needle
                if(needleP >= lenN){
                    return i;
                }
                // haystackP reaches end of haystack
                if(haystackP >= lenH){
                    return -1;
                }
            }
        }

        return -1;

    }
}
