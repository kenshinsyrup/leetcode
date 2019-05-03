package com.myleetcode.backtracking.restore_ip_addresses;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> restoreIpAddresses(String s) {
        return restoreIPAddressesByBacktracking(s);
    }

    // TC: O(3^N)
    // SC: O(N)
    // intuition: Backtracking to solve. The valid IP should has every part length <= 3 and value in range [0:255]
    private List<String> restoreIPAddressesByBacktracking(String str){
        List<String> ret = new ArrayList<>();
        if(str == null || str.length() < 4){
            return ret;
        }

        backtracking(str, 4, 0, ret, "");

        return ret;
    }

    private void backtracking(String str, int stillNeed, int start, List<String> ret, String snippet){
        // reach the end of str, everything is ok, add to ret
        if(start >= str.length() && stillNeed == 0){
            ret.add(snippet);
            return;
        }
        if(stillNeed <= 0){
            return;
        }

        // explore
        // every part is less or equals to 3 digits, so if we want to cut at start+i, we should promise the start:start+i is valid, and the start+i:str.length() is valid, this is a prune to useless branch
        int stillNeedLen = (stillNeed - 1 ) * 3;
        for(int i = 1; i <= 3; i++){
            // !!! important, must check start + i <= str.length
            // the stillNeedLen is prune, without this, the program is still correct but TC and SC will increase
            if(start + i <= str.length() && str.length() - start - i <= stillNeedLen){
                String numStr = str.substring(start, start + i);
                // check the int must be valid, no leading 0 if numStr length > 1
                if(numStr.length() > 1 && numStr.charAt(0) == '0'){
                    continue;
                }
                if(Integer.valueOf(numStr) < 0 || Integer.valueOf(numStr) > 255){
                    break;
                }

                if(snippet == ""){
                    backtracking(str, stillNeed - 1, start + i, ret, numStr);
                }else{
                    backtracking(str, stillNeed - 1, start + i, ret, snippet + "." + numStr);
                }
            }
        }
    }
}
