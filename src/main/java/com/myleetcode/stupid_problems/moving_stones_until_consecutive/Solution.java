package com.myleetcode.stupid_problems.moving_stones_until_consecutive;

class Solution {
    public int[] numMovesStones(int a, int b, int c) {
        return numMovesStonesByGap(a, b, c);
    }

    // https://leetcode.com/problems/moving-stones-until-consecutive/discuss/282836/C%2B%2BJava-4-lines
    /*很tricky的一道题，关于min moves:
Min moves can only be either 1 or 2. These are the only 2 possible cases 1) AB---C or 2) A--B----C, dashes are spaces. In case 1, A and B are next to each other so you only need 1 move to move C next to B. In case 2, you need to move A next to B and then move C next to B, 2 moves. There is also case 3) A---BC but this is the same as case #1.

So the answer is take the shortest distance between AB and BC. If the distance is 0, you know either A is next to B or B is next to C. So the min is just 1. Is the shortest distance is > 0, you know there are some spaces between A--B and B--C so you need 2 moves.
*/
    /* 而对于max moves，就是C-A-2*/
    private int[] numMovesStonesByGap(int posA, int posB, int posC){
        // make sure: posA <= posB <= posC
        int temp = 0;
        if(posA > posB){
            temp = posB;
            posB = posA;
            posA = temp;
        }
        if(posA > posC){
            temp = posC;
            posC = posA;
            posA = temp;
        }
        if(posB > posC){
            temp = posC;
            posC = posB;
            posB = temp;
        }

        // case 1, if posC-posA == 2 means consecutive
        if(posC - posA == 2){
            return new int[]{0, 0};
        }

        // case 2, normal case
        int minGap = Math.min(posC - posB - 1, posB - posA - 1);
        if(minGap <= 1){
            return new int[]{1, posC - posA - 2};
        }

        return new int[]{2, posC - posA - 2};
    }
}
