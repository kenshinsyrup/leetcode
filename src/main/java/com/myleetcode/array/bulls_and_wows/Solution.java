package com.myleetcode.array.bulls_and_wows;

import java.util.HashSet;
import java.util.Set;

class Solution {
    public String getHint(String secret, String guess) {
        // return getHintByLoop(secret, guess);
        return getHintByArray(secret, guess);
    }

    // here is a one pass solution, use negative and positive to record the secretCharTimes and guessCharTimes into one int[10]: https://leetcode.com/problems/bulls-and-cows/discuss/74621/One-pass-Java-solution

    // TC: O(N)
    // SC: O(1)
    // optimize: we could use two pass instead of nested for-loop, and we dont need the Set
    // https://leetcode.com/problems/bulls-and-cows/discuss/74735/Very-easy-solution-using-two-arrays
    private String getHintByArray(String secret, String guess){
        if(secret == null || secret.length() == 0 || guess == null || guess.length() == 0 || secret.length() != guess.length()){
            return "";
        }

        // we use int[10] to record the appearance times of char
        int[] secretCharTimes = new int[10];
        int[] guessCharTimes = new int[10];

        int bulls = 0;
        for(int i = 0; i < secret.length(); i++){
            char secretChar = secret.charAt(i);
            char guessChar = guess.charAt(i);

            if(secretChar == guessChar){
                bulls++;
            }else{
                secretCharTimes[secretChar - '0']++;
                guessCharTimes[guessChar - '0']++;
            }
        }

        // at last, we traverse the secretCharTimes and guessCharTimes, for every char in secret and guess, the cows are the sum of each char overlap times, ie the min of each char appearance times
        int cows = 0;
        for(int i = 0; i < 10; i++){
            cows += Math.min(secretCharTimes[i], guessCharTimes[i]);
        }

        return bulls + "A" + cows + "B";


    }

    // TC: O(N^2)
    // SC: O(N)
    // intuition:
    // A is easy, we could use traverse the two String in nested for-loop, if i == j and str1[i] == str2[j] then this count a A.
    // B, we could use a Set to remember the j we used, then if i!=j && !Set.contains(j) && str1[i] == str2[j], then we store the j and count a B
    // BUT, A has more priority than B, so we should first process all A done.
    private String getHintByLoop(String secret, String guess){
        if(secret == null || secret.length() == 0 || guess == null || guess.length() == 0 || secret.length() != guess.length()){
            return "";
        }

        int secretLen = secret.length();
        int guessLen = guess.length();
        Set<Integer> usedSecretIdx = new HashSet<>();
        Set<Integer> usedGuessIdx = new HashSet<>();

        // count A
        int countA = 0;
        for(int i = 0; i < secretLen; i++){
            if(secret.charAt(i) == guess.charAt(i)){
                usedSecretIdx.add(i);
                usedGuessIdx.add(i);
                countA++;
            }
        }

        // count B
        int countB = 0;
        for(int i = 0; i < secretLen; i++){
            // skip the used i
            if(usedSecretIdx.contains(i)){
                continue;
            }

            for(int j = 0; j < guessLen; j++){
                if(i == j){
                    continue;
                }

                if(!usedGuessIdx.contains(j) && secret.charAt(i) == guess.charAt(j)){
                    usedGuessIdx.add(j);
                    countB++;
                    break; // !!! every time we find one, break, because we could not use more than one time i
                }
            }
        }

        return countA + "A" + countB + "B";
    }
}
