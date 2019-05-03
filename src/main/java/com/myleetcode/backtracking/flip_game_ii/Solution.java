package com.myleetcode.backtracking.flip_game_ii;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean canWin(String s) {
        // return canWinByBacktracking(s);
        return canWinByBacktrackingWithMap(s);
    }

    // 这个题的重点是如何确定我方guarantee win, 我们可以直到任何一方何时会输，对于一个给定的字符串，如果不能再继续flip，那么当前一方就输了，如果当前一方输了，那么另一方一定赢了。所以这个题目是找输方：对于一个给定str，如果对方不能再flip，也就是对方输了，那么我就赢了。

    // use Map to memo and reduce the TC:https://leetcode.com/problems/flip-game-ii/discuss/73971/Java-backtracking-solution-with-time-optimization-through-DP(205ms-greater-19ms)
    private boolean canWinByBacktrackingWithMap(String str){
        if(str == null || str.length() == 0){
            return false;
        }

        Map<String, Boolean> strWinMap = new HashMap<>();

        return backtrackingWithMap(str, strWinMap);
    }

    private boolean backtrackingWithMap(String str, Map<String, Boolean> strWinMap){
        if(strWinMap.containsKey(str)){
            return strWinMap.get(str);
        }

        for(int i = 0; i < str.length() - 1; i++){
            if(str.startsWith("++", i)){
                String nextStr = str.substring(0, i) + "--" + str.substring(i+2, str.length());

                // if opponent could not win, we win
                if(!backtracking(nextStr)){
                    strWinMap.put(str, true);

                    return true;
                }
            }
        }

        strWinMap.put(str, false);

        return false;

    }



    /*
The idea is try to replace every "++" in the current string s to "--" and see if the opponent can win or not, if the opponent cannot win, great, we win!

For the time complexity, here is what I thought, let's say the length of the input string s is n, there are at most n - 1 ways to replace "++" to "--" (imagine s is all "+++..."), once we replace one "++", there are at most (n - 2) - 1 ways to do the replacement, it's a little bit like solving the N-Queens problem, the time complexity is (n - 1) x (n - 3) x (n - 5) x ..., so it's O(n!!), double factorial.
    */
    // https://leetcode.com/problems/flip-game-ii/discuss/73962/Share-my-Java-backtracking-solution
    // intuition: use Backtracking to get all possible combinations that no one could flip, then if we want the starting person win, we should find if there's a odd number "--" combination
    private boolean canWinByBacktracking(String str){
        if(str == null || str.length() == 0){
            return false;
        }

        return backtracking(str);
    }

    private boolean backtracking(String str){
        for(int i = 0; i < str.length() - 1; i++){
            if(str.startsWith("++", i)){
                String nextStr = str.substring(0, i) + "--" + str.substring(i+2, str.length());

                // if opponent could not win, we win
                if(!backtracking(nextStr)){
                    return true;
                }
            }
        }

        return false;

    }
}
