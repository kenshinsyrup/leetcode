package com.myleetcode.ordered_map.hand_of_straights;

import java.util.TreeMap;

public class Solution {
    public boolean isNStraightHand(int[] hand, int W) {
        return isNStraightHandByTreeMap(hand, W);
    }

    /*
    TreeMap

    TC: O(max(N*logN, W*logN))
    SC: O(N)
    */
    private boolean isNStraightHandByTreeMap(int[] hand, int W) {
        if (hand == null || hand.length == 0 || W <= 0) {
            return false;
        }

        int len = hand.length;
        if (len / W == 0 || len % W != 0) {
            return false;
        }

        TreeMap<Integer, Integer> cardNumTM = new TreeMap<>();
        for (int card : hand) {
            cardNumTM.put(card, cardNumTM.getOrDefault(card, 0) + 1);
        }

        int groupNum = len / W;
        for (int group = 1; group <= groupNum; group++) {
            int curNumInGroup = 0;
            int curCard = cardNumTM.firstKey();
            while (curNumInGroup < W) {
                // Check curCard needed exist.
                if (!cardNumTM.containsKey(curCard)) {
                    return false;
                }

                // Add into cur group and reduce its number in TM, if 0 remove it.
                cardNumTM.put(curCard, cardNumTM.get(curCard) - 1);
                if (cardNumTM.get(curCard) <= 0) {
                    cardNumTM.remove(curCard);
                }
                curNumInGroup++;

                // Next card needed(if curNumInGroup not equal to W).
                curCard++;
            }
        }

        return true;

    }
}
