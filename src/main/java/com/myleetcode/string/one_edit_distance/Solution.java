package com.myleetcode.string.one_edit_distance;

class Solution {
    public boolean isOneEditDistance(String s, String t) {
        // return isOneEditDistanceByScan(s, t);
        return isOneEditDistanceByScanII(s, t); // compact
    }

    // https://leetcode.com/problems/one-edit-distance/discuss/50098/My-CLEAR-JAVA-solution-with-explanation/229581
    // make code more compact and clear
    private boolean isOneEditDistanceByScanII(String str, String tar){
        int strLen = str.length();
        int tarLen = tar.length();

        // always make the tar be the shorter one
        if (tarLen > strLen) {
            return isOneEditDistance(tar, str);
        }

        for (int i = 0; i < tarLen; i++) {
            if (str.charAt(i) != tar.charAt(i)) {
                // if len is equal, we should only has one different char, so if we find a different char, then the rest subtring must be the same
                if (strLen == tarLen) {
                    return str.substring(i + 1).equals(tar.substring(i + 1));
                }

                // if len is not equal, then the shorter one's rest including cur char should be the same with the rest of the longer one
                return tar.substring(i).equals(str.substring(i + 1));
            }
        }

        // should not have more than 1 distance with length
        return tarLen + 1 == strLen;
    }

    // TC: O(N)
    // SC: O(1)
    // check
    private boolean isOneEditDistanceByScan(String str, String tar){
        if(str == null || tar == null || str.equals(tar)){
            return false;
        }

        int strLen = str.length();
        int tarLen = tar.length();

        // if len equal
        if(strLen == tarLen){
            boolean hasDiff = false;
            for(int i = 0; i < strLen; i++){
                if(str.charAt(i) == tar.charAt(i)){
                    continue;
                }

                // if cur char are not equal
                // if already has different char, false
                if(hasDiff){
                    return false;
                }else{
                    // otherwise set hasDiff to true
                    hasDiff = true;
                }
            }

            return true;
        }else if(Math.abs(strLen - tarLen) == 1){
            // difference is one
            int i = 0;
            int j = 0;
            boolean hasDiff = false;
            while(i < strLen && j < tarLen){
                if(str.charAt(i) == tar.charAt(j)){
                    i++;
                    j++;
                    continue;
                }

                if(hasDiff){
                    return false;
                }

                hasDiff = true;
                if(strLen < tarLen){
                    j++;
                }else{
                    i++;
                }
            }

            return true;
        }

        // otherwise, difference larger than 1, false
        return false;
    }
}
