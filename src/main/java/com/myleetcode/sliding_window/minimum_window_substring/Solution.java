package com.myleetcode.sliding_window.minimum_window_substring;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public String minWindow(String s, String t) {
        return minWindowBySlidingWindow(s, t);
    }

    // TC: O(2N) => O(N) where N is the length of s. because 'right' never gets set back to 0 or 'i' or anything. It increments exactly once every time the inner loop executes. So the code in the inner loop can only execute 'n' times TOTAL, not 'n' times per value of 'i'.
    // SC: O(S + T)
    // https://leetcode.com/problems/minimum-window-substring/discuss/26810/Java-solution.-using-two-pointers-%2B-HashMap
    // intuition: sliding window, two pointers to represent a window [leftP: rightP]. at start, leftP and rightP from 0, rightP move to end of S one by one char to find the needed char. if find all chars in T, we start shrink the window, ie, we move leftP to rightP one by one char, if the windows contains all chars in T, we update the minWindow.
    // the thought is intuitive, but the most important and hard part is to "check if current windows contains all chars in T". Here we could use a Map, at first, key is the char at T and value is the number of this char in T. And we have a variable count to record userfull chars we met.
    // Then every time our rightP find one char belonging to T, we reduce its value, if the value is greater or equals to 0 we do count++ because this char is useful, is less than 0 means we have found more than needed then we dont do count++ because this char is not useful. so when count == T.length we know we have found enought chars in current window, we could start shrink.
    // at this time, we know the value in map are all <= 0 because we have find all chars belonging to T.
    // when shrink, our while condition is count == T.length, if we find one char in T, we increase its value in map, this means we put back char from window to map, so if its value is <= 0 means our window is still good; but if its value > 0 means we just put a useful char from window back to map, so we need do count--; if count != T.length, means our window is not good, we need expand window again, ie, move the rightP again.
    private String minWindowBySlidingWindow(String s, String t){
        if(s == null || t == null || s.length() == 0 || t.length() == 0){
            return "";
        }

        // map, keep numbers of chars in t
        Map<Character, Integer> charNumMap = new HashMap<>();
        char[] tCharArray = t.toCharArray();
        for(int i = 0; i < tCharArray.length; i++){
            charNumMap.put(tCharArray[i], charNumMap.getOrDefault(tCharArray[i], 0) + 1);
        }

        int leftP = 0;
        // int rightP = 0;
        int count = 0;
        int minWindow = Integer.MAX_VALUE;
        int minWindowLeft = 0;
        char[] sCharArray = s.toCharArray();
        for(int i = 0; i < sCharArray.length; i++){// i is rightP
            // only care about char at rightP is in map
            if(charNumMap.containsKey(sCharArray[i])){
                // !!!当window扩张时，我们先检查当前map中这个char的数量，如果大于0，说明是当前包含到了一个有效char，所以count++
                int charNum = charNumMap.get(sCharArray[i]);
                if(charNum > 0){// this char is useful
                    count++;
                }
                // 无论这个char是否有效，将map中的记录-1表示我们还需要多少个这个char
                charNumMap.put(sCharArray[i], charNum - 1);

                // check window, because our count are useful chars, so if count == t.length, then window is good, if good, shrink
                // shink means we put back char from window to map, of course we only care those chars existing in map. and every shrink, we put a char from window to map, if its value in map is greater than 0, means currently char is a useful char we should let our count--
                while(count == t.length() && leftP <= i){
                    // we caculate new min
                    if(minWindow > i - leftP + 1){
                        minWindow = i - leftP + 1;
                        minWindowLeft = leftP;
                    }

                    // only care about char at leftP is in map
                    if(charNumMap.containsKey(sCharArray[leftP])){
                        // 之所以在扩展window时先操作count再操作map；在缩小window时先操作map再操作count，是因为我们检查的是map中的char的number，所以要以"正确的"map为基准来计算count，这样也可以让我们只检查count与0的关系，比较符合逻辑
                        // !!!当缩小window时，先把要从window中减去的char，也就是当前的char放回map
                        charNumMap.put(sCharArray[leftP], charNumMap.get(sCharArray[leftP]) + 1);
                        // 然后再看这个char在map中的数量是不是仍然小于等于0，如果大于0了，说明我们需要当前char是有效char，我们就需要将count--。
                        if(charNumMap.get(sCharArray[leftP]) > 0){ // currently char at leftP is useful, so since we will put it back from window to map, we need decrement our count
                            count--;
                        }
                    }

                    // increment leftP
                    leftP++;
                }
            }
        }

        if(minWindow == Integer.MAX_VALUE){
            return "";
        }

        return s.substring(minWindowLeft, minWindowLeft + minWindow);

    }
}
