package com.myleetcode.binary_search.koko_eating_bananas;

import java.util.Arrays;

class Solution {
    public int minEatingSpeed(int[] piles, int H) {
        return minEatingSpeedByBS(piles, H);
        // return minEatingSpeedByBSAndSort(piles, H);
    }

    // intuition:
    // k is in the range 1 to piles[len-1], we need find a val that sum of piles[i]/k is smalelr than H, so BS
    // TC:
    //      with sort: O(max(N * logM, N * logN)), N is the length of piles, M is the largest pile
    //      with pick maxPile: O(N*logM)
    // SC: O(1)
    private int minEatingSpeedByBS(int[] piles, int H){
        if(piles == null || piles.length == 0 || H < piles.length){
            return 0;
        }

        int maxPile = 0;
        for(int i = 0; i < piles.length; i++){
            maxPile = Math.max(maxPile, piles[i]);
        }

        int len = piles.length;
        int start = 1;
        int end = maxPile;
        while(start <= end){
            int mid = start + (end - start) / 2;

            int sum = 0;
            for(int i = 0; i < len; i++){
                sum += piles[i] / mid;
                // no enough 1 hour caculated as 1 hour
                if(piles[i] % mid != 0){
                    sum += 1;
                }
            }

            if(sum > H){ // we need the min k, so need the largest sum that smaller or equals to H
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }

        if(start > maxPile){
            return -1;
        }

        return start;
    }

    private int minEatingSpeedByBSAndSort(int[] piles, int H){
        if(piles == null || piles.length == 0 || H < piles.length){
            return 0;
        }

        Arrays.sort(piles);

        int len = piles.length;
        int start = 1;
        int end = piles[len - 1];
        while(start <= end){
            int mid = start + (end - start) / 2;

            int sum = 0;
            for(int i = 0; i < len; i++){
                sum += piles[i] / mid;
                // no enough 1 hour caculated as 1 hour
                if(piles[i] % mid != 0){
                    sum += 1;
                }
            }

            if(sum > H){ // we need the min k, so need the largest sum that smaller or equals to H
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }
        if(start > piles[len - 1]){// !!! if not find, here is the piles[len-1], not len-1
            return -1;
        }

        return start;
    }

}
