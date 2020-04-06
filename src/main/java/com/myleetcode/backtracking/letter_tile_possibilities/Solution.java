package com.myleetcode.backtracking.letter_tile_possibilities;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    public int numTilePossibilities(String tiles) {
        // return numTilePossibilitiesByMath(tiles);
        return numTilePossibilitiesByBacktracking(tiles);
        // return numTilePossibilitiesByBacktrackingII(tiles);
    }

    /*
    Backtracking refactor.

    TC: O(2^N)
    SC: O(N)
    */
    private int numTilePossibilitiesByBacktrackingII(String tiles) {
        if (tiles == null || tiles.length() == 0) {
            return 0;
        }
        if (tiles.length() == 0) {
            return 1;
        }

        int len = tiles.length();
        boolean[] visited = new boolean[len];
        Set<String> snippet = new HashSet<>();
        StringBuilder sb = new StringBuilder();

        backtrackingII(tiles, visited, sb, snippet);

        return snippet.size();
    }

    private void backtrackingII(String tiles, boolean[] visited, StringBuilder sb, Set<String> snippet) {
        int len = tiles.length();
        // Base case.
        if (sb.length() >= len) {
            return;
        }

        // Explore, since we need combination not permunation, so i always start with 0.
        for (int i = 0; i < len; i++) {
            if (visited[i]) {
                continue;
            }

            sb.append(tiles.charAt(i));
            visited[i] = true;
            snippet.add(sb.toString());

            backtrackingII(tiles, visited, sb, snippet);

            // Backtrack.
            visited[i] = false;
            sb.deleteCharAt(sb.length() - 1);
        }

    }

    /*
    Backtracking.

    TC: O(2^N)
    SC: O(N)
    */
    private int numTilePossibilitiesByBacktracking(String tiles) {
        if (tiles == null || tiles.length() == 0) {
            return 0;
        }
        if (tiles.length() == 0) {
            return 1;
        }

        Set<String> snippet = new HashSet<>();
        int len = tiles.length();
        boolean[] visited = new boolean[len];
        for (int i = 0; i < len; i++) {
            backtracking(tiles, i, visited, new StringBuilder(), snippet);
        }

        return snippet.size();

    }

    private void backtracking(String tiles, int curIdx, boolean[] visited, StringBuilder sb, Set<String> snippet) {
        int len = tiles.length();
        // Base case.
        if (curIdx >= len) {
            return;
        }

        // Current.
        sb.append(tiles.charAt(curIdx));
        visited[curIdx] = true;
        snippet.add(sb.toString());

        // Explore, since we need combination not permunation, so i always start with 0.
        for (int i = 0; i < len; i++) {
            if (visited[i]) {
                continue;
            }
            backtracking(tiles, i, visited, sb, snippet);
        }

        // Backtrack.
        visited[curIdx] = false;
        sb.deleteCharAt(sb.length() - 1);
    }

    /*
    Wrong.

    There's duplicate characters in given tiles, so could not use permutation number formula to caculate directly.

    */
    private int numTilePossibilitiesByMath(String tiles) {
        if (tiles == null || tiles.length() == 0) {
            return 0;
        }
        if (tiles.length() == 0) {
            return 1;
        }

        int ret = 0;
        int len = tiles.length();
        for (int i = 1; i <= len; i++) {
            ret += factory(i);
        }

        return ret;
    }

    private int factory(int num) {
        if (num == 0) {
            return 1;
        }

        int ret = 1;
        for (int i = 1; i <= num; i++) {
            ret *= i;
        }

        return ret;
    }
}
