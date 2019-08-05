package com.myleetcode.trie.word_abbreviation;

import java.util.*;

class Solution {
    public List<String> wordsAbbreviation(List<String> dict) {
        // return wordsAbbreviationByMap(dict);

        return wordsAbbreviationByTrie(dict);
    }

    // Optimize with Trie: https://leetcode.com/problems/word-abbreviation/discuss/99792/HashMap-%2B-Trie-greater-O(nL)-solution
    // define Trie
    private class TrieNode{
        int count;
        Map<Character, TrieNode> children;

        public TrieNode(){
            count = 0; // count means in cur node, in chilren map, how many child under a given key
            children = new HashMap<>();
        }
    }

    // build Trie
    private TrieNode buildTrie(List<String> dictList, List<Integer> idxList){
        TrieNode root = new TrieNode();

        for(int idx: idxList){
            String str = dictList.get(idx);
            TrieNode curNode = root;

            for(char ch: str.toCharArray()){
                if(!curNode.children.containsKey(ch)){
                    TrieNode child = new TrieNode();
                    curNode.children.put(ch, child);
                }
                curNode = curNode.children.get(ch);
                curNode.count++;
            }
        }

        return root;
    }

    // abbrev with Trie
    // TC: O(N * M), N is dictList length, M is the longest string length
    // SC: O(N * M), Trie
    private List<String> wordsAbbreviationByTrie(List<String> dictList){
        List<String> ret = new ArrayList<>();
        if(dictList == null || dictList.size() == 0){
            return ret;
        }

        // 1 group by abbrev
        Map<String, List<Integer>> abbrevIdxsMap = new HashMap<>();
        for(int i = 0; i < dictList.size(); i++){
            String abbrev = abbreviate(dictList.get(i), 1);
            abbrevIdxsMap.putIfAbsent(abbrev, new ArrayList<>());
            abbrevIdxsMap.get(abbrev).add(i);
        }

        // 2 process group by trie
        String[] retArr = new String[dictList.size()];
        for(String abbrev: abbrevIdxsMap.keySet()){
            List<Integer> idxList = abbrevIdxsMap.get(abbrev);
            resolve(abbrev, dictList, idxList, retArr);
        }

        return Arrays.asList(retArr);
    }

    private void resolve(String abbrev, List<String> dictList, List<Integer> idxList, String[] retArr){
        // no duplicates for this abbrev
        if(idxList.size() == 1){
            retArr[idxList.get(0)] = abbrev;
            return;
        }

        // resolve confilcts for current abbrev
        TrieNode root = buildTrie(dictList, idxList);

        for(int idx: idxList){
            String str = dictList.get(idx);
            TrieNode curNode = root;

            // find the first idx that only one char, means [0:i] as prefix has no duplicate
            int i = 0;
            while(i < str.length()){
                char ch = str.charAt(i);

                if(curNode.children.get(ch).count <= 1){
                    break;
                }

                curNode = curNode.children.get(ch);
                i++;
            }
            String newAbbrev = abbreviate(str, i + 1);
            retArr[idx] = newAbbrev;
        }
    }


    // Naive Solution:
    // https://leetcode.com/problems/word-abbreviation/discuss/99782/Really-simple-and-straightforward-Java-solution/118055
    // try to abbreviate all words, at first we only use 1 prefix like "a12c", then if there are duplicates, extend those duplicates' prefix by 1, like "ab11c"...
    // TC: O(N^2 * M), N is dictList length, M is string length
    // SC: O(N)
    private List<String> wordsAbbreviationByMap(List<String> dictList){
        List<String> ret = new ArrayList<>();
        if(dictList == null || dictList.size() == 0){
            return ret;
        }

        // 1. abbreviate all words with only 1 prefix
        int size = dictList.size();
        int[] prefix = new int[size]; // record word at idx i's prefix num
        Map<String, List<Integer>> abbrevIdxsMap = new HashMap<>(); // abbrev -> idx list
        for(int i = 0; i < size; i++){
            prefix[i] = 1; // at first, prefix all set to 1
            String abbrev = abbreviate(dictList.get(i), prefix[i]); // get abbrev
            ret.add(abbrev);
            List<Integer> idxList = abbrevIdxsMap.getOrDefault(abbrev, new ArrayList<>());
            idxList.add(i);
            abbrevIdxsMap.put(abbrev, idxList);
        }

        // 2. traverse ret to process duplicate abbrev
        for(int i = 0; i < size; i++){
            String abbrev = ret.get(i);
            List<Integer> idxList = abbrevIdxsMap.get(abbrev);
            // if has duplicates
            if(idxList.size() > 1){
                abbrevIdxsMap.remove(abbrev); // remove the old abbrev

                for(int idx: idxList){
                    prefix[idx]++; // add one prefix
                    String newAbbrev = abbreviate(dictList.get(idx), prefix[idx]);
                    ret.set(idx, newAbbrev);// update ret at idx
                    List<Integer> newIdxList = abbrevIdxsMap.getOrDefault(newAbbrev, new ArrayList<>());
                    newIdxList.add(idx);
                    abbrevIdxsMap.put(newAbbrev, newIdxList);
                }

                i--; // re-check from current i
            }
        }

        return ret;
    }

    private String abbreviate(String str, int num){
        if(num >= str.length() - 2){
            return str;
        }

        StringBuilder sb = new StringBuilder();
        int idx = 0;
        while(idx < num){
            sb.append(str.charAt(idx)); // str[0:num-1] chars

            idx++;
        }

        sb.append(str.length() - 1 - 1 - idx + 1); // str[idx:str.length()-1-1] length

        sb.append(str.charAt(str.length() - 1)); // last char

        return sb.toString();

    }
}
