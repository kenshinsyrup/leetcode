package com.myleetcode.array.shortest_word_distance;

class Solution {
    public int shortestDistance(String[] words, String word1, String word2) {
        return shortestDistanceByTraverse(words, word1, word2);
    }

    // TC: O(N)
    // SC: O(1)
    // intuition: use a variable to record the lastest idx of given two words, everytime we meet the given word, we compare the distance and update the idx
    private int shortestDistanceByTraverse(String[] words, String word1, String word2){
        if(words == null || words.length == 0){
            return 0;
        }
        if(word1 == null || word2 == null){
            return 0;
        }

        int idxWord1 = -1;
        int idxWord2 = -1;
        int minDist = Integer.MAX_VALUE;
        for(int i = 0; i < words.length; i++){
            if(words[i].equals(word1)){
                idxWord1 = i;
            }else if(words[i].equals(word2)){
                idxWord2 = i;
            }

            if(idxWord2 != -1 && idxWord1 != -1){
                minDist = Math.min(minDist, Math.abs(idxWord2 - idxWord1));
            }
        }

        return minDist;

    }
}
