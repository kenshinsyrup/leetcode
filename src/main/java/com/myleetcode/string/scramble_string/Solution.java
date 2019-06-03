package com.myleetcode.string.scramble_string;

class Solution {
    public boolean isScramble(String s1, String s2) {
        return isScrambleByRecursive(s1, s2);
    }

    // this problem has a very misleading eg: looks like we should split at the mid of the string everytime, but is not.
    // solution: https://leetcode.com/problems/scramble-string/discuss/285215/java-2ms-recursive-solution-with-explanation
    private boolean isScrambleByRecursive(String str1, String str2){
        if(str1 == null && str2 == null){
            return true;
        }
        if(str1 == null || str2 == null){
            return false;
        }

        return verify(str1, str2);
    }

    private boolean verify(String str1, String str2){
        // 1: if equal, true
        if(str1.equals(str2)){
            return true;
        }

        int len1 = str1.length();
        int len2 = str2.length();
        // 2: if the prerequisites to be scramble string are not satisfied, then false
        // 2.1: length not equal
        if(len1 != len2){
            return false;
        }
        // 2.2: has different chars
        // check if the str1 coulb be scrambled to str2: we know, if str1 and str2 has any chars with different num, then must could not, so here we check this to avoid too deep recursion, just like the length check above.
        // note: the test only has lower-case letters hhha, actually here should talk with interviewer and maybe use int[256] to represent all ASCII
        int[] str1CountArr = new int[26];
        int[] str2CountArr = new int[26];
        for(int i = 0; i < len1; i++) {
            str1CountArr[str1.charAt(i) - 'a']++;
            str2CountArr[str2.charAt(i) - 'a']++;
        }
        for(int i = 0; i < 26; i++){
            if( str1CountArr[i] != str2CountArr[i] ){
                return false;
            }
        }

        // 3: not equals, but has same length, try split
        // !!! keep in mind, i is the pos of should be in range [1, len1-1], if i is 0, will cause dead loop TLE.
        for(int i = 1; i < len1; i++){
            // try split at every possible pos, once catch one valid pos, true.
            // 3.1: left with left, right with right
            // s1[0,i) can scramble into s2[0,i) and s1[i,length) can scramble into s2[i, length)
            if(verify(str1.substring(0, i), str2.substring(0, i)) && verify(str1.substring(i, len1), str2.substring(i, len1))){
                return true;
            }
            // 3.2: !!! left with right, right with left.
            // s1[0,i) can scramble into s2[length - i, length) and s1[i,length) can scramble into s2[0, length - i)
            if(verify(str1.substring(0, i), str2.substring(len1 - i, len1)) && verify(str1.substring(i, len1), str2.substring(0, len1 - i))){
                return true;
            }
        }

        return false;
    }

}
