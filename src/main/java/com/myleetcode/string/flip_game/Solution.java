package com.myleetcode.string.flip_game;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> generatePossibleNextMoves(String s) {
        return generatePossibleNextMovesByTraverse(s);
    }

    // intuition: this seems a part of other problem, this only need us output all the possible states after one move
    private List<String> generatePossibleNextMovesByTraverse(String str){
        List<String> ret = new ArrayList<>();

        if(str == null || str.length() <= 1){
            return ret;
        }

        int len = str.length();
        for(int i = 0; i < len - 1; i++){
            if(str.substring(i,i+2).equals("++")){
                if(i == len - 2){
                    ret.add(str.substring(0, i) + "--");
                }else{
                    ret.add(str.substring(0, i) + "--" + str.substring(i+2, len));
                }
            }
        }

        return ret;
    }
}
